package com.libok.androidcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class QRCodeActivity extends BaseActivity {

    private static final String TAG = "QRCodeActivity";

    @BindView(R.id.qr_code_message)
    EditText mQrCodeMessage;
    @BindView(R.id.qr_code_create)
    Button mQrCodeCreate;
    @BindView(R.id.qr_code_recognition)
    Button mQrCodeRecognition;
    @BindView(R.id.qr_code_text)
    TextView mQrCodeText;
    @BindView(R.id.qr_code_preview)
    ImageView mQrCodePreview;
    @BindView(R.id.qr_code_save)
    Button mQrCodeSave;

    private Handler mHandler;

    private Runnable mIconRunnable = new Runnable() {
        @Override
        public void run() {
            String account = mQrCodeMessage.getText().toString();
            if (!TextUtils.isEmpty(account)) {
                int width = (int) (256 * getResources().getDisplayMetrics().density + 0.5f);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(account, width);
                mHandler.sendMessage(Message.obtain(mHandler, 1, bitmap));
            } else {
                Toast.makeText(QRCodeActivity.this, "账户获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        String account = mQrCodeMessage.getText().toString();
                        if (!TextUtils.isEmpty(account)) {
                            int width = (int) (256 * getResources().getDisplayMetrics().density + 0.5f);
                            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(account, width);
                            setMessageAndPreview("", bitmap);
                        } else {
                            Toast.makeText(QRCodeActivity.this, "账户获取失败", Toast.LENGTH_SHORT).show();
                        }
                    } else if (msg.what == 2) {
                        Bitmap bitmap = null;
                        if (msg.obj instanceof Uri) {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), (Uri) msg.obj);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (msg.obj instanceof Bundle) {
                            bitmap = ((Bundle) msg.obj).getParcelable("data");
                        }
                        if (bitmap != null) {
                            String message = QRCodeDecoder.syncDecodeQRCode(bitmap);
                            setMessageAndPreview(message, bitmap);
                        }
                    } else if (msg.what == 3) {
                        File iconFile = new File((String) msg.obj + File.separator + "icon.jpg");
                        try {
                            if (!iconFile.exists()) {
                                iconFile.createNewFile();
                            } else {
                                iconFile.delete();
                                iconFile.createNewFile();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String account = mQrCodeMessage.getText().toString();
                        if (!TextUtils.isEmpty(account)) {
                            int width = (int) (128 * getResources().getDisplayMetrics().density + 0.5f);
                            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(account, width);
                            try {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(iconFile));
//                                MediaStore.Images.Media.insertImage(getContentResolver(), iconFile.getPath(), "title", "description");
//                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                                // 保存在外部文件夹时，通知系统图库刷新文件
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(iconFile)));
//                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ (String)msg.obj)));
                                showToast("保存成功");
                            } catch (Exception e) {
                                showToast("保存失败");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            Looper.loop();
        }
    };

    @Override
    protected int setContentViewId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected String setActivityTitle() {
        return "二维码";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        new Thread(mRunnable).start();
//        Log.e(TAG, "onCreate: " + MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        File filesDir = getFilesDir();
        File externalPicturesFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File externalDcimFilesDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File externalStorageOicturePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File externalStorageDcimPublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Log.e(TAG, "onCreate: " +
                filesDir.getPath() + "\n" +
                externalPicturesFilesDir.getPath() + "\n" +
                externalDcimFilesDir.getPath() + "\n" +
                externalStorageOicturePublicDirectory + "\n" +
                externalStorageDcimPublicDirectory.getPath() + "\n" +
                externalStorageDirectory.getPath());
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void addActivityToList() {
        LApplication.addActivity(this);
    }

    @Override
    protected void removeActivityForList() {
        LApplication.removeActivity(this);
    }

    @OnClick({R.id.qr_code_create, R.id.qr_code_recognition, R.id.qr_code_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qr_code_create:
                mHandler.sendEmptyMessage(1);
                break;
            case R.id.qr_code_recognition:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 21);
                break;
            case R.id.qr_code_save:
                File pictureFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                String parentPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "ABC";
                String parentPath = pictureFile.getPath() + File.separator + "李波";
                File parentFile = new File(parentPath);
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                Log.e(TAG, "onViewClicked: " + parentFile.getPath() + " " + pictureFile.getPath());
                if (parentFile.exists()) {
                    Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
                }
//                Log.e(TAG, "onViewClicked: DCIM = " + getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath());
//                Log.e(TAG, "onViewClicked: public DCIM = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());
//                Log.e(TAG, "onViewClicked: public PICTURES = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
                mHandler.sendMessage(mHandler.obtainMessage(3, parentFile.getPath()));
                break;
        }
    }

    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(QRCodeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMessageAndPreview(final String message, final Bitmap preview) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.length() == 0) {
                    mQrCodeText.setText(message);
                } else {
                    if (message.equals(mQrCodeMessage.getText().toString())) {
                        Toast.makeText(QRCodeActivity.this, "结果一致", Toast.LENGTH_SHORT).show();
                    }
                    mQrCodeText.setText("识别结果为：" + message);
                }
                mQrCodePreview.setImageBitmap(preview);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //通过uri的方式返回，部分手机uri可能为空
            if (uri != null) {
                mHandler.sendMessage(mHandler.obtainMessage(2, uri));
            } else {
                //部分手机可能直接存放在bundle中
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    mHandler.sendMessage(mHandler.obtainMessage(2, bundle));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.getLooper().quitSafely();
    }
}