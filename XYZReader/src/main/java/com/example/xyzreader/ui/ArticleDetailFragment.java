package com.example.xyzreader.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.FragmentArticleDetailBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private static final float PARALLAX_FACTOR = 1.25f;

    private long mArticleId;
    private Article mArticle;
    private int mMutedColor = 0xFF333333;
    private ColorDrawable mStatusBarColorDrawable;

    private int mTopInset;
    private int mScrollY;
    private boolean mIsCard = false;
    private int mStatusBarFullOpacityBottom;

    @Inject
    ArticlesDao articlesDao;

    // Use default locale format
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    private FragmentArticleDetailBinding mBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, id);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
            mArticleId = getArguments().getLong(ARG_ITEM_ID);

        mIsCard = getResources().getBoolean(R.bool.detail_is_card);
        mStatusBarFullOpacityBottom = getResources().getDimensionPixelSize(
                R.dimen.detail_card_top_margin);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.fragment_article_detail, container, false);
        mBinding.drawInsetsFrameLayout.setOnInsetsCallback(new DrawInsetsFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                mTopInset = insets.top;
            }
        });

        mBinding.scrollview.setCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged() {
                mScrollY = mBinding.scrollview.getScrollY();
                mBinding.photoContainer.setTranslationY((int) (mScrollY - mScrollY / PARALLAX_FACTOR));
                updateStatusBar();
            }
        });

        mStatusBarColorDrawable = new ColorDrawable(0);
        mBinding.shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)));
            }
        });
        updateStatusBar();

        articlesDao.getArticleAsObservable(mArticleId).observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                mArticle = article;
                bindViews();
                updateStatusBar();
            }
        });

        return mBinding.getRoot();
    }

    private void updateStatusBar() {
        int color = 0;
        if (mBinding.photo != null && mTopInset != 0 && mScrollY > 0) {
            float f = progress(mScrollY,
                    mStatusBarFullOpacityBottom - mTopInset * 3,
                    mStatusBarFullOpacityBottom - mTopInset);
            color = Color.argb((int) (255 * f),
                    (int) (Color.red(mMutedColor) * 0.9),
                    (int) (Color.green(mMutedColor) * 0.9),
                    (int) (Color.blue(mMutedColor) * 0.9));
        }
        mStatusBarColorDrawable.setColor(color);
        mBinding.drawInsetsFrameLayout.setInsetBackground(mStatusBarColorDrawable);
    }

    static float progress(float v, float min, float max) {
        return constrain((v - min) / (max - min), 0, 1);
    }

    static float constrain(float val, float min, float max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    private void bindViews() {
        mBinding.articleByline.setMovementMethod(new LinkMovementMethod());
        mBinding.articleBody.setTypeface(
                Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        if (mArticle != null) {
            mBinding.getRoot().setAlpha(0);
            mBinding.getRoot().setVisibility(View.VISIBLE);
            mBinding.getRoot().animate().alpha(1);
            mBinding.articleTitle.setText(mArticle.getTitle());

            if (!mArticle.getPublishedDate().before(START_OF_EPOCH.getTime())) {
                mBinding.articleByline.setText(Html.fromHtml(
                        DateUtils.getRelativeTimeSpanString(
                                mArticle.getPublishedDate().getTime(),
                                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                                DateUtils.FORMAT_ABBREV_ALL).toString()
                                + " by <font color='#ffffff'>"
                                + mArticle.getAuthor()
                                + "</font>"));

            } else {
                // If date is before 1902, just show the string
                mBinding.articleByline.setText(Html.fromHtml(
                        outputFormat.format(mArticle.getPublishedDate()) + " by " +
                                "<font color='#ffffff'>" + mArticle.getAuthor() + "</font>"));

            }
            mBinding.articleBody.setText(Html.fromHtml(mArticle.getBody().replaceAll("(\r\n|\n)", "<br />")));
            ImageLoaderHelper.getInstance(getActivity()).getImageLoader()
                    .get(mArticle.getPhotoUrl(), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();
                            if (bitmap != null) {
                                Palette p = Palette.generate(bitmap, 12);
                                mMutedColor = p.getDarkMutedColor(0xFF333333);
                                mBinding.photo.setImageBitmap(imageContainer.getBitmap());
                                mBinding.metaBar.setBackgroundColor(mMutedColor);
                                updateStatusBar();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
        } else {
            mBinding.getRoot().setVisibility(View.GONE);
            mBinding.articleTitle.setText("N/A");
            mBinding.articleByline.setText("N/A" );
            mBinding.articleBody.setText("N/A");
        }
    }
}
