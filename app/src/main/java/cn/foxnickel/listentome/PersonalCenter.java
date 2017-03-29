package cn.foxnickel.listentome;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ImageUtils;

public class PersonalCenter extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private ImageView mBack, mEdit, mUserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.Back);
        mEdit = (ImageView) findViewById(R.id.edit_info);
        mUserPic = (ImageView) findViewById(R.id.user_pic);
        mBack.setScaleX(0.5f);
        mBack.setScaleY(0.5f);
        mEdit.setScaleX(0.5f);
        mEdit.setScaleY(0.5f);
        Bitmap userPic = ImageUtils.getBitmap(getResources(), R.drawable.pic9);
        userPic = ImageUtils.toRound(userPic);
        mUserPic.setImageBitmap(userPic);
        mBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Back:
                this.finish();
                break;
        }
    }
}
