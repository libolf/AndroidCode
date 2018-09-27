package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.fragment.FragmentDialog;
import com.libok.androidcode.util.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liboK  2018-09-26 0026 下午 05:19
 * 指纹识别
 */
public class FingerprintActivity extends AppCompatActivity {

    @BindView(R.id.fingerprint_normal)
    Button mFingerprintNormal;
    @BindView(R.id.fingerprint_fragment_dialog)
    Button mFingerprintFragmentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fingerprint_normal, R.id.fingerprint_fragment_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fingerprint_normal:
                DialogUtils.showNormalDialog(this, DialogUtils.TYPE_WHITE_CONFIRM,
                        "标题", "Message", null);
                break;
            case R.id.fingerprint_fragment_dialog:
                FragmentDialog dialog = FragmentDialog.newInstance("请验证指纹", "开始验证");
                dialog.show(getSupportFragmentManager(), "Fingerprint");
                break;
        }
    }
}
