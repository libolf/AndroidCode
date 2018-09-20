package com.libok.androidcode.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.libok.androidcode.R;
import com.libok.androidcode.bean.AppLabelBean;
import com.libok.androidcode.bean.IntentBean;
import com.libok.androidcode.core.ActivityLabelTree;
import com.libok.androidcode.util.Constants;
import com.libok.androidcode.view.adapter.MyListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener, MyListAdapter.ViewCreator<String, HomeActivity.TestViewHolder> {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.home_list)
    ListView mHomeList;
    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    private List<String> mData;
    private MyListAdapter<String, TestViewHolder> mAdapter;
    private List<AppLabelBean<IntentBean>> mCurrentLabelBeanList;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected String setActivityTitle() {
        return "Coder";
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent intent = new Intent(Constants.ActivityConst.ACTIVITY_CUSTOM_ACTION, null);
        intent.addCategory(Constants.ActivityConst.ACTIVITY_CUSTOM_CATEGORY);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
        Log.i(TAG, "onCreate: Activity size is " + resolveInfoList.size());

        Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(packageManager));

        mCurrentLabelBeanList = new ArrayList<>();
        ActivityLabelTree<IntentBean> tree = new ActivityLabelTree<>(null, "first", null);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            String label = resolveInfo.loadLabel(packageManager).toString();
            IntentBean intentBean = new IntentBean(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            Log.i(TAG, "onCreate: " + label + " " + intentBean.toString());
            String[] split1 = label.split("/");
            String path = "";
            for (int i = 0; i < split1.length; i++) {
                path += split1[i] + "/";
                AppLabelBean<IntentBean> appLabelBean = new AppLabelBean<>(null, split1[i], path, (i + 1), null);
                if (i == split1.length - 1) {
                    appLabelBean.setData(intentBean);
                }
                tree.add(appLabelBean);
            }
        }

        mData = new ArrayList<>();
        mCurrentLabelBeanList = tree.getFirst().getNext();
        for (AppLabelBean<IntentBean> labelBean : mCurrentLabelBeanList) {
            mData.add(labelBean.getTag());
        }
        mAdapter = new MyListAdapter<>(this, mData);
        mHomeList.setAdapter(mAdapter);
        mHomeList.setOnItemClickListener(this);

        Log.i(TAG, "onCreate: ActivityLabelTree is " + tree.toString());
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void addActivityToList() {

    }

    @Override
    protected void removeActivityForList() {

    }

    /**
     * 组装Intent
     *
     * @param resolvePackageName 包名
     * @param componentName      类名
     * @return 启动Intent
     */
    private Intent componentIntent(String resolvePackageName, String componentName) {
        return new Intent().setClassName(resolvePackageName, componentName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " + mData.size() + " " + mData.toString());
        Toast.makeText(this, mData.get(position), Toast.LENGTH_SHORT).show();
        AppLabelBean<IntentBean> appLabelBean = mCurrentLabelBeanList.get(position);
        if (appLabelBean.getNext() == null) {
            IntentBean intentBean = appLabelBean.getData();
            Intent intent = componentIntent(intentBean.getPackageName(), intentBean.getClassName());
            startActivity(intent);
            overridePendingTransition(R.anim.anim_activity_go_translate_enter, R.anim.anim_activity_go_translate_exit);
//            overridePendingTransition(R.anim.anim_activity_go_scale_enter, R.anim.anim_activity_go_scale_exit);
            getLabelTagAgain(appLabelBean.getParent());
        } else {
            getLabelTagAgain(appLabelBean);
        }
    }

    /**
     * 重新获取当前高度的所有Tag
     *
     * @param appLabelBean 当前位置的总父节点
     */
    private void getLabelTagAgain(AppLabelBean<IntentBean> appLabelBean) {
        mData.clear();
        mAdapter.clearData();
        mCurrentLabelBeanList = appLabelBean.getNext();
        for (AppLabelBean<IntentBean> labelBean : mCurrentLabelBeanList) {
            mData.add(labelBean.getTag());
        }
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: " + mCurrentLabelBeanList.size() + " " + mCurrentLabelBeanList.get(0).toString());
        if (mCurrentLabelBeanList.size() > 0 && mCurrentLabelBeanList.get(0).getParent().getParent() != null) {
            getLabelTagAgain(mCurrentLabelBeanList.get(0).getParent().getParent());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public TestViewHolder createViewHolder(int position, ViewGroup parent) {
        return new TestViewHolder(getLayoutInflater().inflate(R.layout.item_home_list, parent, false));
    }

    @Override
    public void bindData(int position, TestViewHolder holder, String data) {
        holder.mTitle.setText(data);
    }

    static class TestViewHolder extends MyListAdapter.ViewHolder {

        TextView mTitle;
        ImageView mIcon;

        private TestViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_home_list_title);
            mIcon = itemView.findViewById(R.id.item_home_list_icon);
        }
    }

}
