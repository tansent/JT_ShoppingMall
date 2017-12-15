package jli170.com.shoppingmall.home.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.base.BaseFragment;
import jli170.com.shoppingmall.home.adapter.HomeFragmentAdapter;
import jli170.com.shoppingmall.home.bean.ResultBeanData;
import jli170.com.shoppingmall.utils.Constants;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Jingtian(Tansent).
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView rvHome;
    private ImageView ib_top;
//    private HomeRecycleAdapter adapter;
    private TextView tv_search_home;
    private TextView tv_message_home;
    ResultBeanData.ResultBean resultBean;
    private static final String TAG = "HomeFragment";
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);
        initListener();
        return view;
    }

    @Override
    public void initData() {

        getDataFromServer();
    }

    private void getDataFromServer() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "fail: "+ e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "success: "+ response);
                        parseData(response);
                    }

                });
    }

    private void parseData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null){
            //there is data, set adapter
            adapter = new HomeFragmentAdapter(mContext, resultBean);
            rvHome.setAdapter(adapter);
            // recycleView has to set layoutManager
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
            //listen when scroll to the certain item position
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < 3){
                        ib_top.setVisibility(View.GONE);
                    }else {
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            rvHome.setLayoutManager(gridLayoutManager);
        }else {

        }
    }



    private void initListener() {
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MessageCenterActivity.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
