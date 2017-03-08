package cn.foxnickel.listentome.fragment.subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_exam, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.exam_recycler);
        mRecyclerAdapter = new RecyclerAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return mRootView;
    }

}
