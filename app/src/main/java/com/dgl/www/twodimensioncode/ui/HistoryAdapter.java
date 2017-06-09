package com.dgl.www.twodimensioncode.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;
import com.dgl.www.twodimensioncode.utils.DateUtil;

import java.util.List;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> implements View.OnClickListener ,View.OnLongClickListener{

    private List<QrCode> mDatas;

    public HistoryAdapter(List<QrCode> datas) {
        this.mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_history, parent,
                false);
        MyViewHolder vh = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.id_type.setText(mDatas.get(position).getType() == 1 ? "链接" : "文本");
        holder.id_time.setText(DateUtil.getMillisecondFormatDateTo24(mDatas.get(position).getTime()) + "");
        holder.id_content.setText(mDatas.get(position).getContent() + "");

        if (null != mDatas.get(position).getBlob()) {
            Bitmap imagebitmap = BitmapFactory.decodeByteArray(mDatas.get(position).getBlob(), 0, mDatas.get(position).getBlob().length);
            //将位图显示为图片
            holder.iv.setImageBitmap(imagebitmap);
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_type, id_time, id_content;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            id_type = (TextView) view.findViewById(R.id.id_type);
            id_time = (TextView) view.findViewById(R.id.id_time);
            id_content = (TextView) view.findViewById(R.id.id_content);
            iv = (ImageView) view.findViewById(R.id.iv);

        }
    }

    //点击事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    //点击长按
    public static interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemLongClickListener.onItemLongClick(view, (int)view.getTag());
        }
        return true;
    }
}