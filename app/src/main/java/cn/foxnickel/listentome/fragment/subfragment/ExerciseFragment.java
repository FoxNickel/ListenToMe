package cn.foxnickel.listentome.fragment.subfragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ImageUtils;

import cn.foxnickel.listentome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private ImageView mEasyImage, mMiddleImage, mHardImage;
    private CardView mEasyCard, mMiddleCard, mHardCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_exercise, container, false);
        initView();
        setListener();
        return mRootView;
    }

    private void setListener() {

    }

    private void initView() {
        Bitmap levelPic = ImageUtils.getBitmap(getResources(), R.drawable.pic7);
        levelPic = ImageUtils.toRound(levelPic);
        mEasyImage = (ImageView) mRootView.findViewById(R.id.easy_image);
        mMiddleImage = (ImageView) mRootView.findViewById(R.id.middle_image);
        mHardImage = (ImageView) mRootView.findViewById(R.id.hard_image);
        mEasyImage.setImageBitmap(levelPic);
        mMiddleImage.setImageBitmap(levelPic);
        mHardImage.setImageBitmap(levelPic);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }
}
