package cn.foxnickel.listentome.fragment.subfragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ImageUtils;

import cn.foxnickel.listentome.ListenExcerciseActivity;
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
        mEasyCard.setOnClickListener(this);
        mMiddleCard.setOnClickListener(this);
        mHardCard.setOnClickListener(this);
    }

    private void initView() {
        Bitmap easyPic = ImageUtils.getBitmap(getResources(), R.drawable.easy_mode);
        easyPic = ImageUtils.toRound(easyPic);
        Bitmap middleMode = ImageUtils.getBitmap(getResources(), R.drawable.middle_mode);
        middleMode = ImageUtils.toRound(middleMode);
        Bitmap hardMode = ImageUtils.getBitmap(getResources(), R.drawable.hard_mode);
        hardMode = ImageUtils.toRound(hardMode);
        mEasyImage = (ImageView) mRootView.findViewById(R.id.easy_image);
        mMiddleImage = (ImageView) mRootView.findViewById(R.id.middle_image);
        mHardImage = (ImageView) mRootView.findViewById(R.id.hard_image);
        mEasyImage.setImageBitmap(easyPic);
        mMiddleImage.setImageBitmap(middleMode);
        mHardImage.setImageBitmap(hardMode);
        mEasyCard = (CardView) mRootView.findViewById(R.id.easy_mode);
        mMiddleCard = (CardView) mRootView.findViewById(R.id.middle_mode);
        mHardCard = (CardView) mRootView.findViewById(R.id.hard_mode);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.easy_mode:
                startActivity(new Intent(getActivity(), ListenExcerciseActivity.class));
                break;
            case R.id.middle_mode:
                startActivity(new Intent(getActivity(), ListenExcerciseActivity.class));
                break;
            case R.id.hard_mode:
                startActivity(new Intent(getActivity(), ListenExcerciseActivity.class));
                break;
        }
    }
}
