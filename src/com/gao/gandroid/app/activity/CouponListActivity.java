package com.gao.gandroid.app.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gao.gandroid.R;
import com.gao.gandroid.app.adapter.CouponListAdapter;
import com.gao.gandroid.core.ActionCallbackListener;
import com.gao.gandroid.model.CouponBO;

/**
 * 券列表
 *
 * @version 1.0 创建时间：15/6/28
 */
public class CouponListActivity extends KBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private CouponListAdapter listAdapter;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        initViews();
        getData();

        // TODO 添加上拉加载更多的功能
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.list_view);
        listAdapter = new CouponListAdapter(this);
        listView.setAdapter(listAdapter);
    }

    private void getData() {
        this.appAction.listCoupon(currentPage, new ActionCallbackListener<List<CouponBO>>() {
            @Override
            public void onSuccess(List<CouponBO> data) {
                if (!data.isEmpty()) {
                    if (currentPage == 1) { // 第一页
                        listAdapter.setItems(data);
                    } else { // 分页数据
                        listAdapter.addItems(data);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        // 需要重置当前页为第一页，并且清掉数据
        currentPage = 1;
        listAdapter.clearItems();
        getData();
    }
}
