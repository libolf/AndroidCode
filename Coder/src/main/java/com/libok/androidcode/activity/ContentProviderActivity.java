package com.libok.androidcode.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentProviderActivity extends BaseActivity {

    private static final String TAG = "ContentProviderActivity";

    @BindView(R.id.content_provider_list)
    ListView mContentProviderList;

    private CursorLoader mCursorLoader;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected String setActivityTitle() {
        return "ContentProvider";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
//        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//        }
        final SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_phone_content, null,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[]{R.id.item_phone_content_name, R.id.item_phone_content_number},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mContentProviderList.setAdapter(simpleCursorAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(21, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Log.e(TAG, "onCreateLoader: " + Thread.currentThread().getId());
                mCursorLoader = new CursorLoader(ContentProviderActivity.this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                return mCursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Log.e(TAG, "onLoadFinished: " + Thread.currentThread().getId());
                simpleCursorAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.e(TAG, "onLoaderReset: " + Thread.currentThread().getId());
                simpleCursorAdapter.swapCursor(null);
            }
        });
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void addActivityToList() {
        LApplication.addActivity(this);
    }

    @Override
    protected void removeActivityForList() {
        LApplication.removeActivity(this);
    }
}
