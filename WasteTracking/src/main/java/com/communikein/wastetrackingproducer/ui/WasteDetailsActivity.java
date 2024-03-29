package com.communikein.wastetrackingproducer.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.communikein.wastetrackingproducer.data.FakeDataHelper;
import com.communikein.wastetrackingproducer.data.model.Producer;
import com.communikein.wastetrackingproducer.data.model.Waste;
import com.communikein.wastetrackingproducer.viewmodel.WasteDetailViewModel;
import com.communikein.wastetrackingproducer.viewmodel.factory.WasteDetailViewModelFactory;
import com.communikein.wastetrackingproducer.R;
import com.communikein.wastetrackingproducer.databinding.ActivityWasteDetailBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 *
 * The title animation has taken inspiration from the
 * http://saulmm.github.io/mastering-coordinator website
 */
public class WasteDetailsActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    /* */
    @Inject
    WasteDetailViewModelFactory viewModelFactory;

    /* */
    public WasteDetailViewModel mViewModel;


    public static final String ARG_WASTE = "waste";
    public static final String ARG_SCORE = "score";

    int textColor, shadeColorEnd, shadeStartColor, fabColor, fabIconColor;
    Waste mSelectedWaste;
    float mScore;

    private ActivityWasteDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_waste_detail);

        parseData();

        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(WasteDetailViewModel.class);

        initUi();
        updateUI();
    }


    private void initUi() {
        initColors();
        initToolbar();

        mBinding.speedView.setWithTremble(false);
    }

    private void initColors() {
        textColor = ContextCompat.getColor(WasteDetailsActivity.this,
                R.color.article_title);
        shadeColorEnd = ContextCompat.getColor(WasteDetailsActivity.this,
                R.color.theme_secondary_semi_transparent);
        shadeStartColor = ContextCompat.getColor(WasteDetailsActivity.this,
                android.R.color.transparent);
        fabColor = ContextCompat.getColor(WasteDetailsActivity.this,
                R.color.theme_secondary);
        fabIconColor = ContextCompat.getColor(WasteDetailsActivity.this,
                R.color.article_title);
    }

    private void parseData() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mSelectedWaste = intent.getParcelableExtra(ARG_WASTE);
            mScore = intent.getFloatExtra(ARG_SCORE, 0);
        } else {
            mSelectedWaste = null;
            mScore = 0;
        }
    }

    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void updateUI() {
        int feelingResource = FakeDataHelper.pickFeelingResource(mScore);

        mBinding.userScoreThumbnail.setImageResource(feelingResource);
        mBinding.speedView.setSpeedAt(mScore);

        //mBinding.rankingInfo.setText();

        if (feelingResource == R.drawable.ic_face_sad)
            mBinding.userMessage.setText(R.string.user_message_sad);
        else if (feelingResource == R.drawable.ic_face_neutral)
            mBinding.userMessage.setText(R.string.user_message_neutral);
        else
            mBinding.userMessage.setText(R.string.user_message_happy);

        if (mSelectedWaste == null) {
            Producer producer = new Producer("ASZ-123", "Elia Maracani", 0.5, "Verdello, Italia", 4);

            mBinding.scoreTopic.setText(producer.getName());
        }
        else
            mBinding.scoreTopic.setText(mSelectedWaste.printType(this));
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
