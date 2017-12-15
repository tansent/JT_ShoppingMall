package jli170.com.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.base.BaseFragment;
import jli170.com.shoppingmall.community.fragment.CommunityFragment;
import jli170.com.shoppingmall.home.fragment.HomeFragment;
import jli170.com.shoppingmall.shoppingcart.fragment.CartFragment;
import jli170.com.shoppingmall.type.fragment.TypeFragment;
import jli170.com.shoppingmall.user.fragment.UserFragment;

public class MainActivity extends FragmentActivity {

    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
//        rgMain.check(R.id.rb_home);

        initFragment();
        initListener();
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new CartFragment());
        fragments.add(new UserFragment());
    }


    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
//                        dismissAnmiation();
//                        typeFragment.hideFragment();
                        break;
                    case R.id.rb_type:

                        position = 1;
//                        dismissAnmiation();
                        //初始化数据
//                        int currentTab = typeFragment.getCurrentTab();
//                        if (currentTab == 0) {
//                            if (typeFragment.listFragment != null) {
//                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                                transaction.show(typeFragment.listFragment).commit();
//                            }
//                        } else {
//                            if (typeFragment.tagFragment != null) {
//                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                                transaction.show(typeFragment.tagFragment).commit();
//                            }
//                        }
                        break;
                    case R.id.rb_community:
                        position = 2;
//                        typeFragment.hideFragment();

                        break;
                    case R.id.rb_cart:
                        position = 3;

//                        fragments.remove(fragments.get(3));
//                        ShoppingCartFragment cartFragment = new ShoppingCartFragment();
//                        fragments.add(3, cartFragment);
//
//                        typeFragment.hideFragment();
                        break;
                    case R.id.rb_user:
                        position = 4;
//                        dismissAnmiation();
//                        typeFragment.hideFragment();
                        break;
                }

                BaseFragment baseFragment = getFragment(position);
                switchFragment(tempFragment, baseFragment);
            }
        });

        rgMain.check(R.id.rb_home);

    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment; //store this fragment as cache
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }




}
