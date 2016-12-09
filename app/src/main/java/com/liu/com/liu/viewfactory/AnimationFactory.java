package com.liu.com.liu.viewfactory;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2016-12-8.
 */
public class AnimationFactory {

    public static AnimationSet createScaleAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1,1.2f,         //x开始位置，放大/缩小倍数
                1,1.2f,         //y开始位置，放大/缩小倍数
                Animation.RELATIVE_TO_SELF,0,           //x放大/缩小的位置
                Animation.RELATIVE_TO_SELF,0);          //y放大/缩小的位置
        scaleAnimation.setDuration(4000);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(-1);
        animationSet.addAnimation(scaleAnimation);

        return animationSet;
    }

    public static AnimationSet createTranslateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation
                (       Animation.RELATIVE_TO_SELF,0,   //x起始位置
                        Animation.RELATIVE_TO_SELF,0,   //x终止位置
                        Animation.RELATIVE_TO_SELF,0,   //y起始位置
                        Animation.RELATIVE_TO_SELF,-0.3f);   //y终止位置

        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setDuration(6000);
        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }


    public static AnimationSet cretateAlphaAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);        //开始的透明度，结束的透明度(透明到不透明)
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatMode(Animation.REVERSE);                //Animation.REVERSE反向开始，Animation.RESTART重新开始
        alphaAnimation.setRepeatCount(-1);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    public static AnimationSet createRotateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        //1、开始角度    2、结束角度后面    3、确定旋转中心的位置
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(4000);
        rotateAnimation.setRepeatMode(Animation.REVERSE);                //Animation.REVERSE反向开始，Animation.RESTART重新开始
        rotateAnimation.setRepeatCount(-1);
        animationSet.addAnimation(rotateAnimation);
        return animationSet;
    }
}
