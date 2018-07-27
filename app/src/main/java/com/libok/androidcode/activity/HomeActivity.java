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
import com.libok.androidcode.model.AppLabelBean;
import com.libok.androidcode.util.StatusBarUtil;
import com.libok.androidcode.view.adapter.MyListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MyListAdapter.ViewCreator<String, HomeActivity.TestViewHolder> {

    private static final String TAG = "HomeActivity";

    public static int count = 0;

    @BindView(R.id.home_list)
    ListView mHomeList;
    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.top_toolbar)
    Toolbar mTopToolbar;
    private int[] ids = {R.drawable.ic_launcher, R.drawable.ic_launcher_round};
    private List<String> mDatas;
    private MyListAdapter mAdapter;
    private Map<String, List<AppLabelBean>> mAppLabelBeanMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StatusBarUtil.immerseStatusBar(this);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("Coder");

        String qwe = "a";
        String[] split = qwe.split("/");
        for (String s : split) {
            Log.e(TAG, "onCreate: qwe  " + s);
        }

        Intent intent = new Intent("AndroidCode", null);
        intent.addCategory("code");
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        Log.e(TAG, "onCreate: " + resolveInfos.size());
        String header = "";
        mAppLabelBeanMap = new LinkedHashMap<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            String label = resolveInfo.loadLabel(packageManager).toString();
            AppLabelBean appLabelBean = new AppLabelBean(label.split("/"), resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            Log.e(TAG, "onCreate: " + appLabelBean.toString() + " " + appLabelBean.getLabelSplite().length);
            if (!mAppLabelBeanMap.containsKey(appLabelBean.getLabelSplite()[0])) {
                List<AppLabelBean> list = new ArrayList<>();
                list.add(appLabelBean);
                mAppLabelBeanMap.put(appLabelBean.getLabelSplite()[0], list);
            } else {
                mAppLabelBeanMap.get(appLabelBean.getLabelSplite()[0]).add(appLabelBean);
            }

//            Log.e(TAG, "onCreate: " + label);
//            if (header.length() == 0) {
//                String[] split = label.split("/");
//                for (String s : split) {
//                    Log.e(TAG, "onCreate: " + s);
//                }
//                Log.e(TAG, "onCreate: \n");
////                componentIntent(resolveInfo.resolvePackageName, resolveInfo.activityInfo.name);
//            }

        }
        mDatas = new ArrayList<>();

        for (Map.Entry<String, List<AppLabelBean>> entry : mAppLabelBeanMap.entrySet()) {
            mDatas.add(entry.getKey());
        }
        mAdapter = new MyListAdapter<>(this, mDatas);
        mHomeList.setAdapter(mAdapter);
        mHomeList.setOnItemClickListener(this);
    }

    private Intent componentIntent(String resolvePackageName, String componentName) {
        return new Intent().setClassName(resolvePackageName, componentName);
    }

    private void addNextItemToList() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        count++;
        List<AppLabelBean> labelBeans = mAppLabelBeanMap.get(mDatas.get(position));
        Log.e(TAG, "onItemClick: " + labelBeans.size());
        if (labelBeans.size() > 1) {
            List<String> list = new ArrayList<>();
            for (AppLabelBean labelBean : labelBeans) {
                if (!list.contains(labelBean.getLabelSplite()[count])) {
                    list.add(labelBean.getLabelSplite()[count]);
                }
            }
            mAdapter.resetDatas(list);
        } else {
            AppLabelBean appLabelBean = labelBeans.get(0);
            startActivity(componentIntent(appLabelBean.getPackageName(), appLabelBean.getClassName()));
        }
//        List<String> list = new ArrayList<>();
//        for (AppLabelBean labelBean : labelBeans) {
//            if (labelBean.getLabelSplite().length == 1) {
//                startActivity(componentIntent());
//            }
//        }
        Toast.makeText(this, mDatas.get(position), Toast.LENGTH_SHORT).show();
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
