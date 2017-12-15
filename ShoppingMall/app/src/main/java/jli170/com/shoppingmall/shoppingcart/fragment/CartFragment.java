package jli170.com.shoppingmall.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.base.BaseFragment;
import jli170.com.shoppingmall.home.bean.GoodsBean;
import jli170.com.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import jli170.com.shoppingmall.shoppingcart.utils.CartStorage;

/**
 * Created by Jingtian(Tansent).
 */

public class CartFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private LinearLayout empty_cart;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        tvShopcartEdit = (TextView)view.findViewById( R.id.tv_shopcart_edit );
        recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
        llCheckAll = (LinearLayout)view.findViewById( R.id.ll_check_all );
        checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
        tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
        btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
        llDelete = (LinearLayout)view.findViewById( R.id.ll_delete );
        cbAll = (CheckBox)view.findViewById( R.id.cb_all );
        btnDelete = (Button)view.findViewById( R.id.btn_delete );
        btnCollection = (Button)view.findViewById( R.id.btn_collection );

        empty_cart = view.findViewById(R.id.ll_empty_shopcart);
        ivEmpty = (ImageView)view.findViewById( R.id.iv_empty );
        tvEmptyCartTobuy = (TextView)view.findViewById( R.id.tv_empty_cart_tobuy );

        btnCheckOut.setOnClickListener( this );
        btnDelete.setOnClickListener( this );
        btnCollection.setOnClickListener( this );

        setListener();

        return view;
    }

    private void setListener() {
        tvShopcartEdit.setTag(ACTION_EDIT); //tag is invisible, just for status judgement
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();
                if (tag == ACTION_EDIT){
                    //change to "complete" mode
                    showDelete();
                }else {
                    //change to "edit" mode
                    hideDelete();
                }

            }
        });
    }

    private void hideDelete() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        if(adapter != null){
            adapter.setAllCheckBoxes(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);
    }

    private void showDelete() {
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        if(adapter != null){
            adapter.setAllCheckBoxes(false);
            adapter.checkAll();
        }
        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
//        showData();
    }

    /*
    when the fragment regain focus
     */
    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private ShoppingCartAdapter adapter;
    private void showData() {
        List<GoodsBean> allGoods = CartStorage.getCartStorage().getAllData();
        tvShopcartEdit.setVisibility(View.VISIBLE);
        if (allGoods!=null && allGoods.size() > 0){
            empty_cart.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.VISIBLE);

            adapter = new ShoppingCartAdapter(mContext, allGoods, tvShopcartTotal, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        }
        else {
            emptyShoppingCart();
        }

    }

    private void emptyShoppingCart() {
        empty_cart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE); //if we have set Gone, we must not forget to set Visibility
        llDelete.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnCheckOut ) {
            // Handle clicks for btnCheckOut
        } else if ( v == btnDelete ) {
            adapter.deleteData();
            adapter.checkAll();
            if (adapter.getItemCount() == 0){ //judging count based on adapter
                emptyShoppingCart();
            }

        } else if ( v == btnCollection ) {
            // Handle clicks for btnCollection
        }
    }
}
