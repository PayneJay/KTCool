package com.ktcool.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ktcool.R;
import com.ktcool.annotation.Router;
import com.ktcool.common.constant.RouterMap;
import com.ktcool.common.network.HttpPoxy;
import com.ktcool.common.network.ICallback;
import com.ktcool.common.utils.MyPrint;
import com.ktcool.module_dashboard.bean.TestBean;

@Router(path = RouterMap.TEST_ACTIVITY)
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        HttpPoxy.getInstance().get("https://easy-mock.com/mock/5f18f3a02da6de5e62cedb35/example/query", null, new ICallback<TestBean>() {
            @Override
            public void onSuccess(TestBean response) {
                MyPrint.Companion.print("onSuccess : " + response.toString());
            }

            @Override
            public void onFailure(String msg) {
                MyPrint.Companion.print("onFailure : " + msg);
            }
        }, TestBean.class);
    }
}
