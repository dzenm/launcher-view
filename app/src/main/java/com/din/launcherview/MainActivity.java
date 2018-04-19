package com.din.launcherview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.launcherview.launcher.LauncherView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LauncherView launcherView = (LauncherView) findViewById(R.id.load_view);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherView.setLoginId(R.id.iv_logo);
                launcherView.setSlogo(R.id.iv_slogo);
                launcherView.start();
            }
        });
    }
}
