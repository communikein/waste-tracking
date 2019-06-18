
package com.communikein.wastetrackingproducer.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.communikein.wastetrackingproducer.data.database.WasteDao;
import com.communikein.wastetrackingproducer.data.model.Waste;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasContentProviderInjector;

public class WasteProvider extends ContentProvider implements
        HasContentProviderInjector {

    public static final String AUTHORITY = "com.communikein.wastetrackingproducer";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int CODE_WASTE_DIR = 1000;
    private static final int CODE_WASTE_ITEM = 1001;

	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    @Inject
    DispatchingAndroidInjector<ContentProvider> dispatchingAndroidInjector;

    @Inject
    Lazy<WasteDao> wasteDao;

	static {
        MATCHER.addURI(AUTHORITY, BlockChainContract.BlockEntry.TABLE_NAME, CODE_WASTE_DIR);
        MATCHER.addURI(AUTHORITY, BlockChainContract.BlockEntry.TABLE_NAME + "/#", CODE_WASTE_ITEM);
	}

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final Context context = getContext();
        if (context == null) return null;

        Cursor cursor;
        String id;
        final int code = MATCHER.match(uri);
        switch (code) {
            case CODE_WASTE_DIR:
                cursor = wasteDao.get().getWastesAsCursor();
                cursor.setNotificationUri(context.getContentResolver(), uri);
                break;

            case CODE_WASTE_ITEM:
                id = uri.getQueryParameter(BlockChainContract.BlockEntry.COLUMN_WASTE_ID);
                cursor = wasteDao.get().getWasteAsCursor(id);
                cursor.setNotificationUri(context.getContentResolver(), uri);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return cursor;
	}

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final Context context = getContext();
        if (context == null) {
            return 0;
        }

        switch (MATCHER.match(uri)) {
            case CODE_WASTE_DIR:
                final ArrayList<Waste> articles = new ArrayList<>();
                for (ContentValues article : values)
                    articles.add(Waste.fromContentValues(article));

                return wasteDao.get().addWaste(articles).length;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
        final Context context = getContext();
        if (context == null) {
            return null;
        }

        long id;
        switch (MATCHER.match(uri)) {
            case CODE_WASTE_ITEM:
                id = wasteDao.get().addWaste(Waste.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final Context context = getContext();
        if (context == null) return 0;

        int count;
        switch (MATCHER.match(uri)) {
            case CODE_WASTE_DIR:
                count = wasteDao.get().deleteWastes();
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case CODE_WASTE_ITEM:
                count = wasteDao.get().deleteWaste(String.valueOf(ContentUris.parseId(uri)));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
	}



    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        return dispatchingAndroidInjector;
    }
}
