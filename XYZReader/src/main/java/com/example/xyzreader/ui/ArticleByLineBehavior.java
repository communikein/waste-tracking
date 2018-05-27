package com.example.xyzreader.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class ArticleByLineBehavior extends CoordinatorLayout.Behavior {

    private static final float VIEW_ALPHA = 1.0f;

    public ArticleByLineBehavior(){
        super();
    }

    public ArticleByLineBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency){
        return dependency instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency){

        if(child instanceof TextView && dependency instanceof CollapsingToolbarLayout){
            TextView tv = (TextView) child;
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) dependency;

            int height = collapsingToolbarLayout.getHeight();

            if (height == 200)
                tv.setAlpha(0.5f);
        }

        return false;
    }

}
