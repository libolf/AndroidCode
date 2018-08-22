package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.libok.androidcode.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConstraintLayoutActivity extends AppCompatActivity {

    @BindView(R.id.constraint_toolbar)
    Toolbar mConstraintToolbar;
    @BindView(R.id.constraint_listview)
    ListView mConstraintListView;

    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mData.add("item" + (i + 1));
        }
        mConstraintListView.setAdapter(new ConstraintAdapter());
    }

    class ConstraintAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_constraint_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.icon = convertView.findViewById(R.id.item_constraint_icon);
                viewHolder.name = convertView.findViewById(R.id.item_constraint_file_name);
                viewHolder.size = convertView.findViewById(R.id.item_constraint_file_size);
                viewHolder.state = convertView.findViewById(R.id.item_constraint_state);
                viewHolder.index = convertView.findViewById(R.id.item_constraint_index);
                viewHolder.time = convertView.findViewById(R.id.item_constraint_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(mData.get(position));
            viewHolder.size.setText("2.20GB");
            viewHolder.state.setText("状态:领取成功");
            viewHolder.index.setText("所属区块:4123");
            viewHolder.time.setText("时间:10:20:20");

            return convertView;
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView name, size, state, index, time;
    }
}
