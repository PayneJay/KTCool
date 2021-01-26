package com.ktcool.common.router;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyRouter {
    private volatile static MyRouter instance;
    private final Map<String, Class<?>> routerMap = new HashMap<>();
    private final Map<String, String> stringParams = new HashMap<>();
    private final Map<String, Integer> intParams = new HashMap<>();
    private final Map<String, Boolean> booleanParams = new HashMap<>();
    private WeakReference<Context> contextWeakRef;

    public static MyRouter getInstance() {
        if (instance == null) {//这一层的判空是为了防止下面重复加锁
            synchronized (MyRouter.class) {
                if (instance == null) {//这一层的判空是为了防止重复构建实例
                    instance = new MyRouter();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        contextWeakRef = new WeakReference<Context>(application);

        Set<String> fileNames = ClassUtils.INSTANCE.getFileNameByPackageName(contextWeakRef.get(), "com.ktcool");
        if (fileNames == null) return;
        for (String name : fileNames) {
            Class<?> clazz;
            try {
                clazz = Class.forName(name);
                Object obj = clazz.newInstance();
                if (obj instanceof IRouter) {
                    IRouter route = (IRouter) obj;
                    route.loadInto();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void addRouter(String path, Class<?> clazz) {
        if (!routerMap.containsKey(path)) {
            routerMap.put(path, clazz);
        }
    }

    public MyRouter withString(String key, String value) {
        if (!stringParams.containsKey(key) && !TextUtils.isEmpty(key)) {
            stringParams.put(key, value);
        }
        return this;
    }

    public MyRouter withInt(String key, int value) {
        if (!intParams.containsKey(key) && !TextUtils.isEmpty(key)) {
            intParams.put(key, value);
        }
        return this;
    }

    public MyRouter withBool(String key, boolean value) {
        if (!booleanParams.containsKey(key) && !TextUtils.isEmpty(key)) {
            booleanParams.put(key, value);
        }
        return this;
    }

    public void navigation(String path) {
        Class<?> aClass = routerMap.get(path);
        Context context = contextWeakRef.get();
        if (aClass != null && context != null) {
            Intent intent = new Intent(context, aClass);
            if (!stringParams.isEmpty()) {
                for (String key : stringParams.keySet()) {
                    intent.putExtra(key, stringParams.get(key));
                }
            }
            if (!intParams.isEmpty()) {
                for (String key : intParams.keySet()) {
                    intent.putExtra(key, intParams.get(key));
                }
            }
            if (!booleanParams.isEmpty()) {
                for (String key : booleanParams.keySet()) {
                    intent.putExtra(key, booleanParams.get(key));
                }
            }
            //用application的context启动Activity必须加这一句,否则抛异常
            //android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity
            //context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
