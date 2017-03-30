package cn.foxnickel.listentome.fragment.subfragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.QuestionRecyclerAdapter;
import cn.foxnickel.listentome.bean.ListenExamBean;
import cn.foxnickel.listentome.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private QuestionRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences mPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_exam, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.exam_recycler);
        ArrayList<ListenExamBean> list = new ArrayList<>();
        list.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH_CET4_2013_6_2, "大学英语四级", "2013年六月英语四级听力真题", "0"));
        list.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH_CET4_2016_6_1, "大学英语四级", "2016年六月英语四级听力真题", "0"));
        list.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH_CET6_2016_6_1, "大学英语六级", "2016年六月英语六级听力真题", "0"));
        list.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH_CET6_2013_6_1, "大学英语六级", "2013年六月英语六级听力真题", "0"));
        mRecyclerAdapter = new QuestionRecyclerAdapter(getContext(),list);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mPreferences.getInt("grade", -1) != -1) {
            list.get(0).setGrade(mPreferences.getInt("grade", 0) + "分");
            mRecyclerAdapter.notifyItemChanged(0);
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setOnItemClickListener(new QuestionRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        return mRootView;
    }

}
