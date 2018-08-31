package com.libok.androidcode.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.IBinder;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.libok.androidcode.R;

import java.util.Random;

public class MyWallpaperService extends WallpaperService {

    private Bitmap heart;

    public MyWallpaperService() {
    }

    @Override
    public Engine onCreateEngine() {
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round);
        return new MyEngine();
    }

    class MyEngine extends Engine {

        private boolean mVisible;
        private float mTouchX = -1;
        private float mTouchY = -1;
        private int count = 1;
        private int originX = 50;
        private int originY = 50;
        private Paint mPaint = new Paint();
        Handler mHandler = new Handler();

        private Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mPaint.setARGB(76, 0, 0, 255);
            mPaint.setAntiAlias(true);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                drawFrame();
            } else {
                mHandler.removeCallbacks(mRunnable);
            }
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            drawFrame();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mRunnable);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mTouchX = event.getX();
                mTouchY = event.getY();
            } else {
                mTouchX = -1;
                mTouchY = -1;
            }
            super.onTouchEvent(event);
        }

        private void drawFrame() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                c.drawColor(Color.parseColor("#ffffffff"));
                drawTouchPoint(c);
                mPaint.setAlpha(76);
                c.translate(originX, originY);
                for (int i = 0; i < count; i++) {
                    c.translate(80, 0);
                    c.scale(0.95f, 0.95f);
                    c.rotate(20f);
                    c.drawRect(0, 0, 150, 75, mPaint);
                }
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c);
                }
            }
            mHandler.removeCallbacks(mRunnable);
            if (mVisible) {
                count++;
                if (count >= 50) {
                    Random random = new Random();
                    count = 1;
                    originX += (random.nextInt(60) - 30);
                    originY += (random.nextInt(60) - 30);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.postDelayed(mRunnable, 100);
            }
        }

        private void drawTouchPoint(Canvas c) {
            if (mTouchY >= 0 && mTouchY >= 0) {
                mPaint.setAlpha(255);
                c.drawBitmap(heart, mTouchX, mTouchY, mPaint);
            }
        }
    }
}
