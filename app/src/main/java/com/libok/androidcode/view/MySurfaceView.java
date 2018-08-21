package com.libok.androidcode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;


/**
 * @author liboK  2018/08/13 下午 12:08
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MySurfaceView";

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Handler mHandler;
    private Paint mPaint;
    private DrawBean mDrawBean;
    private Random mRandom;
    private long mStartTime;
    private boolean mRunning = true;
    private long mDurtion;

    private Runnable mHandlerRunnable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            Log.e(TAG, "run: ");
            mHandler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    mStartTime = System.currentTimeMillis();
                    if (msg.what == 1) {

                        try {
                            mCanvas = mSurfaceHolder.lockCanvas();
                            Log.e(TAG, "handleMessage: draw123 = " + (System.currentTimeMillis() - mStartTime));
                            long start = System.currentTimeMillis();
                            mDrawBean.onDraw(mCanvas);
                            Log.e(TAG, "handleMessage: draw = " + (System.currentTimeMillis() - start));
                        } finally {
                            if (mCanvas != null) {
                                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                            }
                            Log.e(TAG, "handleMessage: lock canvas = " + (System.currentTimeMillis() - mStartTime));
                        }
                        if (mHandler != null) {
                            mDurtion = System.currentTimeMillis() - mStartTime;
                            Log.e(TAG, "handleMessage: end = " + mDurtion);
                            mHandler.sendEmptyMessageDelayed(1, 16 - mDurtion);
                        }
                    } else if (msg.what == -1) {
                        getLooper().quitSafely();
                    }
                }
            };
            mHandler.sendEmptyMessage(1);
            Looper.loop();
        }
    };

    private Runnable mSleepRunnable = new Runnable() {
        @Override
        public void run() {
            while (mRunning) {
                Log.e(TAG, "run: " + mRunning);
                mStartTime = System.currentTimeMillis();
                try {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mDrawBean.onDraw(mCanvas);
                } finally {
                    if (mCanvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
                try {
                    mDurtion = System.currentTimeMillis() - mStartTime;
                    if (mDurtion <= 16) {
                        Thread.sleep(16 - mDurtion);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GRAY);
        mRandom = new Random();
        mDrawBean = new DrawBean(100);
        Log.e(TAG, "MySurfaceView: ");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        new Thread(mSleepRunnable).start();
        new Thread(mHandlerRunnable).start();
        Log.e(TAG, "surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHandler != null) {
//            mHandler.sendEmptyMessage(1);
        }
        Log.e(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(-1);
        }
        Log.e(TAG, "surfaceDestroyed: ");
    }

    /**
     * 控制动画开始或停止
     *
     * @param running
     */
    public void animationControl(boolean running) {
//        if (running) {
//            new Thread(mSleepRunnable).start();
//        } else {
//            mRunning = false;
//        }
        Log.e(TAG, "animationControl: " + mRunning + " " + running);
        if (!running && mHandler != null) {
            mHandler.sendEmptyMessage(-1);
            mHandler = null;
        } else {
            new Thread(mHandlerRunnable).start();
        }
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    private class DrawBean {

        private int mCount;
        private RainBean[] mRainBeans;

        public DrawBean(int count) {
            mCount = count;
            mRainBeans = new RainBean[count];
            for (int i = 0; i < mCount; i++) {
                mRainBeans[i] = new RainBean(
                        mRandom.nextInt(1080),
                        mRandom.nextInt(1920),
                        mRandom.nextInt(100),
                        mRandom.nextInt(20),
                        mRandom.nextInt(250));
            }
        }

        private void onDraw(Canvas canvas) {
            clearCanvas(canvas);
            for (RainBean rainBean : mRainBeans) {
                mPaint.setAlpha(rainBean.a);
                canvas.drawLine(rainBean.x, rainBean.y, rainBean.x, rainBean.y + rainBean.l, mPaint);
                rainBean.y += rainBean.r;
                if (rainBean.y >= 1920) {
                    rainBean.y = -rainBean.l;
                }
            }
        }
    }

    private class RainBean {

        // XY位置，L长度，R速度，A透明度
        int x, y, l, r, a;

        public RainBean(int x, int y, int l, int r, int a) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.r = r;
            this.a = a;
        }
    }

}
