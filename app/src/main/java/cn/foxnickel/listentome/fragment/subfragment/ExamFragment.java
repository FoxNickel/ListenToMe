package cn.foxnickel.listentome.fragment.subfragment;


import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_exam, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.exam_recycler);
        ArrayList<ListenExamBean> list = new ArrayList<>();
        list.add(new ListenExamBean(Constants.PATH,"sdgfsdfsdfsdfsdf","sgsdfsdfsdfsdf","78"));
        list.add(new ListenExamBean(Constants.PATH,"sdgfsdfsdfsdfsdf","sgsdfsdfsdfsdf","78"));
        list.add(new ListenExamBean(Constants.PATH,"sdgfsdfsdfsdfsdf","sgsdfsdfsdfsdf","78"));
        list.add(new ListenExamBean(Constants.PATH,"sdgfsdfsdfsdfsdf","sgsdfsdfsdfsdf","78"));
        list.add(new ListenExamBean(Constants.PATH,"sdgfsdfsdfsdfsdf","sgsdfsdfsdfsdf","78"));
        mRecyclerAdapter = new QuestionRecyclerAdapter(getContext(),list);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return mRootView;
    }

}
