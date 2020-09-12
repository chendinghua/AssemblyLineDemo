package com.ycexample.ycapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ycexample.ycapp.R;
import com.ycexample.ycapp.entry.ScanCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 任务信息列表  新增操作列表
 * Created by 16486 on 2020/1/9.
 */
public class ScanCodeAdapter extends BaseAdapter {
    private Context context;
    private List<ScanCode> list;


    public ScanCodeAdapter(Context context, List<ScanCode> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup viewGroup) {


        // 通过LayoutInflater 类的 from 方法 再 使用 inflate()方法得到指定的布局
        // 得到ListView中要显示的条目的布局
        View view = LayoutInflater.from(context).inflate(R.layout.activity_scan_code_items, null);
        ViewHolder holder = new ViewHolder(view);



        if (list != null) {
            holder.tvTitleScanCode.setText(list.get(position).getCode());
            holder.tvTitleScanCodeId.setText(list.get(position).getId().toString());


        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title_scan_code_id)
        TextView tvTitleScanCodeId;
        @BindView(R.id.tv_title_scan_code)
        TextView tvTitleScanCode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
