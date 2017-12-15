package jli170.com.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.home.bean.GoodsBean;
import jli170.com.shoppingmall.shoppingcart.utils.CartStorage;
import jli170.com.shoppingmall.shoppingcart.view.AddSubView;
import jli170.com.shoppingmall.utils.Constants;

/**
 * Created by Jingtian(Tansent).
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    Context context;
    List<GoodsBean> list;
    TextView tvShopcartTotal;
    CheckBox checkboxAll;
    CheckBox cbAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> allGoods, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        context = mContext;
        list = allGoods;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;

        showTotalPrice(); //show total price in checkboxAll Checkbox

        setListener();

        checkAll();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // find corresponding bean
                GoodsBean goodsBean = list.get(position);
                // reverse selection
                goodsBean.setSelected(!goodsBean.isSelected());
                // refresh view
                notifyDataSetChanged();
                // check if all checkBoxes are selected
                checkAll();
                // recalculate total price
                showTotalPrice();
                // update new status to local file
                CartStorage.getCartStorage().updateData(goodsBean);
            }
        });

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = checkboxAll.isChecked();
                setAllCheckBoxes(checked);
                showTotalPrice();
            }
        });

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = cbAll.isChecked();
                setAllCheckBoxes(checked);
            }
        });

    }

    /**
     * set all check boxes accroding to the "check all" check box
     * @param checked
     */
    public void setAllCheckBoxes(boolean checked) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GoodsBean goodsBean = list.get(i);
                goodsBean.setSelected(checked);
            }
            notifyDataSetChanged();
        }
    }

    public void checkAll() {
        if (list != null && list.size() > 0){
            int number = 0;
            for(int i=0;i<list.size();i++){
                GoodsBean goodsBean = list.get(i);
                if (goodsBean.isSelected())
                    number++;
            }
            if (number == list.size()){
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }else {
                checkboxAll.setChecked(false);
                cbAll.setChecked(false);
            }
        }else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shop_cart, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GoodsBean goodsBean = list.get(position);
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(context).load(Constants.BASE_IMAGE_URL+goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("$" + goodsBean.getCover_price());
        holder.numberAddSubView.setValue(goodsBean.getNumber());

        holder.numberAddSubView.setMaxValue(10);

        holder.numberAddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onChange(int value) {
                // change in ram
                goodsBean.setNumber(value);
                // change locally
                CartStorage.getCartStorage().updateData(goodsBean);
                // refresh adapter
//                notifyItemChanged(position); //refresh this line only (with unwanted flashing effect)
                notifyDataSetChanged();
                // calculate total price again
                showTotalPrice();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteData() {
        if (list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                GoodsBean goodsBean = list.get(i);
                if (goodsBean.isSelected()){
                    //remove in ram
                    list.remove(i);
                    //remove in local
                    CartStorage.getCartStorage().deleteData(goodsBean);
                    i--;
                }
            }
            notifyDataSetChanged();;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView numberAddSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = itemView.findViewById(R.id.numberAddSubView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("$"+getTotalPrice());
    }

    //calculate total price
    public double getTotalPrice() {
        double totalPrice = 0.0;
        if (list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                GoodsBean goodsBean = list.get(i);
                if (goodsBean.isSelected()){
                    totalPrice += goodsBean.getNumber() * Double.parseDouble(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }


    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
