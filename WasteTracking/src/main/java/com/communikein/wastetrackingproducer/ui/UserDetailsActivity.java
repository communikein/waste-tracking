package com.communikein.wastetrackingproducer.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.communikein.wastetrackingproducer.data.model.Producer;
import com.communikein.wastetrackingproducer.viewmodel.UserDetailViewModel;
import com.communikein.wastetrackingproducer.viewmodel.factory.UserDetailViewModelFactory;
import com.communikein.wastetrackingproducer.R;
import com.communikein.wastetrackingproducer.databinding.ActivityUserDetailsBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class UserDetailsActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    /* */
    @Inject
    UserDetailViewModelFactory viewModelFactory;

    /* */
    public UserDetailViewModel mViewModel;


    public static final String ARG_USER = "user";

    Producer mProducer;

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

        mViewModel.getUser().observe(this, this::updateUI);
    }


    private void initUi() {
        initColors();
        initToolbar();
    }

    private void initColors() { }

    private void parseData() { }

    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void updateUI(Producer producer) {
        if (producer != null) {
            mBinding.userFamilyCount.setText(String.valueOf(producer.getFamilySize()));
            mBinding.userLocation.setText("City of " + producer.getLocation());
        }
    }

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
