package com.itxiaox.xview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用Adapter,万能Adapter
 *
 * @param <T>
 * @author xiaoxiao
 * @ClassName: CommonAdapter
 * @Description: TODO
 * @date modify by 2015-9-21 下午4:38:27
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    /*
     add OnItemClickListener 和 onItemLongClickListener 还未经过项目测试，
    add by xx 2018/7/9 还未测试
    */
    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void onClick(int position, Object t);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onClickListener = listener;
    }

    private OnItemLongClickListener onLongClickListener;

    public interface OnItemLongClickListener {
        void onLongClick(int position, Object t);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    /**
     * 更新刷新数据，
     * ps：还未经过项目测试
     * @param datas
     */
    public void updateData(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        View converView = holder.getConvertView();
        converView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, mDatas.get(position));
            }
        });
        convertView.setOnLongClickListener(v -> {
            if (onLongClickListener != null) {
                onLongClickListener.onLongClick(position, mDatas.get(position));
            }
            return false;
        });
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
