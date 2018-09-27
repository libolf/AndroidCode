package com.libok.androidcode.util;

import com.libok.androidcode.R;

/**
 * @author liboK  2018/09/13 下午 03:01
 */
public final class Constants {

    private Constants() {
    }

    public static class ActivityConst {

        public static final String ACTIVITY_CUSTOM_ACTION = "com.libok.androidcode.action.activity.tree";
        public static final String ACTIVITY_CUSTOM_CATEGORY = "com.libok.androidcode.category.activity.tree";

        public static final int[] ACTIVITY_ANIM_TRANSLATE_GO = {R.anim.anim_activity_go_translate_enter, R.anim.anim_activity_go_translate_exit};
        public static final int[] ACTIVITY_ANIM_TRANSLATE_BACK = {R.anim.anim_activity_back_translate_enter, R.anim.anim_activity_back_translate_exit};
        public static final int[] ACTIVITY_ANIM_SCALE_GO = {R.anim.anim_activity_go_scale_enter, R.anim.anim_activity_go_scale_exit};
        public static final int[] ACTIVITY_ANIM_SCALE_BACK = {R.anim.anim_activity_back_scale_enter, R.anim.anim_activity_back_scale_exit};
        public static final int[] ACTIVITY_ANIM_WX_GO = {R.anim.anim_activity_go_wx_enter, R.anim.anim_activity_go_wx_exit};
        public static final int[] ACTIVITY_ANIM_WX_BACK = {R.anim.anim_activity_back_wx_enter, R.anim.anim_activity_back_wx_exit};

        public static final String ACTIVITY_LIFECYCLE_TITLE = "Activity生命周期";

    }

    public static class FragmentConst{
        public static final String FRAGMENT_KEY_DIALOG_TITLE = "dialog_title";
        public static final String FRAGMENT_KEY_DIALOG_MESSAGE = "dialog_message";
    }

    public static class ServiceConst {


    }

    public static class UtilConst {

        /**
         * 线程池核心线程数
         */
        public static final int THREAD_POOL_CORE_SIZE = 2;
        /**
         * 线程池最大线程数
         */
        public static final int THREAD_POOL_MAXIMUM_SIZE = 8;
        /**
         * 线程池空闲线程存活时间 second
         */
        public static final int THREAD_POOL_KEEP_ALIVE_TIME = 10;
        /**
         * 线程池任务队列
         */
        public static final int THREAD_POOL_QUEUE_SIZE = 0;

    }

    public static class DBConst {


    }
}
