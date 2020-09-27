package com.ktcool.common.network;

import java.util.Map;

/**
 * Http网络请求代理类
 * 代理模式方便扩展
 */
public class HttpPoxy implements IHttp {
    private static volatile HttpPoxy instance;
    private IHttp mHttp;

    private HttpPoxy() {
    }

    public static HttpPoxy getInstance() {
        if (null == instance) {
            synchronized (HttpPoxy.class) {
                if (null == instance) {
                    instance = new HttpPoxy();
                }
            }
        }
        return instance;
    }

    /**
     * 以后想用别的网络请求框架可以加一个新的Mode，交给我们的代理类处理
     * 只需实现IHttp接口即可
     *
     * @param http 网络请求具体执行类
     */
    public void init(IHttp http) {
        mHttp = http;
        if (null == mHttp) {
            //如果开发这没传，则默认使用okHttp
            mHttp = OKHttpMode.getInstance();
        }
    }

    @Override
    public <T extends BaseBean> void get(String url, Map<String, String> params, ICallback<T> iCallback, Class<T> clazz) {
        mHttp.get(url, params, iCallback, clazz);
    }

    @Override
    public <T extends BaseBean> void post(String url, String params, ICallback<T> iCallback, Class<T> clazz) {
        mHttp.post(url, params, iCallback, clazz);
    }

    @Override
    public <T extends BaseBean> void put(String url, String params, ICallback<T> iCallback, Class<T> clazz) {
        mHttp.put(url, params, iCallback, clazz);
    }

    @Override
    public <T extends BaseBean> void delete(String url, String params, ICallback<T> iCallback, Class<T> clazz) {
        mHttp.delete(url, params, iCallback, clazz);
    }

    @Override
    public <T extends BaseBean> void patch(String url, String params, ICallback<T> iCallback, Class<T> clazz) {
        mHttp.patch(url, params, iCallback, clazz);
    }
}
