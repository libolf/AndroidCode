package com.libok.androidcode.view.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.List;

/**
 * @author liboK
 */
public class MyListAdapter<T, H extends MyListAdapter.ViewHolder> extends BaseAdapter {

    private List<T> mDatas;
    private ViewCreator<T, H> mViewCreator;

    public MyListAdapter(ViewCreator<T, H> viewCreator, @NonNull List<T> datas) {
        mViewCreator = viewCreator;
        mDatas = datas;
    }

    @Override

    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void resetDatas(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(int position, T t) {
        if (position > mDatas.size()) {
            throw new IllegalArgumentException("position is " + position + ", size is " + mDatas.size());
        }
        mDatas.add(position, t);
        notifyDataSetChanged();
    }

    public void addData(T t) {
        addData(mDatas.size(), t);
    }

    public T removeData(int position) {
        if (position > mDatas.size() - 1) {
        }
        T t = mDatas.remove(position);
        notifyDataSetChanged();
        return t;
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = mViewCreator.createViewHolder(position, parent);
            convertView = viewHolder.mItemView;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mViewCreator.bindData(position, (H) viewHolder, mDatas.get(position));

        return convertView;
    }

    public abstract static class ViewHolder {

        private View mItemView;

        public ViewHolder(View itemView) {
            mItemView = itemView;
            mItemView.setTag(this);
        }
    }

    public interface ViewCreator<T, H extends ViewHolder> {

        H createViewHolder(int position, ViewGroup parent);

        void bindData(int position, H holder, T data);
    }

}
