package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dugaolong on 17/3/1.
 */

public class HistoryActivity extends Activity implements HistoryAdapter.OnRecyclerViewItemClickListener
        , HistoryAdapter.OnRecyclerViewItemLongClickListener {

    private static final String TAG = "HistoryActivity";
    private static RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter mAdapter;
    private List<QrCode> mDatas = new ArrayList<QrCode>();
    private List<QrCode> dataDb;

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
        dataDb = DataSupport.findAll(QrCode.class);
        mDatas.addAll(dataDb);
        mRecyclerView.setAdapter(mAdapter = new HistoryAdapter(mDatas));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrashData();

    }

    //刷新数据
    public void refrashData() {
        dataDb = DataSupport.findAll(QrCode.class);
        mDatas.clear();
        mDatas.addAll(dataDb);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {
//        ToastUtils.showToast(mDatas.get(position).getContent());
        Intent intent = new Intent(this, HistoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mDatas.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onItemLongClick(View view, final int position) {
        Snackbar.make(view, "Are you sure to delete this data?", Snackbar.LENGTH_LONG)
                .setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除item
                        QrCode qrCode = mDatas.get(position);
                        qrCode.delete();
                        refrashData();
                    }
                })
                .show();
    }
}
