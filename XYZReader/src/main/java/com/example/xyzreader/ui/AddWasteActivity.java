package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.RadioGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.databinding.ActivityAddWasteBinding;
import com.example.xyzreader.viewmodel.AddWasteViewModel;
import com.example.xyzreader.viewmodel.factory.AddWasteViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class AddWasteActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    /* */
    @Inject
    AddWasteViewModelFactory viewModelFactory;

    /* */
    public AddWasteViewModel mViewModel;

    private ActivityAddWasteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_waste);

        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(AddWasteViewModel.class);

        initUi();
    }

    private void initUi() {
        initColors();
        initToolbar();

        mBinding.labelVolumeMeters.setText(Html.fromHtml("m<span style=\"font-size:10pt\"><sup>3</sup></span>"));
        mBinding.fabAdd.setOnClickListener(v -> addWaste());
        mBinding.radioWasteTypeGroup.setOnCheckedChangeListener(this::onWasteTypeChanged);
        mBinding.radioWastePlastic.setChecked(true);
    }

    public void onWasteTypeChanged(RadioGroup group, int checkedId) {
        String wasteType;
        switch (checkedId) {
            case R.id.radio_waste_glass:
                wasteType = Waste.WASTE_TYPE_GLASS;
                break;
            case R.id.radio_waste_metal:
                wasteType = Waste.WASTE_TYPE_METAL;
                break;
            case R.id.radio_waste_organic:
                wasteType = Waste.WASTE_TYPE_ORGANIC;
                break;
            case R.id.radio_waste_paper:
                wasteType = Waste.WASTE_TYPE_PAPER;
                break;
            case R.id.radio_waste_plastic:
                wasteType = Waste.WASTE_TYPE_PLASTIC;
                break;
            default:
                wasteType = "";
                break;
        }

        mBinding.labelWasteId.setText("waste-" + wasteType + "-");
    }

    private void addWaste() {
        String wasteType;
        switch (mBinding.radioWasteTypeGroup.getCheckedRadioButtonId()) {
            case R.id.radio_waste_glass:
                wasteType = Waste.WASTE_TYPE_GLASS;
                break;
            case R.id.radio_waste_metal:
                wasteType = Waste.WASTE_TYPE_METAL;
                break;
            case R.id.radio_waste_organic:
                wasteType = Waste.WASTE_TYPE_ORGANIC;
                break;
            case R.id.radio_waste_paper:
                wasteType = Waste.WASTE_TYPE_PAPER;
                break;
            case R.id.radio_waste_plastic:
                wasteType = Waste.WASTE_TYPE_PLASTIC;
                break;
            default:
                wasteType = "";
                break;
        }
        double wasteWeight = Double.parseDouble(mBinding.textWasteWeight.getText().toString());
        double wasteVolume = Double.parseDouble(mBinding.textWasteVolume.getText().toString());
        String wasteParams = mBinding.textWasteParams.getText().toString();
        String wasteQuality = "good";
        String wasteId = "waste:" + wasteType + ":" + mBinding.textWasteId.getText().toString();

        Waste waste = new Waste(wasteId, wasteType, wasteWeight, wasteVolume,
                wasteQuality, wasteParams);

        mViewModel.addWaste(waste);
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


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
