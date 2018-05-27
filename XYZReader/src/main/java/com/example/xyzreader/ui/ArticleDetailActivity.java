package com.example.xyzreader.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.xyzreader.R;

import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ActivityArticleDetailBinding;
import com.example.xyzreader.viewmodel.ArticleDetailViewModel;
import com.example.xyzreader.viewmodel.factory.ArticleDetailViewModelFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

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

    /* */
    @Inject
    ArticleDetailViewModelFactory viewModelFactory;

    /* */
    public ArticleDetailViewModel mViewModel;

    public static final String ARG_ARTICLE = "article";
    public static final String ARG_ARTICLE_POSITION = "article_position";
    public static final String ARG_ARTICLE_ID = "article_id";

    int textColor, shadeColorEnd, shadeStartColor, fabColor, fabIconColor;

    // Use default locale format
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    private int mSelectedItemPosition;
    long mSelectedArticleId;

    private ActivityArticleDetailBinding mBinding;
    private ArticlesPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);

        parseData();
        //initTransitions();

        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ArticleDetailViewModel.class);
        mViewModel.updateSelectedArticle(mSelectedItemPosition);

        initUi();
        mViewModel.getArticles().observe(this, articles -> {
            if (articles != null) {
                mPagerAdapter.setList(articles);
                mBinding.pager.setCurrentItem(mViewModel.getSelectedArticlePosition());
            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                mPagerAdapter.instantiateItem(mBinding.pager, mViewModel.getSelectedArticlePosition());

                // Map the shared elements name to the respective views.
                View view = mBinding.getRoot();
                sharedElements.put(names.get(0), view.findViewById(R.id.article_image_thumbnail));
                sharedElements.put(names.get(1), view.findViewById(R.id.article_title));
                sharedElements.put(names.get(2), view.findViewById(R.id.article_byline));
            }
        });
    }

    private void initUi() {
        initColors();

        mBinding.shareFab.setOnClickListener(view ->
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(ArticleDetailActivity.this)
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)))
        );

        initPager();
        initToolbar();
    }

    private void initColors() {
        textColor = ContextCompat.getColor(ArticleDetailActivity.this,
                R.color.article_title);
        shadeColorEnd = ContextCompat.getColor(ArticleDetailActivity.this,
                R.color.theme_accent_semi_transparent);
        shadeStartColor = ContextCompat.getColor(ArticleDetailActivity.this,
                android.R.color.transparent);
        fabColor = ContextCompat.getColor(ArticleDetailActivity.this,
                R.color.theme_accent);
        fabIconColor = ContextCompat.getColor(ArticleDetailActivity.this,
                R.color.article_title);
    }

    private void initTransitions() {
        ViewCompat.setTransitionName(mBinding.articleImageThumbnail,
                getString(R.string.transition_article_image) + mSelectedArticleId);
        ViewCompat.setTransitionName(mBinding.articleTitle,
                getString(R.string.transition_article_title) + mSelectedArticleId);
        ViewCompat.setTransitionName(mBinding.articleByline,
                getString(R.string.transition_article_byline) + mSelectedArticleId);
    }

    private void initPager() {
        mPagerAdapter = new ArticlesPagerAdapter(getSupportFragmentManager());
        mBinding.pager.setAdapter(mPagerAdapter);
        mBinding.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        mBinding.shareFab.show();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (!mViewModel.isFirstArticleSelected() && !mViewModel.isLastArticleSelected())
                            mBinding.shareFab.hide();
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Article selectedArticle;
                // if the selected article is the following one
                if (mViewModel.getSelectedArticlePosition() < position)
                    selectedArticle = mViewModel.getNextArticle();
                else if (mViewModel.getSelectedArticlePosition() > position)
                    selectedArticle = mViewModel.getPreviousArticle();
                else
                    selectedArticle = mViewModel.getSelectedArticle();

                if (selectedArticle != null)
                    updateUI(selectedArticle);
            }
        });
    }

    private void parseData() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mSelectedItemPosition = intent.getIntExtra(ARG_ARTICLE_POSITION, -1);
            mSelectedArticleId = intent.getLongExtra(ARG_ARTICLE_ID, -1);
        }
        else {
            mSelectedItemPosition = -1;
            mSelectedArticleId = -1;
        }
    }

    private void initToolbar() {
        //make translucent statusBar on kitkat devices
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //mBinding.articleImageThumbnail.setTransitionName();

        mBinding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int appBarMaxHeight = getResources()
                    .getDimensionPixelSize(R.dimen.app_bar_expanded_height);
            int toolbarBackground = (verticalOffset >= appBarMaxHeight) ? fabColor : shadeStartColor;
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void updateUI(Article article) {
        Picasso.get()
                .load(article.getPhotoUrl())
                .noFade()
                .into(target);

        mBinding.articleTitle.setText(article.getTitle());
        mBinding.articleByline.setMovementMethod(new LinkMovementMethod());
        if (!article.getPublishedDate().before(START_OF_EPOCH.getTime())) {
            mBinding.articleByline.setText(Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            article.getPublishedDate().getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString()
                            + " by <font color='#ffffff'>"
                            + article.getAuthor()
                            + "</font>"));

        } else {
            // If date is before 1902, just show the string
            mBinding.articleByline.setText(Html.fromHtml(
                    outputFormat.format(article.getPublishedDate()) + " by " +
                            "<font color='#ffffff'>" + article.getAuthor() + "</font>"));

        }
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            supportStartPostponedEnterTransition();
            mBinding.articleImageThumbnail.setImageBitmap(bitmap);

            Palette.from(bitmap)
                    .generate(palette -> {
                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                        Palette.Swatch dominantSwatch = palette.getDominantSwatch();

                        if (vibrantSwatch != null) {
                            shadeColorEnd = vibrantSwatch.getRgb();
                            textColor = vibrantSwatch.getTitleTextColor();
                            fabColor = vibrantSwatch.getRgb();
                            fabIconColor = vibrantSwatch.getTitleTextColor();
                        }
                        else if (dominantSwatch != null){
                            shadeColorEnd = dominantSwatch.getRgb();
                            textColor = dominantSwatch.getTitleTextColor();
                            fabColor = dominantSwatch.getRgb();
                            fabIconColor = dominantSwatch.getTitleTextColor();
                        }

                        mBinding.articleTitle.setTextColor(textColor);
                        mBinding.articleByline.setTextColor(textColor);

                        int colors[] = { shadeStartColor, shadeColorEnd };
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM, colors);
                        mBinding.imageShade.setBackground(gradientDrawable);

                        // Change FAB background color
                        ColorStateList colorStateList = ColorStateList.valueOf(fabColor);
                        mBinding.shareFab.setBackgroundTintList(colorStateList);

                        // Change FAB icon color
                        Drawable myFabSrc = getResources().getDrawable(R.drawable.ic_share);
                        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
                        willBeWhite.mutate().setColorFilter(fabIconColor, PorterDuff.Mode.MULTIPLY);
                        mBinding.shareFab.setImageDrawable(willBeWhite);

                        // Change CollapsingToolbar scrim color
                        mBinding.collapsingToolbar.setContentScrimColor(fabColor);
                    });
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            supportStartPostponedEnterTransition();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };

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
