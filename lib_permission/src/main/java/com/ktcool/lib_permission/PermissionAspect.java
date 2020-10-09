package com.ktcool.lib_permission;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.ktcool.lib_permission.annotation.PermissionNeed;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    @Pointcut("execution(@com.ktcool.lib_permission.annotation.PermissionNeed * * (..)) && @annotation(permissionNeed)")
    public void permissionRequest() {

    }

    @Around("permissionRequest(permissionNeed)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {
        //拿到PermissionNeed注解所在类的类对象
        Object obj = joinPoint.getThis();
        Activity context = null;
        if (obj instanceof Activity) {
            context = (Activity) obj;
        } else if (obj instanceof Fragment) {
            context = ((Fragment) obj).getActivity();
        }
        PermissionRequestActivity.startPermissionActivity(context, permissionNeed.value(),
                permissionNeed.requestCode(), new IPermission() {
                    @Override
                    public void permissionDenied(int requestCode, String[] permissions) {
                        Log.d("Leather", "权限申请拒绝 requestCode ： " + requestCode);
                    }

                    @Override
                    public void permissionGranted() {
                        Log.d("Leather", "权限申请通过");
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }
}
