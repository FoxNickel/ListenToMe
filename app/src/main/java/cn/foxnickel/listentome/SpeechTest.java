package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.iflytek.cloud.SpeechUtility;

import cn.foxnickel.listentome.utils.voice.result.Result;
import cn.foxnickel.listentome.utils.voice.result.xml.XmlResultParser;

public class SpeechTest extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mRecord, mStopRecord, mRawAudio;
    private SpeechEvaluator mSpeechEvaluator;
    private Toast mToast;
    private TextView mContent, mScore, mShowDetail;
    private String mResult;
    private EditText mDetailResult;
    private Result readableResult;
    private Button mPrevious, mNext;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_test);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58ca19e8");//语音引擎初始化
        mSpeechEvaluator = SpeechEvaluator.createEvaluator(this, null);//评测引擎初始化
        back();//返回上级
        initView();
        setListener();
    }

    private void setListener() {
        mRecord.setOnClickListener(this);
        mContent.setOnClickListener(this);
        mScore.setOnClickListener(this);
        mStopRecord.setOnClickListener(this);
        mRawAudio.setOnClickListener(this);
        mShowDetail.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
        mNext.setOnClickListener(this);
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

    private void initView() {
        mRecord = (ImageView) findViewById(R.id.begin_record);
        mContent = (TextView) findViewById(R.id.text_content);
        mScore = (TextView) findViewById(R.id.text_score);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mStopRecord = (ImageView) findViewById(R.id.stop_record);
        mRawAudio = (ImageView) findViewById(R.id.raw_radio);
        mDetailResult = (EditText) findViewById(R.id.text_detail);
        mShowDetail = (TextView) findViewById(R.id.show_detail);
        mPrevious = (Button) findViewById(R.id.bt_previous);
        mNext = (Button) findViewById(R.id.bt_next);
    }

    @Override
    public void onClick(View v) {

        if (null == mSpeechEvaluator) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("语音引擎初始化失败");
            return;
        }

        switch (v.getId()) {
            case R.id.begin_record:
                setParams();//设置参数
                mDetailResult.setText("");
                String evaluateString = mContent.getText().toString();//获取要评测的句子
                mSpeechEvaluator.startEvaluating(evaluateString, null, mEvaluatorListener);//开始评测
                mStopRecord.setVisibility(View.VISIBLE);
                break;
            case R.id.stop_record:
                if (mSpeechEvaluator.isEvaluating()) {
                    showTip("评测已停止，等待结果中...");
                    mSpeechEvaluator.stopEvaluating();
                }
                break;
            case R.id.raw_radio:
                showTip("原声播放中:");
                break;
            case R.id.show_detail:
                mDetailResult.setText(readableResult.toString());
                mShowDetail.setVisibility(View.GONE);
                break;
            case R.id.bt_previous:
                showTip("Previous");
                mContent.setText("No,I'm not mi fan.");
                break;
            case R.id.bt_next:
                showTip("Next");
                mContent.setText("Yes,I'm mi fan.");
                break;
        }
    }

    private void showTip(String str) {
        if (!TextUtils.isEmpty(str)) {
            mToast.setText(str);
            mToast.show();
        }
    }

    private void setParams() {
        //设置评测语言
        String language = "en_us";
        // 设置需要评测的类型
        String category = "read_sentence";
        // 设置结果等级（中文仅支持complete）
        String result_level = "complete";
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = "3000";
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = "1800";
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = "-1";

        mSpeechEvaluator.setParameter(SpeechConstant.LANGUAGE, language);
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mSpeechEvaluator.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mSpeechEvaluator.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mSpeechEvaluator.setParameter(SpeechConstant.RESULT_LEVEL, result_level);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        /*mSpeechEvaluator.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/ise.wav");*/
    }

    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());
                mResult = builder.toString();
                parseResult();
            }
        }

        private void parseResult() {
            /*解析结果*/
            if (!TextUtils.isEmpty(mResult)) {
                XmlResultParser resultParser = new XmlResultParser();//解析器
                readableResult = resultParser.parse(mResult);//解析结果
                if (null != readableResult) {
                    /*float score = Float.valueOf(readableResult.toString());
                    double grade = (score / 5.0) * 100;
                    mScore.setText(String.valueOf((int) grade) + " Perfect!!!");*/
                    //mDetailResult.setText(readableResult.toString());
                    mScore.setVisibility(View.VISIBLE);
                    mStopRecord.setVisibility(View.INVISIBLE);
                    mShowDetail.setVisibility(View.VISIBLE);
                    String totalScore = readableResult.toString().substring(readableResult.toString().indexOf("总分:"),
                            readableResult.toString().indexOf("单词")).substring(readableResult.toString().indexOf("总分:") + 3).trim();
                    float score = Float.valueOf(totalScore);
                    StringBuilder str = new StringBuilder();
                    str.append((int) (score + 0.5));
                    if (score >= 90) {
                        str.append(" Perfect!!!");
                    } else if (score >= 80) {
                        str.append(" Good!!");
                    } else if (score >= 70) {
                        str.append(" Not Bad!");
                    } else if (score >= 60) {
                        str.append(" Not Very Good.");
                    } else {
                        str.append(" Bad!!!");
                    }
                    mScore.setText(str.toString());
                } else {
                    showTip("解析结果为空");
                }
            }
        }

        @Override
        public void onError(SpeechError error) {
            if (error != null) {
                showTip("error:" + error.getErrorCode() + "," + error.getErrorDescription());
            } else {

            }
        }

        @Override
        public void onBeginOfSpeech() {
            showTip("请开始讲话");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前音量：" + volume);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

}
