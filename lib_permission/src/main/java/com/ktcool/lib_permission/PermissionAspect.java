package com.ktcool.lib_permission;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.ktcool.lib_permission.annotation.PermissionDenied;
import com.ktcool.lib_permission.annotation.PermissionNeed;
import com.ktcool.lib_permission.utils.PermissionsUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    @Pointcut("execution(@com.ktcool.lib_permission.annotation.PermissionNeed * *(..)) && @annotation(permissionNeed)")
    public void permissionRequest(PermissionNeed permissionNeed) {
        printLog("PermissionAspect --> permissionRequest");
    }

    @Around("permissionRequest(permissionNeed)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, final PermissionNeed permissionNeed) {
        //拿到PermissionNeed注解所在类的类对象
        final Object obj = joinPoint.getThis();
        Activity context = null;
        if (obj instanceof Activity) {
            context = (Activity) obj;
        } else if (obj instanceof Fragment) {
            context = ((Fragment) obj).getActivity();
        }
        printLog("PermissionAspect --> checkPermission");
        PermissionRequestActivity.startPermissionActivity(context, permissionNeed.value(),
                permissionNeed.requestCode(), new IPermission() {
                    @Override
                    public void permissionDenied(int requestCode, String[] permissions) {
                        printLog("PermissionAspect --> permissionDenied --> requestCode " + requestCode);
                        PermissionsUtils.getInstance().invokeAnnotationMethod(obj, PermissionDenied.class, requestCode);
                    }

                    @Override
                    public void permissionGranted() {
                        try {
                            joinPoint.proceed();
                            printLog("PermissionAspect --> permissionGranted");
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }

    private void printLog(String s) {
        Log.d("Leather", " Log :" + s);
    }
}
