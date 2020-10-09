package com.ktcool.lib_permission.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ktcool.lib_permission.IPermission;
import com.ktcool.lib_permission.annotation.PermissionDenied;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PermissionsUtils {
    private final int mRequestCode = 100;//权限请求码
    public static boolean showSystemSetting = true;
    /**
     * 不再提示权限时的展示对话框
     */
    private AlertDialog mPermissionDialog;

    private PermissionsUtils() {
    }

    private static PermissionsUtils permissionsUtils;
    private IPermission mPermissionsResult;

    public static PermissionsUtils getInstance() {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionsUtils();
        }
        return permissionsUtils;
    }

    public void checkPermissions(Activity context, String[] permissions, @NonNull IPermission permissionsResult) {
        mPermissionsResult = permissionsResult;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//6.0才用动态权限
            permissionsResult.permissionGranted();
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPermissionList中
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, permissions, mRequestCode);
        } else {
            //说明权限都已经通过
            permissionsResult.permissionGranted();
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    showSystemPermissionsSettingDialog(context, permissions);//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                } else {
                    mPermissionsResult.permissionDenied(requestCode, permissions);
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionsResult.permissionGranted();
            }
        }

    }


    private void showSystemPermissionsSettingDialog(final Activity context, final String[] permissions) {
        final String mPackName = context.getPackageName();
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            //mContext.finish();
                            mPermissionsResult.permissionDenied(mRequestCode, permissions);
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }

    }

    /**
     * 通过反射调用指定注解方法
     *
     * @param obj             注解方法所在类
     * @param annotationClazz 注解类名
     * @param requestCode     requestCode
     */
    public void invokeAnnotationMethod(Object obj, Class<? extends Annotation> annotationClazz, int requestCode) {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getDeclaredMethods();
        if (methods.length <= 0) {
            return;
        }

        for (Method method : methods) {
            //根据是否有指定注解过滤
            boolean annotationPresent = method.isAnnotationPresent(annotationClazz);
            if (annotationPresent) {
                method.setAccessible(true);
                //获取方法参数类型
                Class<?>[] parameterTypes = method.getParameterTypes();
                //获取方法上的注解
                PermissionDenied annotation = (PermissionDenied) method.getAnnotation(annotationClazz);
                if (annotation == null) {
                    return;
                }

                try {
                    if (parameterTypes.length == 1) {
                        method.invoke(obj, requestCode);
                    } else {
                        method.invoke(obj);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
