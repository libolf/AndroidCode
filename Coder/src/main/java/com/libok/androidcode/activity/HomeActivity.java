package com.libok.androidcode.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.core.ActivityLabelTree;
import com.libok.androidcode.util.StatusBarUtil;
import com.libok.androidcode.view.adapter.MyListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MyListAdapter.ViewCreator<String, HomeActivity.TestViewHolder> {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.home_list)
    ListView mHomeList;
    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.toolbar_fits)
    Toolbar mTopToolbar;
    private List<String> mDatas;
    private MyListAdapter<String, TestViewHolder> mAdapter;
    private List<AppLabelBean<IntentBean>> mCurrentLabelBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StatusBarUtil.immerseStatusBar(this);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("Coder");

        LApplication.addActivity(this);

        Intent intent = new Intent("AndroidCode", null);
        intent.addCategory("code");
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        Log.i(TAG, "onCreate: Activity size is " + resolveInfos.size());

        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));

        mCurrentLabelBeanList = new ArrayList<>();
        ActivityLabelTree<IntentBean> tree = new ActivityLabelTree<>(null, "first", null);
        for (ResolveInfo resolveInfo : resolveInfos) {
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

        mDatas = new ArrayList<>();
        mCurrentLabelBeanList = tree.getFirst().getNext();
        for (AppLabelBean<IntentBean> labelBean : mCurrentLabelBeanList) {
            mDatas.add(labelBean.getTag());
        }
        mAdapter = new MyListAdapter<>(this, mDatas);
        mHomeList.setAdapter(mAdapter);
        mHomeList.setOnItemClickListener(this);
        Log.i(TAG, "onCreate: ActivityLabelTree is " + tree.toString());
    }

    /**
     * 组装Intent
     * @param resolvePackageName 包名
     * @param componentName 类名
     * @return 启动Intent
     */
    private Intent componentIntent(String resolvePackageName, String componentName) {
        return new Intent().setClassName(resolvePackageName, componentName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " + mDatas.size() + " " + mDatas.toString());
        Toast.makeText(this, mDatas.get(position), Toast.LENGTH_SHORT).show();
        AppLabelBean<IntentBean> appLabelBean = mCurrentLabelBeanList.get(position);
        if (appLabelBean.getNext() == null) {
            IntentBean intentBean = appLabelBean.getData();
            Intent intent = componentIntent(intentBean.getPackageName(), intentBean.getClassName());
            startActivity(intent);
            getLabelTagAgain(appLabelBean.getParent());
        } else {
            getLabelTagAgain(appLabelBean);
        }
    }

    /**
     * 重新获取当前高度的所有Tag
     * @param appLabelBean
     */
    private void getLabelTagAgain(AppLabelBean<IntentBean> appLabelBean) {
        mDatas.clear();
        mAdapter.clearData();
        mCurrentLabelBeanList = appLabelBean.getNext();
        for (AppLabelBean<IntentBean> labelBean : mCurrentLabelBeanList) {
            mDatas.add(labelBean.getTag());
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
