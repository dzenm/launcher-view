

#### 第一步、在drawable新建四个不同颜色xml的圆形背景：以下是其中一个

```Xml
<shape
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval"
    android:useLevel="false">
    <solid
        android:color="@color/load_blue"/>
    <size android:width="40dp"
          android:height="40dp"/>
</shape>
```

#### 第二步、在activity对应的layout中使用以下布局

```Xml
    <com.din.launcherview.launcher.LauncherView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/load_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <ImageView
        android:id="@+id/iv_logo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginBottom="80dp"
        android:alpha="0"
        android:src="@mipmap/logo" />

    <ImageView
        android:id="@+id/iv_slogo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginBottom="70dp"
        android:layout_marginTop="80dp"
        android:alpha="0"
        android:scaleX="1.5"
        android:scaleY="1.5"
        android:src="@mipmap/slogan" />


    <Button android:layout_gravity="center_horizontal|bottom"
        android:layout_width="wrap_content"
        android:text="start"
        android:id="@+id/start"
        android:layout_height="wrap_content"/>
```

* 第一个是自定义的View
* 第二和第三个ImageView是用来最后显示的图片
* 第四个是启动的按钮

#### 第三步、在activity里为按钮设置启动点击事件

```
		final LauncherView launcherView = (LauncherView) 					findViewById(R.id.load_view);
        findViewById(R.id.start).setOnClickListener(new 					View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherView.setLoginId(R.id.iv_logo);
                launcherView.setSlogo(R.id.iv_slogo);
                //以上两个id是为了设置图片显示需要的ID
                launcherView.start();
            }
        });
```



---



* 在mipmap-xxhdpi文件夹中需要放置两个显示的图片，分别是logo.png和slogan.png



