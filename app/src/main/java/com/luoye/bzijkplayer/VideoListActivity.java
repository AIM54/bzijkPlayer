package com.luoye.bzijkplayer;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tv.danmaku.ijk.media.example.activities.VideoActivity;

public class VideoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private ListView mListView;
    private String project[] = new String[]{
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME
    };
    private SimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mListView = findViewById(R.id.lv_main);
        initData();
    }

    private void initData() {
        LoaderManager manager = LoaderManager.getInstance(this);
        manager.initLoader(110, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, project, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.i_video_info,
                data,
                new String[]{MediaStore.Video.Media.DISPLAY_NAME},
                new int[]{R.id.tv_name}, 0);
        mListView.setAdapter(mCursorAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Cursor cursor = (Cursor) mCursorAdapter.getItem(position);
        final String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        VideoActivity.intentTo(this, filePath, videoName);
    }
}
