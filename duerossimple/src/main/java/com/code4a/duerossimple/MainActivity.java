package com.code4a.duerossimple;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.code4a.dueroslib.DuerosConfig;
import com.code4a.dueroslib.DuerosPlatformManager;
import com.code4a.dueroslib.IDuerosPlatform;
import com.code4a.dueroslib.androidsystemimpl.webview.BaseWebView;
import com.code4a.dueroslib.http.HttpConfig;
import com.code4a.dueroslib.util.CommonUtil;
import com.code4a.dueroslib.util.FileUtil;
import com.code4a.dueroslib.util.LogUtil;
import com.code4a.dueroslib.util.NetWorkUtil;
import com.code4a.dueroslib.wakeup.WakeUpSuccessCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button voiceButton;
    private TextView textViewTimeStopListen;
    private TextView textViewRenderVoiceInputText;
    private Button pauseOrPlayButton;
    private BaseWebView webView;
    private LinearLayout mTopLinearLayout;
    private String mHtmlUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dcs_sample_activity_main);
        initView();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(MainActivity.this, SimpleService.class));
    }

    private void setListeners() {
        DuerosPlatformManager.addOnRecordingListener(new DuerosConfig.OnRecordingListener() {
            @Override
            public void startRecording() {
                voiceButton.setText(getResources().getString(R.string.start_record));
            }

            @Override
            public void stopRecording() {
                voiceButton.setText(getResources().getString(R.string.stop_record));
            }

            @Override
            public void onRecording(String text) {
                textViewRenderVoiceInputText.setText(text);
            }
        });
        DuerosPlatformManager.addWakeUpSuccessCallback(new WakeUpSuccessCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,
                        getResources().getString(R.string.wakeup_succeed),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void initView() {
        Button openLogBtn = (Button) findViewById(R.id.openLogBtn);
        openLogBtn.setOnClickListener(this);
        voiceButton = (Button) findViewById(R.id.voiceBtn);
        voiceButton.setOnClickListener(this);

        textViewTimeStopListen = (TextView) findViewById(R.id.id_tv_time_0);
        textViewRenderVoiceInputText = (TextView) findViewById(R.id.id_tv_RenderVoiceInputText);
        mTopLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);

        webView = new BaseWebView(MainActivity.this.getApplicationContext());
        webView.setWebViewClientListen(new BaseWebView.WebViewClientListener() {
            @Override
            public BaseWebView.LoadingWebStatus shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截处理不让其点击
                return BaseWebView.LoadingWebStatus.STATUS_TRUE;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!url.equals(mHtmlUrl) && !"about:blank".equals(mHtmlUrl)) {
                    DuerosPlatformManager.getWebView().linkClicked(url);
                }

                mHtmlUrl = url;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });
        mTopLinearLayout.addView(webView);
        if (DuerosPlatformManager.initDcsFramework()) {
            DuerosPlatformManager.setWebView(webView);
        }

        Button mPreviousSongBtn = (Button) findViewById(R.id.previousSongBtn);
        pauseOrPlayButton = (Button) findViewById(R.id.pauseOrPlayBtn);
        Button mNextSongBtn = (Button) findViewById(R.id.nextSongBtn);
        mPreviousSongBtn.setOnClickListener(this);
        pauseOrPlayButton.setOnClickListener(this);
        mNextSongBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voiceBtn:
                DuerosPlatformManager.changeRecording();
                break;
            case R.id.openLogBtn:
                openAssignFolder(FileUtil.getLogFilePath());
                break;
            case R.id.previousSongBtn:
                DuerosPlatformManager.startAudioRecord();
                break;
            case R.id.nextSongBtn:
                DuerosPlatformManager.stopAudioRecord();
                break;
            case R.id.pauseOrPlayBtn:
                break;
            default:
                break;
        }
    }


    /**
     * 打开日志
     *
     * @param path 文件的绝对路径
     */
    private void openAssignFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.no_log),
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "text/plain");
        try {
            startActivity(Intent.createChooser(intent,
                    getResources().getString(R.string.open_file_title)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DuerosPlatformManager.uninitDcsFramework();
    }
}
