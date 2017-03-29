package cn.foxnickel.listentome.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.foxnickel.listentome.DataAnalysis;
import cn.foxnickel.listentome.DynamicCollectionActivity;
import cn.foxnickel.listentome.ListenCollectionActivity;
import cn.foxnickel.listentome.PersonalCenter;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.SettingsActivity;
import cn.foxnickel.listentome.SpeechCollectionActivity;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ProfileFragmet extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private View mRootView;
    private Toolbar mToolbar;
    private LinearLayout mListen, mSpeech, mCollection, mDownload, mWord, mData, mAboutUs, mPersonalInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        inflateView();
        setListener();
        return mRootView;
    }

    private void inflateView() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.action_settings);
        mListen = (LinearLayout) mRootView.findViewById(R.id.my_listen);
        mSpeech = (LinearLayout) mRootView.findViewById(R.id.my_speech);
        mCollection = (LinearLayout) mRootView.findViewById(R.id.my_collection);
        mDownload = (LinearLayout) mRootView.findViewById(R.id.my_download);
        mWord = (LinearLayout) mRootView.findViewById(R.id.my_word);
        mData = (LinearLayout) mRootView.findViewById(R.id.my_data);
        mAboutUs = (LinearLayout) mRootView.findViewById(R.id.about_us);
        mPersonalInfo = (LinearLayout) mRootView.findViewById(R.id.personal_info);
    }

    private void setListener() {
        mListen.setOnClickListener(this);
        mSpeech.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mWord.setOnClickListener(this);
        mData.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_setting) {
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
                }
                return true;
            }
        });
        mPersonalInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.my_listen:
                startActivity(new Intent(getActivity(), ListenCollectionActivity.class));
                break;
            case R.id.my_speech:
                startActivity(new Intent(getActivity(), SpeechCollectionActivity.class));
                break;
            case R.id.my_collection:
                startActivity(new Intent(getActivity(), DynamicCollectionActivity.class));
                break;
            case R.id.my_download:
                Toast.makeText(getActivity(), "Mydownload", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_word:
                Toast.makeText(getActivity(), "Myword", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_data:
                startActivity(new Intent(getActivity(), DataAnalysis.class));
                break;
            case R.id.about_us:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.developers);
                builder.setItems(R.array.developer_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.personal_info:
                startActivity(new Intent(getActivity(), PersonalCenter.class));
                break;
        }
    }
}
