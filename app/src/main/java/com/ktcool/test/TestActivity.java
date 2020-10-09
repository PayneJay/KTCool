package com.ktcool.test;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ktcool.R;
import com.ktcool.annotation.Router;
import com.ktcool.common.constant.RouterMap;
import com.ktcool.common.network.HttpPoxy;
import com.ktcool.common.network.ICallback;
import com.ktcool.common.utils.MyPrint;
//import com.ktcool.lib_permission.annotation.PermissionNeed;
import com.ktcool.module_dashboard.bean.TestBean;

import java.security.Permission;

@Router(path = RouterMap.TEST_ACTIVITY)
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionRequest();
            }
        });

        netRequest();
    }

    private void netRequest() {
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

//    @PermissionNeed(value = {Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, requestCode = 100)
    public void permissionRequest() {
    }
}
