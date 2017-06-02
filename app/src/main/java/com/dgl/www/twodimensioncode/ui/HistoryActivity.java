package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dugaolong on 17/3/1.
 */

public class HistoryActivity extends Activity {

    private static final String TAG = "HistoryActivity";
    private static RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter mAdapter;
    private List<QrCode> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        setTitle("历史记录");

        initview();

    }

    private void initview() {
        mRecyclerView = (RecyclerView) findViewById(R.id.ll_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = DataSupport.findAll(QrCode.class);
        mRecyclerView.setAdapter(mAdapter = new HistoryAdapter());
    }


    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    HistoryActivity.this).inflate(R.layout.item_history, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position).getContent());
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }

}
