package com.ktcool.module_dashboard;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ktcool.annotation.Router;
import com.ktcool.common.constant.RouterMap;
import com.ktcool.common.network.HttpPoxy;
import com.ktcool.common.network.ICallback;
import com.ktcool.common.utils.MyPrint;
import com.ktcool.lib_permission.annotation.PermissionDenied;
import com.ktcool.lib_permission.annotation.PermissionNeed;
import com.ktcool.module_dashboard.bean.TestBean;

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
        HttpPoxy.getInstance().get("http://m.data.mob.com/v2/cconf?appkey=198a4d42a5e&plat=1&apppkg=com.tuicool.activity&appver=3.3.1&networktype=wifi&duid=de0123fce7e7b758931e4361035feb08c365a5bb", null, new ICallback<TestBean>() {
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

    @PermissionNeed(value = {Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR}, requestCode = 100)
    public void permissionRequest() {
        MyPrint.Companion.print("permissionRequest : 权限申请通过");
    }

    @PermissionDenied()
    public void permissionDenied(int requestCode) {
        MyPrint.Companion.print("permissionDenied : 权限申请拒绝" + requestCode);
    }
}
