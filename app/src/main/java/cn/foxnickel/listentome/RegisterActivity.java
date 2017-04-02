package cn.foxnickel.listentome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.SMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;
import cn.foxnickel.listentome.utils.AESUtils;
import cn.foxnickel.listentome.utils.OkHttpManager;
import okhttp3.Response;

/**
 * Created by Night on 2017/3/18.
 * Desc:
 */
//欠缺判断手机号是否已在数据库存在
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mVerifyButton;//发送验证码Button
    private Button mRegisterButton;//注册Button
    private EditText mPhone, mPassWord, mRePassword, mVerifyText;
    private CountDownTime mTime;
    private int smsId;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    byte[] byBuffer = new byte[200];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BmobSMS.initialize(this, "cb24fd9939e4b0cd5e9368f375a47302", new MySMSCodeListener());
        init();
        //短信验证api初始化
    }

    private void init() {
        mVerifyButton = (Button) findViewById(R.id.bt_send_verifycode);
        mRegisterButton = (Button) findViewById(R.id.bt_phone_register);
        mPhone = (EditText) findViewById(R.id.phone);
        mPassWord = (EditText) findViewById(R.id.password);
        mVerifyText = (EditText) findViewById(R.id.verifycode);
        mRePassword = (EditText) findViewById(R.id.repassword);
        mVerifyButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mTime = new CountDownTime(60000, 1000);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Utils.init(this);//使用第三方工具库前需初始化Context
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send_verifycode: {
                if (isMobileNO(mPhone.getText().toString().trim()) &&
                        isPassword(mPassWord.getText().toString().trim(), mRePassword.getText().toString().trim())) {
                    mTime.start();
                    submit();
                }

            }
            break;
            case R.id.bt_phone_register: {

                verifySmsCode();
            }

            default:
                break;
        }
    }

    private void submit() {

        BmobSMS.requestSMSCode(RegisterActivity.this, mPhone.getText().toString().trim(), "你听我说", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "ok " + smsId, Toast.LENGTH_LONG).show();
                    RegisterActivity.this.smsId = smsId;
                    Log.e("TAG", "TAG:" + smsId);
                } else {
                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class CountDownTime extends CountDownTimer {

        //构造函数  第一个参数代表总的计时时长  第二个参数代表计时间隔  单位都是毫秒
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) { //每计时一次回调一次该方法
            mVerifyButton.setClickable(false);
            mVerifyButton.setText(l / 1000 + "秒后重新开始");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            mVerifyButton.setClickable(true);
            mVerifyButton.setText(R.string.send_verifycode);
        }
    }

    public boolean isMobileNO(String mobiles) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            mPhone.setError("手机号为空");
            mPhone.requestFocus();
            return false;
        } else {
            if (!mobiles.matches(telRegex)) {
                mPhone.setError("您输入的手机号有误，请重新输入");
                mPhone.requestFocus();
                mPhone.setText("");
                return false;
            } else return true;
        }
    }

    private boolean isPassword(String password, String repassword) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(repassword)) {
            mPassWord.setError("密码为空");
            mPassWord.requestFocus();
            return false;
        } else if (!mPassWord.getText().toString().trim().equals(mRePassword.getText().toString().trim())) {
            mPassWord.setError("两次输入的密码不一致");
            mPassWord.requestFocus();
            mPassWord.setText("");
            mRePassword.setText("");
            return false;
        } else {
            if (!password.matches(regex)) {
                mPassWord.setError("密码应在8-16位之间，必须包含至少一位大写字母、小写字母，数字");
                mPassWord.requestFocus();
                mPassWord.setText("");
                mRePassword.setText("");
                return false;
            } else
                return true;
        }
    }

    class MySMSCodeListener implements SMSCodeListener {

        @Override
        public void onReceive(String content) {
            if (mVerifyText != null) {
                mVerifyText.setText(content);
            }
        }
    }

    private void verifySmsCode() {
        mEditor = mPreferences.edit();
        String number = mPhone.getText().toString();
        String code = mVerifyText.getText().toString();


        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(code)) {
            BmobSMS.verifySmsCode(RegisterActivity.this, number, code, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ToastUtils.showShortToast("验证成功");
                        if (!mPreferences.contains("key")) {
                            byte[] aes = AESUtils.initKey256();//加密的密匙
                            try {
                                mEditor.putString("key", new String(aes, "ISO-8859-1"));
                                mEditor.apply();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                        }

                        OTJ o = new OTJ();

                        try {
                            byBuffer = AESUtils.encrypt(mPassWord.getText().toString().trim().getBytes(), mPreferences.getString("key", "").getBytes("ISO-8859-1"));

                            o.setUserName(mPhone.getText().toString().trim());
                            o.setUserPwd(new String(byBuffer, "ISO-8859-1"));
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        String s1 = new Gson().toJson(o);
                        Response r = OkHttpManager.postJson("http://122.233.74.249:3000/login", s1);
                        if (r.isSuccessful()) {
                            ToastUtils.showShortToast("注册成功");
                        } else {
                            ToastUtils.showShortToast("注册失败");
                        }
                    } else {
                        ToastUtils.showShortToast("验证码有误");
                    }
                }
            });

        } else {
            ToastUtils.showShortToast("请输入手机号和验证码");
        }

    }

    class OTJ {
        String UserName, UserPwd;

        public void setUserName(String userName) {
            UserName = userName;
        }

        public void setUserPwd(String userPwd) {
            UserPwd = userPwd;
        }
    }
}
