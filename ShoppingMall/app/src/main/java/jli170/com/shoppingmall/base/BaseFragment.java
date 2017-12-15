package jli170.com.shoppingmall.base;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jingtian(Tansent)
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    //UI created
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    public abstract View initView();


    //after activity created, we should get data from the server
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public abstract void initData();


//    //from the second time and forward visible
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if (hidden) {
//            //相当于Fragment的onPause
//            System.out.println("界面不可见");
//        } else {
//            // 相当于Fragment的onResume
//            System.out.println("界面可见");
//            initData();
//        }
//    }

}
