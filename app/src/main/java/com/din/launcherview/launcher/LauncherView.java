package com.din.launcherview.launcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.din.launcherview.R;

public class LauncherView extends RelativeLayout {

    private int mHeight;
    private int mWidth;
    private int dp80 = dp2px(getContext(), 80);

    public LauncherView(Context context) {
        super(context);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //  动画图片
    private ImageView red, purple, yellow, blue;

    private void init() {

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL, TRUE);    //这里的TRUE 要注意 不是true
        lp.addRule(CENTER_VERTICAL, TRUE);
        //  图片设置margin
        lp.setMargins(0, 0, 0, dp80);

        //  初始化四张图片
        purple = new ImageView(getContext());
        purple.setLayoutParams(lp);
        purple.setImageResource(R.drawable.shape_circle_purple);
        addView(purple);

        yellow = new ImageView(getContext());
        yellow.setLayoutParams(lp);
        yellow.setImageResource(R.drawable.shape_circle_yellow);
        addView(yellow);

        blue = new ImageView(getContext());
        blue.setLayoutParams(lp);
        blue.setImageResource(R.drawable.shape_circle_blue);
        addView(blue);

        red = new ImageView(getContext());
        red.setLayoutParams(lp);
        red.setImageResource(R.drawable.shape_circle_red);
        addView(red);

        //  设置动画效果和路径
        setAnimation(red, redPath1);
        setAnimation(purple, purplePath1);
        setAnimation(yellow, yellowPath1);
        setAnimation(blue, bluePath1);

    }

    private ViewPath redPath1, purplePath1, yellowPath1, bluePath1;

    //  绘制路径
    private void initPath() {
        redPath1 = new ViewPath(); //偏移坐标
        redPath1.moveTo(0, 0);
        redPath1.lineTo(mWidth / 5 - mWidth / 2, 0);
        redPath1.curveTo(-700, -mHeight / 2, mWidth / 3 * 2, -mHeight / 3 * 2, 0, -dp80);

        purplePath1 = new ViewPath(); //偏移坐标
        purplePath1.moveTo(0, 0);
        purplePath1.lineTo(mWidth / 5 * 2 - mWidth / 2, 0);
        purplePath1.curveTo(-300, -mHeight / 2, mWidth, -mHeight / 9 * 5, 0, -dp80);

        yellowPath1 = new ViewPath(); //偏移坐标
        yellowPath1.moveTo(0, 0);
        yellowPath1.lineTo(mWidth / 5 * 3 - mWidth / 2, 0);
        yellowPath1.curveTo(300, mHeight, -mWidth, -mHeight / 9 * 5, 0, -dp80);

        bluePath1 = new ViewPath(); //偏移坐标
        bluePath1.moveTo(0, 0);
        bluePath1.lineTo(mWidth / 5 * 4 - mWidth / 2, 0);
        bluePath1.curveTo(700, mHeight / 3 * 2, -mWidth / 2, mHeight / 2, 0, -dp80);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        initPath();
    }
    
    //  动画启动
    public void start() {
        removeAllViews();
        init();
        redAll.start();
        yellowAll.start();
        purpleAll.start();
        blueAll.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogo();
            }
        }, 2400);
    }

    //  设置动画
    private void setAnimation(final ImageView target, ViewPath path1) {
        //路径
        ObjectAnimator anim1 = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), path1.getPoints().toArray());
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());
        anim1.setDuration(2600);
        //组合添加缩放透明效果
        addAnimation(anim1, target);
    }


    private AnimatorSet redAll, purpleAll, yellowAll, blueAll;

    private void addAnimation(ObjectAnimator animator1, final ImageView target) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 1000);
        valueAnimator.setDuration(1800);
        valueAnimator.setStartDelay(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float alpha = 1 - value / 2000;
                float scale = getScale(target) - 1;
                if (value <= 500) {
                    scale = 1 + (value / 500) * scale;
                } else {
                    scale = 1 + ((1000 - value) / 500) * scale;
                }
                target.setScaleX(scale);
                target.setScaleY(scale);
                target.setAlpha(alpha);
            }
        });
        valueAnimator.addListener(new AnimEndListener(target));
        if (target == red) {
            redAll = new AnimatorSet();
            redAll.playTogether(animator1, valueAnimator);
        }
        if (target == blue) {
            blueAll = new AnimatorSet();
            blueAll.playTogether(animator1, valueAnimator);
        }
        if (target == purple) {
            purpleAll = new AnimatorSet();
            purpleAll.playTogether(animator1, valueAnimator);
        }
        if (target == yellow) {
            yellowAll = new AnimatorSet();
            yellowAll.playTogether(animator1, valueAnimator);
        }

    }

    private float getScale(ImageView target) {
        if (target == red)
            return 3.0f;
        if (target == purple)
            return 2.0f;
        if (target == yellow)
            return 4.5f;
        if (target == blue)
            return 3.5f;
        return 2f;
    }

    //  设置动画结束之后的图片控件ID
    private int loginId, slogoId;

    public void setSlogo(int slogoId) {
        this.slogoId = slogoId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    //  动画结束后显示图片
    private void showLogo() {
        View view = View.inflate(getContext(), R.layout.activity_main, this);
        View logo = view.findViewById(loginId);
        final View slogo = view.findViewById(slogoId);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f);
        alpha.setDuration(800);

        alpha.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(slogo, View.ALPHA, 0f, 1f);
                alpha.setDuration(200);
                alpha.start();
            }
        }, 400);

    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView((target));
        }
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}