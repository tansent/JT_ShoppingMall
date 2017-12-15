package jli170.com.shoppingmall.type.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import jli170.com.shoppingmall.base.BaseFragment;

/**
 * Created by Jingtian(Tansent).
 */

public class TypeFragment extends BaseFragment {

    TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return  textView;
    }

    @Override
    public void initData() {
        textView.setText("TypeFragment");
    }
}
