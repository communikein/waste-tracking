package com.communikein.wastetrackingproducer.di.module;

import com.communikein.wastetrackingproducer.ui.AddWasteActivity;
import com.communikein.wastetrackingproducer.ui.BlockChainListActivity;
import com.communikein.wastetrackingproducer.ui.UserDetailsActivity;
import com.communikein.wastetrackingproducer.ui.WasteDetailsActivity;
import com.communikein.wastetrackingproducer.ui.WasteListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector()
    abstract WasteDetailsActivity contributeWasteDetailActivity();

    @ContributesAndroidInjector
    abstract WasteListActivity contributeWasteListActivity();

    @ContributesAndroidInjector
    abstract UserDetailsActivity contributeUserDetailsActivity();

    @ContributesAndroidInjector
    abstract BlockChainListActivity contributeBlockChainListActivity();

    @ContributesAndroidInjector
    abstract AddWasteActivity contributeAddWasteActivity();

}
