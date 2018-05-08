package com.example.xyzreader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.example.xyzreader.R;

import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ActivityArticleDetailBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    public static final String ARG_ARTICLES_IDS = "articles_ids";
    public static final String ARG_ARTICLE_POSITION = "article_position";
    public static final String ARG_ARTICLE_ID = "article_id";

    private long[] mArticlesIds;
    private long mStartId;
    private long mSelectedItemId;
    private int mSelectedItemPosition;

    private ActivityArticleDetailBinding mBinding;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mBinding.pager.setAdapter(mPagerAdapter);
        mBinding.pager.setPageMargin((int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        mBinding.pager.setPageMarginDrawable(new ColorDrawable(0x22000000));
        mBinding.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                mSelectedItemPosition = position;
            }
        });

        initToolbar();

        parseData();
        mPagerAdapter.notifyDataSetChanged();

        // Select the start ID
        if (mStartId > 0) {
            for (int i=0; i<mArticlesIds.length; i++) {
                if (mArticlesIds[i] == mStartId) {
                    mBinding.pager.setCurrentItem(i, false);
                    break;
                }
            }
            mStartId = 0;
        }

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                mStartId = mArticlesIds[mSelectedItemPosition];
                mSelectedItemId = mStartId;
            }
        }
    }

    private void parseData() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mArticlesIds = intent.getLongArrayExtra(ARG_ARTICLES_IDS);
            mSelectedItemPosition = intent.getIntExtra(ARG_ARTICLE_POSITION, -1);
            mSelectedItemId = intent.getLongExtra(ARG_ARTICLE_ID, -1);
        }
        else {
            mArticlesIds = null;
            mSelectedItemPosition = -1;
        }
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onSupportNavigateUp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticleDetailFragment.newInstance(mArticlesIds[position]);
        }

        @Override
        public int getCount() {
            return (mArticlesIds != null) ? mArticlesIds.length : 0;
        }
    }



    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
