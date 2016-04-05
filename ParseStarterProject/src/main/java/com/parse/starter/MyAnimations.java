package com.parse.starter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * Utility class which contains helper function for animation
 */
public class MyAnimations {

    public static void animatePlace(RecyclerView.ViewHolder holder, int lastPosition, int currentPosition, Context context) {
        if (currentPosition > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            animation.setStartOffset((currentPosition % 5) *100);
            holder.itemView.startAnimation(animation);
        }
    }
}
