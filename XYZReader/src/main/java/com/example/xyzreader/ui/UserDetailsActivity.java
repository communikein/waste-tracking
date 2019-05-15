package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.xyzreader.R;
import com.example.xyzreader.data.FakeDataHelper;
import com.example.xyzreader.data.model.User;
import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.databinding.ActivityUserDetailsBinding;
import com.example.xyzreader.databinding.ActivityWasteDetailBinding;
import com.example.xyzreader.viewmodel.UserDetailViewModel;
import com.example.xyzreader.viewmodel.WasteDetailViewModel;
import com.example.xyzreader.viewmodel.factory.UserDetailViewModelFactory;
import com.example.xyzreader.viewmodel.factory.WasteDetailViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class UserDetailsActivity  extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    /* */
    @Inject
    UserDetailViewModelFactory viewModelFactory;

    /* */
    public UserDetailViewModel mViewModel;


    public static final String ARG_USER = "user";

    User mUser;

    private ActivityUserDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);

        parseData();

        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(UserDetailViewModel.class);

        initUi();
        updateUI();
    }


    private void initUi() {
        initColors();
        initToolbar();
    }

    private void initColors() { }

    private void parseData() { }

    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        // Change FAB icon color
        Drawable myFabSrc = getResources().getDrawable(R.drawable.logo);
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();

        int color = ContextCompat.getColor(UserDetailsActivity.this, R.color.theme_primary_dark);
        willBeWhite.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void updateUI() { }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(1);
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(1);
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
