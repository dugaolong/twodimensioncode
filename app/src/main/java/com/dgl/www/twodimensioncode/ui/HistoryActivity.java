package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dugaolong on 17/3/1.
 */

public class HistoryActivity extends Activity implements HistoryAdapter.OnRecyclerViewItemClickListener {

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
        mRecyclerView.setAdapter(mAdapter = new HistoryAdapter(mDatas));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatas = DataSupport.findAll(QrCode.class);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {
//        ToastUtils.showToast(mDatas.get(position).getContent());
        Intent intent = new Intent(this,HistoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",mDatas.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
