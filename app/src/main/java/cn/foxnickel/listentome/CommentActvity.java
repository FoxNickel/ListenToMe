package cn.foxnickel.listentome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ImageUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import cn.foxnickel.listentome.adapter.CommentsAdapter;
import cn.foxnickel.listentome.bean.Comment;

public class CommentActvity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mUsername, mTime, mContent;
    private EditText mComments;
    private Button mDoComment;
    private ImageView mUserPicture;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentsAdapter mAdapter;
    private List<Comment> mCommentList;
    private final String TAG = getClass().getSimpleName();
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_actvity);
        mIntent = getIntent();
        getCommentList();
        back();
        initView();
    }

    private void initView() {
        mUsername = (TextView) findViewById(R.id.username);
        mTime = (TextView) findViewById(R.id.detail_time);
        mContent = (TextView) findViewById(R.id.contents);
        mComments = (EditText) findViewById(R.id.text_comment);
        mDoComment = (Button) findViewById(R.id.bt_comment);
        mUserPicture = (ImageView) findViewById(R.id.head_picture);
        Bitmap userPic = ImageUtils.getBitmap(getResources(), R.drawable.pic7);
        userPic = ImageUtils.toRound(userPic);
        mUserPicture.setImageBitmap(userPic);
        mUsername.setText(mIntent.getStringExtra("username"));
        mTime.setText(mIntent.getStringExtra("time"));
        mContent.setText(mIntent.getStringExtra("contents"));
        mComments.setOnClickListener(this);
        mDoComment.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CommentsAdapter(this, mCommentList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getCommentList() {
        Gson gson = new Gson();
        String finalStr = formatJsonString(mIntent.getStringExtra("commentJsonStr"));
        Comment[] comments = gson.fromJson(finalStr, Comment[].class);
        for (int i = 0; i < comments.length; i++) {
            Log.i(TAG, "doInBackground: comments: " + comments[i].toString());
        }
        mCommentList = Arrays.asList(comments);
        Log.i(TAG, "getCommentList: mCommentList: " + mCommentList.size());
    }

    private String formatJsonString(String string) {
        String temp = string.replace("\\", "");
        String finalStr = temp.substring(1, temp.indexOf("]") + 1);
        return finalStr;
    }

    private void back() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回上级的箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//将actionbar原有的标题去掉
        /*返回上级*/
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_comment:
                ToastUtils.showShortToast("发表评论");
                break;
            case R.id.text_comment:
                ToastUtils.showShortToast(mComments.getText());
                break;
        }
    }

}
