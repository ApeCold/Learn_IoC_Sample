package cn.bsd.learn.ioc.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bsd.learn.ioc.library.InjectManager;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //帮助所有子类完成注入工作
        InjectManager.inject(this);
    }
}
