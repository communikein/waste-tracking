package com.example.xyzreader.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xyzreader.data.model.Article;

import java.util.ArrayList;
import java.util.List;

class ArticlesPagerAdapter extends FragmentStatePagerAdapter {

    private List<Article> mArticles;

    public ArticlesPagerAdapter(FragmentManager fm) {
        super(fm);

        this.mArticles = new ArrayList<>();
    }

    void setList(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleDetailFragment.newInstance(mArticles.get(position));
    }

    @Override
    public int getCount() {
        return (this.mArticles != null) ? this.mArticles.size() : 0;
    }

}
