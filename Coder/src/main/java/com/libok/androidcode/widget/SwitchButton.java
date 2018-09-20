package com.libok.androidcode.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.libok.androidcode.util.DisplayUtils;


/**
 * @author 李波 2018-03-16 下午 09:53
 * <p>
 * 可以有以下几种模式：都是要添加点击变化的，如果是点击的话可以加个过度移动的动画——斗鱼
 * 1、背景颜色不变，只随着手指移动变换开关圆圈的大小和颜色，有边框，关灰色开淡蓝色——魅族 √
 * 2、背景颜色不变，只随着手指移动变换开关为横线和颜色，有边框，关灰色开淡蓝色   √
 * 3、背景颜色不变，开关大小不变，只在点击时改变开关颜色，有边框，关白色开淡蓝色——应用宝 ×
 * 4、背景颜色随着手指的移动渐变，开关大小和颜色不变，无边框，关白色开淡绿色——微信 √
 * 5、背景颜色随着手指移动变化，左边颜色区域逐渐变小，右边颜色区域逐渐变大，反之类同，无边框，关灰色开背景淡绿色开关深绿色——豌豆荚 √
 * 6、背景颜色不随着手指移动变化，只在UP时更改背景颜色，移动时只改变圆圈的位置，有边框，关白色开淡绿色——今日头条 ×
 * <p>
 * SwitchButton: create
 * setCurrentType:
 * onMeasure:
 * onMeasure:
 * onSizeChanged:
 * onLayout:
 * onLayout:
 * onDraw:
 */
public class SwitchButton extends View {

    private static final String TAG = "SwitchButton";
    private static final float MAGIC_NUM = 0.8f;
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
    public static final int TYPE_5 = 4;
    public static final int TYPE_6 = 5;

    public static final int VIEW_SMALL = 0;
    public static final int VIEW_NORMAL = 1;
    public static final int VIEW_BIG = 2;

    public static final int ACCELERATE_INTERPOLATOR = 0;
    public static final int LINEAR_INTERPOLATOR = 1;
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 2;
    public static final int ANTICIPATE_INTERPOLATOR = 3;
    public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 4;
    public static final int BOUNCE_INTERPOLATOR = 5;
    public static final int DECELERATE_INTERPOLATOR = 6;
    public static final int OVERSHOOT_INTERPOLATOR = 7;

    private Context mContext;
    private SwitchButton mCurrentButton;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;
    private int mActuallyWidth;
    private int mActuallyHeight;

    // 第一次按下时的坐标
    private PointF mDownPoint;
    // 操作后最后的坐标
    private PointF mLastPoint;
    // 背景左右半圆半径
    private float mRadius;
    // 正常情况下的开关圆半径
    private float mCircleRadius;
    // 状态为关时的小开关圆的半径
    private float mSmallCircleRadius;
    // 移动比例
    private float positionOffset;

    // 中心点
    private PointF mCenterPoint;
    // 圆心
    private PointF mCirclePoint;
    // 左半圆的外切正方形
    private RectF mRightRectF;
    // 右半圆的外切正方形
    private RectF mLeftRectF;
    // 整个控件矩形
    private RectF mRectF;
    // 外环矩形，扣除宽度
    private RectF mStrokeRectF;
    //开关两边需要不同颜色时的区域
    private RectF mBackGroundRightRectF;
    private RectF mBackGroundLeftRectF;
    // 当样式为TYPE_2时，状态关的横线区域
    private RectF mLineRectF;

    // 颜色渐变计算
    private ArgbEvaluator mArgbEvaluator;

    //当前样式
    private int mCurrentType;
    // 横线模式时，横线的高度和宽度
    private float mLineWidth;
    private float mLineHeight;
    // 状态改变
    private OnCheckedChangeListener mCheckedChangeListener;
    private OnCustomDrawSwitchListener mCustomDrawSwitchListener;
    // 最小移动距离
    private int mScaledTouchSlop;
    // 当前状态
    private boolean mCurrentState;
    // 只在点击时的动画
    private ValueAnimator mClickValueAnimator;
    // 默认动画时长
    private int mAnimationDuration;
    // 默认动画插值器
    private Interpolator mAnimationInterpolator;
    // 移动时的速度跟踪
    private VelocityTracker mTracker;
    // 第一次触摸的ID
    private int mFirstPointerId;
    // X轴上的瞬时速度
    private float mXVelocity;
    // 当移动过半时才会触发状态改变的通知
    private boolean mCanNotifyChangeCheck = false;
    // 当触摸位置在开关位置范围内才允许触摸移动
    private boolean mCircleCanMove = false;
    // 当Listener不为null时，需要通知状态的改变
    private boolean mNeedNotifyChangeCheck = false;
    // 腾讯样式的默认颜色
    private int mType2Color = Color.GRAY;
    // 头条模式的背景默认颜色
    private int mType5Color = Color.parseColor("#d3dee9");
    // 默认的外环宽度
    private int mStrokeWidth;
    // 连续点击次数
    private int mClickCount = 0;
    // 自动滑动
    private Handler mAutoHandler;
    private Runnable mAutoOpenScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCirclePoint.x < mRightRectF.centerX()) {
                mCirclePoint.x += mXVelocity;
            }
            if (mCirclePoint.x > mRightRectF.centerX()) {
                mCirclePoint.x = mRightRectF.centerX();
            }
            mAutoHandler.postDelayed(this, 24);
//            Log.e(TAG, "run: " + mCirclePoint.x + " " + mRightRectF.centerX());
            if (mCirclePoint.x == mRightRectF.centerX()) {
                mAutoHandler.removeCallbacks(this);
                Log.e(TAG, "run: open " + mCanNotifyChangeCheck);
                if (mCanNotifyChangeCheck && mNeedNotifyChangeCheck) {
                    mCheckedChangeListener.onCheckedChanged(mCurrentButton, true);
                    mCanNotifyChangeCheck = false;// 保证下次触摸不会紊乱
                }
            }
            if (mCurrentType == TYPE_6) {
                mType5Color = Color.parseColor("#ff99cc00");
            }
            invalidate();
        }
    };
    private Runnable mAutoCloseScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCirclePoint.x > mLeftRectF.centerX()) {
                mCirclePoint.x -= mXVelocity;
            }
            if (mCirclePoint.x < mLeftRectF.centerX()) {
                mCirclePoint.x = mLeftRectF.centerX();
            }
            mAutoHandler.postDelayed(this, 24);
            if (mCirclePoint.x == mLeftRectF.centerX()) {
                mAutoHandler.removeCallbacks(this);
                Log.e(TAG, "run: close " + mCanNotifyChangeCheck);
                if (mCanNotifyChangeCheck && mNeedNotifyChangeCheck) {
                    mCheckedChangeListener.onCheckedChanged(mCurrentButton, false);
                    mCanNotifyChangeCheck = false;// 保证下次触摸不会紊乱
                }
            }
            if (mCurrentType == TYPE_6) {
                mType5Color = Color.parseColor("#d3dee9");
            }
            invalidate();
        }
    };
    private int mSwitchButtonBackground;
    private int mSwitchOffColor;
    private int mSwitchOnColor;
    private int mSwitchStrokeColor;
    private int mAnimationInterpolatorNum;
    private int mSwitchButtonViewSize;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mCurrentButton = this;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mRadius = DisplayUtils.dp2px(context, 10);
        mCircleRadius = DisplayUtils.dp2px(context, 9);

        mRightRectF = new RectF();
        mLeftRectF = new RectF();
        mBackGroundRightRectF = new RectF();
        mBackGroundLeftRectF = new RectF();
        mRectF = new RectF();
        mLineRectF = new RectF();
        mStrokeRectF = new RectF();
        mCenterPoint = new PointF();
        mCirclePoint = new PointF();
        mDownPoint = new PointF();
        mLastPoint = new PointF();

        mArgbEvaluator = new ArgbEvaluator();
        mLineWidth = DisplayUtils.dp2px(context, 10);
        mLineHeight = DisplayUtils.dp2px(context, 8);

        mCurrentType = TYPE_2;//TYPE_1;
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScaledTouchSlop /= 4;

        mClickValueAnimator = ValueAnimator.ofFloat(0f, 1f);

        mAutoHandler = new Handler();

//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
//        mCurrentState = typedArray.getBoolean(R.styleable.SwitchButton_checked, false);
//        mSwitchButtonBackground = typedArray.getColor(R.styleable.SwitchButton_background_color, Color.WHITE);
//        mSwitchOffColor = typedArray.getColor(R.styleable.SwitchButton_off_color, Color.GRAY);
//        mSwitchOnColor = typedArray.getColor(R.styleable.SwitchButton_on_color, Color.parseColor("#ff33b5e5"));
//        mSwitchStrokeColor = typedArray.getColor(R.styleable.SwitchButton_stroke_color, Color.GRAY);
//        mRadius = typedArray.getDimensionPixelOffset(R.styleable.SwitchButton_circle_radius, -1);
//        mStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.SwitchButton_stroke_width, DisplayUtils.dp2px(context, 2));
//        mAnimationDuration = typedArray.getInt(R.styleable.SwitchButton_animation_duration, 400);
//        mAnimationInterpolatorNum = typedArray.getInt(R.styleable.SwitchButton_animation_interpolator, ACCELERATE_DECELERATE_INTERPOLATOR);
//        mSwitchButtonViewSize = typedArray.getInt(R.styleable.SwitchButton_view_size, 1);
//        mCurrentType = typedArray.getInt(R.styleable.SwitchButton_view_type, TYPE_1);
//        typedArray.recycle();

        Log.e(TAG, "SwitchButton: " +
                "background = " + Integer.toHexString(mSwitchButtonBackground) +
                "\noffColor = " + Integer.toHexString(mSwitchOffColor) +
                "\nonColor = " + Integer.toHexString(mSwitchOnColor) +
                "\nstroekColor = " + Integer.toHexString(mSwitchStrokeColor) +
                "\ncirclrRadius = " + mRadius +
                "\nstrokeWidth = " + mStrokeWidth +
                "\nanimationDuration = " + mAnimationDuration +
                "\nanimationInterpolator = " + mAnimationInterpolatorNum +
                "\nviewSize = " + mSwitchButtonViewSize +
                "\nviewType = " + mCurrentType
        );

        switch (mAnimationInterpolatorNum) {
            case ACCELERATE_INTERPOLATOR:
                mAnimationInterpolator = new AccelerateInterpolator();
                break;
            case LINEAR_INTERPOLATOR:
                mAnimationInterpolator = new LinearInterpolator();
                break;
            case ACCELERATE_DECELERATE_INTERPOLATOR:
                mAnimationInterpolator = new AccelerateDecelerateInterpolator();
                break;
            case ANTICIPATE_INTERPOLATOR:
                mAnimationInterpolator = new AnticipateInterpolator();
                break;
            case ANTICIPATE_OVERSHOOT_INTERPOLATOR:
                mAnimationInterpolator = new AnticipateOvershootInterpolator();
                break;
            case BOUNCE_INTERPOLATOR:
                mAnimationInterpolator = new BounceInterpolator();
                break;
            case DECELERATE_INTERPOLATOR:
                mAnimationInterpolator = new DecelerateInterpolator();
                break;
            case OVERSHOOT_INTERPOLATOR:
                mAnimationInterpolator = new OvershootInterpolator();
                break;
        }

        Log.e(TAG, "SwitchButton: create " + mScaledTouchSlop);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        mCheckedChangeListener = checkedChangeListener;
    }

    public void setCustomDrawSwitchListener(OnCustomDrawSwitchListener customDrawSwitchListener) {
        mCustomDrawSwitchListener = customDrawSwitchListener;
    }

    public int getSwitchButtonBackground() {
        return mSwitchButtonBackground;
    }

    public void setSwitchButtonBackground(int switchButtonBackground) {
        mSwitchButtonBackground = switchButtonBackground;
        invalidate();
    }

    public int getSwitchOffColor() {
        return mSwitchOffColor;
    }

    public void setSwitchOffColor(int switchOffColor) {
        mSwitchOffColor = switchOffColor;
        invalidate();
    }

    public int getSwitchOnColor() {
        return mSwitchOnColor;
    }

    public void setSwitchOnColor(int switchOnColor) {
        mSwitchOnColor = switchOnColor;
        invalidate();
    }

    public int getSwitchStrokeColor() {
        return mSwitchStrokeColor;
    }

    public void setSwitchStrokeColor(int switchStrokeColor) {
        mSwitchStrokeColor = switchStrokeColor;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
        switchSizeConpute();
        invalidate();
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidate();
    }

    public int getAnimationDuration() {
        return mAnimationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    public Interpolator getAnimationInterpolator() {
        return mAnimationInterpolator;
    }

    public void setAnimationInterpolator(Interpolator animationInterpolator) {
        mAnimationInterpolator = animationInterpolator;
    }

    public int getSwitchButtonViewSize() {
        return mSwitchButtonViewSize;
    }

    public void setSwitchButtonViewSize(int switchButtonViewSize) {
        mSwitchButtonViewSize = switchButtonViewSize;
        mRadius = -1f;
        requestLayout();
    }

    public void setCurrentType(int currentType) {
//        Log.e(TAG, "setCurrentType: ");
        mCurrentType = currentType;
        if (mCurrentType == TYPE_3) {
            if (mCurrentState) {
                mType2Color = mSwitchOnColor;
            } else {
                mType2Color = mSwitchOffColor;
            }
        } else if (mCurrentType == TYPE_6) {
            if (mCurrentState) {
                mType5Color = mSwitchOnColor;
            } else {
                mType5Color = mSwitchOffColor;
            }
        }
        invalidate();
    }

    public int getCurrentType() {
        return mCurrentType;
    }

    public boolean getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(boolean currentState) {
        mCurrentState = currentState;
//        switchSizeConpute();
        if (mCurrentState) {
            mCirclePoint.x = mRightRectF.centerX();
        } else {
            mCirclePoint.x = mLeftRectF.centerX();
        }
//        if (mClickValueAnimator != null && mClickValueAnimator.isRunning()) {
////            Log.e(TAG, "onTouchEvent animation: " + mClickValueAnimator.isRunning());
//            mClickValueAnimator.end();
//        }
//        if (mCurrentState) {
//            mClickValueAnimator = ValueAnimator.ofFloat(0f, 1f);
//            clickAnimation();
//        } else {
//            mClickValueAnimator = ValueAnimator.ofFloat(1f, 0f);
//            clickAnimation();
//        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.e(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        Log.e(TAG, "onMeasure: " + widthMode + " " + heightMode);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e(TAG, "measureWidth: " + widthSize + " " + widthMode);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                //当父容器是ListView或ScrollView时，会出现模式为UNSPECIFIED的情况
            case MeasureSpec.UNSPECIFIED:
                if (mSwitchButtonViewSize == VIEW_SMALL) {
                    width = DisplayUtils.dp2px(mContext, 32);
                } else if (mSwitchButtonViewSize == VIEW_NORMAL) {
                    width = DisplayUtils.dp2px(mContext, 50);
                } else if (mSwitchButtonViewSize == VIEW_BIG) {
                    width = DisplayUtils.dp2px(mContext, 70);
                }
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "measureHeight: " + heightSize + " " + heightMode);
        int height = 0;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                if (mSwitchButtonViewSize == VIEW_SMALL) {
                    height = DisplayUtils.dp2px(mContext, 14);
                } else if (mSwitchButtonViewSize == VIEW_NORMAL) {
                    height = DisplayUtils.dp2px(mContext, 22);
                } else if (mSwitchButtonViewSize == VIEW_BIG) {
                    height = DisplayUtils.dp2px(mContext, 31);
                }
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
        }
        return height;
    }

    /**
     * Canvas直接画图和画Path到底哪个快
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e(TAG, "onDraw: " + mCurrentType);
        if (mCustomDrawSwitchListener != null) {
            mCustomDrawSwitchListener.onCustomDrawSwitch(canvas, mCirclePoint.x, mRectF);
        }
        switch (mCurrentType) {
            case TYPE_1:
                // 如果不改变背景，那么先画背景
                drawBackground(canvas, mSwitchButtonBackground);
                drawStrokeNotChangeBackground(canvas);
                drawCircleWithRadiusColor(canvas, radiusCompute(mCirclePoint.x), colorCompute(mCirclePoint.x, mSwitchOffColor, mSwitchOnColor));
                break;
            case TYPE_2:
                drawBackground(canvas, mSwitchButtonBackground);
                drawStrokeNotChangeBackground(canvas);
                drawLineWithRadiusColor(canvas, colorCompute(mCirclePoint.x, mSwitchOffColor, mSwitchOnColor));
                break;
            case TYPE_3:
                drawBackground(canvas, mSwitchButtonBackground);
                drawStrokeNotChangeBackground(canvas);
                drawCircleWithRadiusColor(canvas, mCircleRadius, mType2Color);
                break;
            case TYPE_4:
                drawBackground(canvas, colorCompute(mCirclePoint.x, mSwitchOffColor, mSwitchOnColor));
                drawCircleWithRadiusColor(canvas, mCircleRadius, mSwitchButtonBackground);
                break;
            case TYPE_5:
                drawLeftBackgroundRect(canvas, mSwitchOnColor, mSwitchButtonBackground);
                drawRightBackgroundRect(canvas, mSwitchOffColor, mSwitchButtonBackground);
                break;
            case TYPE_6:
                drawBackground(canvas, mType5Color);
                drawStrokeNotChangeBackground(canvas);
                drawCircleWithRadiusColor(canvas, mCircleRadius, mSwitchButtonBackground);
                break;
        }
    }

    /**
     * 需要左右两边不同的颜色时，绘制右边
     *
     * @param canvas
     * @param rightBackgroundColor
     * @param circleColor
     */
    private void drawRightBackgroundRect(Canvas canvas, int rightBackgroundColor, int circleColor) {
        canvas.save();
        mBackGroundRightRectF.left = mCirclePoint.x;
        mBackGroundRightRectF.top = mRightRectF.top;
        mBackGroundRightRectF.right = mRightRectF.right;
        mBackGroundRightRectF.bottom = mRightRectF.bottom;
        canvas.clipRect(mBackGroundRightRectF);
        drawBackground(canvas, rightBackgroundColor);
        drawCircleWithRadiusColor(canvas, mRadius, circleColor);
        canvas.restore();
    }

    /**
     * 需要左右两边不同的颜色时，绘制左边
     *
     * @param canvas
     * @param leftBackgroundColor
     * @param circleColor
     */
    private void drawLeftBackgroundRect(Canvas canvas, int leftBackgroundColor, int circleColor) {
        canvas.save();
        mBackGroundLeftRectF.left = mLeftRectF.left;
        mBackGroundLeftRectF.top = mLeftRectF.top;
        mBackGroundLeftRectF.right = mCirclePoint.x;
        mBackGroundLeftRectF.bottom = mLeftRectF.bottom;
        canvas.clipRect(mBackGroundLeftRectF);
        drawBackground(canvas, leftBackgroundColor);
        drawCircleWithRadiusColor(canvas, mRadius, circleColor);
        canvas.restore();
    }


    /**
     * 不改变外环颜色和背景颜色
     *
     * @param canvas
     */
    private void drawStrokeNotChangeBackground(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mSwitchStrokeColor);
        if (mStrokeWidth > 0) {
            mPaint.setStrokeWidth(mStrokeWidth);
        } else {
            mPaint.setStrokeWidth(DisplayUtils.dp2px(mContext, 2f));
        }
        canvas.drawRoundRect(mStrokeRectF, mStrokeRectF.height() / 2f, mStrokeRectF.height() / 2f, mPaint);
//        canvas.drawRoundRect(mRectF, mLeftRectF.height() / 2, mLeftRectF.height() / 2, mPaint);
    }

    /**
     * 绘制条形开关
     *
     * @param canvas
     * @param color
     */
    private void drawLineWithRadiusColor(Canvas canvas, int color) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        positionOffset = offsetCompute(mCirclePoint.x);

        mLineRectF.left = mCirclePoint.x - mCircleRadius * positionOffset - mLineWidth / 2f * (1 - positionOffset);
        mLineRectF.top = mCirclePoint.y - mCircleRadius * positionOffset - mLineHeight / 2f * (1 - positionOffset);
        mLineRectF.right = mCirclePoint.x + mCircleRadius * positionOffset + mLineWidth / 2 * (1 - positionOffset);
        mLineRectF.bottom = mCirclePoint.y + mCircleRadius * positionOffset + mLineHeight / 2f * (1 - positionOffset);

        canvas.drawRoundRect(
                mLineRectF,
                Math.max(mLineHeight / 2f, mCircleRadius * positionOffset),
                Math.max(mLineHeight / 2f, mCircleRadius * positionOffset),
                mPaint);
    }

    /**
     * 绘制圆形开关
     *
     * @param canvas
     * @param radius
     * @param color
     */
    private void drawCircleWithRadiusColor(Canvas canvas, float radius, int color) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(mCirclePoint.x, mCirclePoint.y, mCircleRadius, mPaint);
        canvas.drawCircle(mCirclePoint.x, mCirclePoint.y, radius, mPaint);
    }


    /**
     * 绘制背景
     *
     * @param canvas
     * @param color
     */
    private void drawBackground(Canvas canvas, int color) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
    }

    /**
     * 随着移动计算小圆圈的半径
     *
     * @param x
     * @return
     */
    private float radiusCompute(float x) {
        return mSmallCircleRadius + (mCircleRadius - mSmallCircleRadius) * offsetCompute(x);
    }

    /**
     * 随着移动计算已移动距离的比例
     *
     * @param x
     * @return
     */
    private float offsetCompute(float x) {
        return (x - mLeftRectF.centerX()) / (mRightRectF.centerX() - mLeftRectF.centerX());
    }

    /**
     * 随着移动计算即时颜色
     *
     * @param x
     * @return
     */
    private int colorCompute(float x, int startColor, int endColor) {
        return (int) mArgbEvaluator.evaluate(offsetCompute(x), startColor, endColor);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = event.getX();
                mDownPoint.y = event.getY();
                mLastPoint.x = mDownPoint.x;
                mLastPoint.y = mDownPoint.y;
                mFirstPointerId = event.getPointerId(0);
                if (mDownPoint.x < mCirclePoint.x + mRadius && mDownPoint.x > mCirclePoint.x - mRadius && mCurrentType != TYPE_3) {
                    mCircleCanMove = true;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                mLastPoint.x = event.getX();
                mLastPoint.y = event.getY();
                if (mCircleCanMove) {
                    mCirclePoint.x = event.getX();
                }
                if (mCirclePoint.x > mRightRectF.centerX()) {
                    mCirclePoint.x = mRightRectF.centerX();
                } else if (mCirclePoint.x < mLeftRectF.centerX()) {
                    mCirclePoint.x = mLeftRectF.centerX();
                }

//                Log.e(TAG, "onTouchEvent: " + mXVelocity);
                break;
            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
                // Listener不为null时才回去通知
                mClickCount += 1;
                if (mCheckedChangeListener != null) {
                    mNeedNotifyChangeCheck = true;
                }
                // 自主滑动时判定是否已经达到需要改变状态的要求
//                Log.e(TAG, "onTouchEvent: " + mCurrentState + " " + mCirclePoint.x + " " + mCenterPoint.x);
                if ((mCurrentState && mCirclePoint.x < mCenterPoint.x) || (!mCurrentState && mCirclePoint.x > mCenterPoint.x)) {
                    mCanNotifyChangeCheck = true;
                } else {
                    mCanNotifyChangeCheck = false;
                }
                // 准备自主滑动后如果未达到边界时的速度
                mTracker.computeCurrentVelocity(100);
                mXVelocity = mTracker.getXVelocity(mFirstPointerId);
                mXVelocity = Math.max(mXVelocity, 10f);
                // 如果只是点击那么状态改变
                if (Math.abs(mLastPoint.x - mDownPoint.x) < mScaledTouchSlop) {
                    mCurrentState = !mCurrentState;
                    Log.e(TAG, "onTouchEvent: " + mCurrentState);
                    if (mClickValueAnimator != null && mClickValueAnimator.isRunning()) {
                        Log.e(TAG, "onTouchEvent animation: " + mClickValueAnimator.isRunning());
                        mClickValueAnimator.end();
                    }
                    if (mCurrentState) {
                        mClickValueAnimator = ValueAnimator.ofFloat(0f, 1f);
                        clickAnimation();
                    } else {
                        mClickValueAnimator = ValueAnimator.ofFloat(1f, 0f);
                        clickAnimation();
                    }
                } else {
                    // 不是纯点击，而是移动过距离的
                    if (mCirclePoint.x >= mCenterPoint.x && mCircleCanMove) {
                        mCurrentState = true;
//                        if (mRightRectF.centerX() - mCirclePoint.x > mCenterPoint.x - mLeftRectF.centerX()) {
//                            mCanNotifyChangeCheck = true;
//                        }
                        mAutoHandler.post(mAutoOpenScrollRunnable);
                    } else if (mCirclePoint.x < mCenterPoint.x && mCircleCanMove) {
                        mCurrentState = false;
//                        if (mCirclePoint.x - mLeftRectF.centerX() > mCenterPoint.x - mLeftRectF.centerX()) {
//                            mCanNotifyChangeCheck = true;
//                        }
                        mAutoHandler.post(mAutoCloseScrollRunnable);
                    }
                }
                releaseVelocityTracker();// 释放速度测量器资源
                mCircleCanMove = false;// 保证下次触摸不会紊乱
                break;
        }
        invalidate();
        return true;
    }

    private void releaseVelocityTracker() {
        if (mTracker != null) {
            mTracker.clear();
            mTracker.recycle();
            mTracker = null;
        }
    }

    private void clickAnimation() {
        mClickValueAnimator.setDuration(mAnimationDuration);
        mClickValueAnimator.setInterpolator(mAnimationInterpolator);
        mClickValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCirclePoint.x = (float) animation.getAnimatedValue() * (mRightRectF.centerX() - mLeftRectF.centerX()) + mLeftRectF.centerX();
                invalidate();
            }
        });
        mClickValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mClickCount -= 1;
                if (mCurrentType == TYPE_3) {
                    if (mCurrentState) {
                        mType2Color = mSwitchOnColor;
                    } else {
                        mType2Color = mSwitchOffColor;
                    }
                    invalidate();
                } else if (mCurrentType == TYPE_6) {
                    if (mCurrentState) {
                        mType5Color = mSwitchOnColor;
                    } else {
                        mType5Color = mSwitchOffColor;
                    }
                    invalidate();
                }
                if (mNeedNotifyChangeCheck && mClickCount == 0) {
                    Log.e(TAG, "onAnimationEnd: " + mCurrentState);
                    mCheckedChangeListener.onCheckedChanged(mCurrentButton, mCurrentState);
                }
            }
        });
        mClickValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        Log.e(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mCenterPoint.x = mWidth / 2f;
        mCenterPoint.y = mHeight / 2f;
        switchSizeConpute();


    }

    private void switchSizeConpute() {
        // 宽度大，高度小，不满足正常的宽高比
        if (mHeight * 1f / mWidth < MAGIC_NUM / (1 + MAGIC_NUM)) {
            mActuallyHeight = mHeight;
            mActuallyWidth = (int) (mActuallyHeight / MAGIC_NUM + mActuallyHeight);
        }
        // 宽度小，高度大，不满足正常的宽高比
        else if (mHeight * 1f / mWidth > MAGIC_NUM / (1 + MAGIC_NUM)) {
            mActuallyWidth = mWidth;
            mActuallyHeight = (int) (mActuallyWidth * MAGIC_NUM / (1f + MAGIC_NUM));
        }
        // 满足宽高比
        else if (Math.abs(mHeight * 1f / mWidth) - (MAGIC_NUM / (1 + MAGIC_NUM)) < 0.05f) {
            mActuallyWidth = mWidth;
            mActuallyHeight = mHeight;
        }

        mRectF.left = (mWidth - mActuallyWidth) / 2;
        mRectF.top = (mHeight - mActuallyHeight) / 2;
        mRectF.right = mRectF.left + mActuallyWidth;
        mRectF.bottom = mRectF.top + mActuallyHeight;

        switch (mCurrentType) {
            case TYPE_4:
            case TYPE_5:
                mStrokeWidth = 0;
                break;
            case TYPE_1:
            case TYPE_2:
            case TYPE_3:
            case TYPE_6:
                computeStroekRectF();
                break;
        }


        if (mRadius == -1f) {
            mRadius = mActuallyHeight / 2f;
        }
        mCircleRadius = mStrokeWidth != 0 ? mRadius * 5 / 6f - mStrokeWidth : mRadius * 5 / 6f;
        mSmallCircleRadius = mCircleRadius / 3f;
//        mLeftRectF.left = (mWidth - 2f * mRadius / MAGIC_NUM - 2f * mRadius) / 2f;
        mLeftRectF.left = mRectF.left;
        mLeftRectF.top = mRectF.top;
        mLeftRectF.right = mLeftRectF.left + mRadius * 2;
        mLeftRectF.bottom = mRectF.bottom;

//        mRightRectF.left = mWidth - (mWidth - 2f * mRadius / MAGIC_NUM - 2f * mRadius) / 2f - 2f * mRadius;
//        mRightRectF.left = mWidth / 2f + mRadius / MAGIC_NUM;
        mRightRectF.left = mRectF.right - mRadius * 2;
        mRightRectF.top = mRectF.top;
        mRightRectF.right = mRectF.right;
        mRightRectF.bottom = mRectF.bottom;

        if (mCurrentState) {
            mCirclePoint.x = mRightRectF.centerX();
            mCirclePoint.y = mRightRectF.centerY();
        } else {
            mCirclePoint.x = mLeftRectF.centerX();
            mCirclePoint.y = mLeftRectF.centerY();
        }

        mLineWidth = mCircleRadius;
        mLineHeight = mLineWidth / 3f;
    }

    private void computeStroekRectF() {
        if (mStrokeWidth < 0) {
            mStrokeWidth = DisplayUtils.dp2px(mContext, 2);
        } else if (mStrokeWidth == 0) {
            throw new IllegalArgumentException("当前样式需要设置StrokeWidth为非0值");
        }

        mStrokeRectF.left = mRectF.left + mStrokeWidth / 2f;
        mStrokeRectF.top = mRectF.top + mStrokeWidth / 2f;
        mStrokeRectF.right = mRectF.right - mStrokeWidth / 2f;
        mStrokeRectF.bottom = mRectF.bottom - mStrokeWidth / 2f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        Log.e(TAG, "onLayout: ");
        super.onLayout(changed, left, top, right, bottom);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton switchButton, boolean isChecked);
    }

    public interface OnCustomDrawSwitchListener {
        void onCustomDrawSwitch(Canvas canvas, float switchX, RectF viewRectf);
    }
}
