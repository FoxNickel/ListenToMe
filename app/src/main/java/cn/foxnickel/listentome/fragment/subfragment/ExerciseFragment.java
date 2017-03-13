package cn.foxnickel.listentome.fragment.subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import cn.foxnickel.listentome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private Button mEasy, mMiddle, mHard, mStartExercise;
    private ImageView mModeImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_exercise, container, false);
        initView();
        setListener();
        return mRootView;
    }

    private void setListener() {
        mEasy.setOnClickListener(this);
        mMiddle.setOnClickListener(this);
        mHard.setOnClickListener(this);
        mStartExercise.setOnClickListener(this);
    }

    private void initView() {
        mEasy = (Button) mRootView.findViewById(R.id.exercise_easy);
        mMiddle = (Button) mRootView.findViewById(R.id.exercise_middle);
        mHard = (Button) mRootView.findViewById(R.id.exercise_hard);
        mModeImage = (ImageView) mRootView.findViewById(R.id.mode_image);
        mStartExercise = (Button) mRootView.findViewById(R.id.start_exercise);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.exercise_easy:
                mModeImage.setImageResource(R.drawable.exercise_easy);
                mEasy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mMiddle.setBackgroundColor(getResources().getColor(android.R.color.white));
                mHard.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
            case R.id.exercise_middle:
                mModeImage.setImageResource(R.drawable.exercise_middle);
                mMiddle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mEasy.setBackgroundColor(getResources().getColor(android.R.color.white));
                mHard.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
            case R.id.exercise_hard:
                mModeImage.setImageResource(R.drawable.exercise_hard);
                mHard.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mMiddle.setBackgroundColor(getResources().getColor(android.R.color.white));
                mEasy.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
            case R.id.start_exercise:
                Toast.makeText(getActivity(), "开始练习", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
