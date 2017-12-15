package jli170.com.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.home.bean.ResultBeanData;
import jli170.com.shoppingmall.utils.Constants;

/**
 * Created by Jingtian(Tansent).
 */

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context context, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_seckill, null);
        return new SeckillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SeckillViewHolder secKillHolder = (SeckillViewHolder) holder; //if holder cannot be resolved properly, cast!

        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
        Glide.with(context)
                .load(Constants.BASE_IMAGE_URL + listBean.getFigure())
                .into(secKillHolder.imageFigure);
        secKillHolder.textCoverPrice.setText(listBean.getCover_price());
        secKillHolder.textOriginPrice.setText(listBean.getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SeckillViewHolder extends RecyclerView.ViewHolder{
         ImageView imageFigure;
         TextView textCoverPrice;
         TextView textOriginPrice;

        public SeckillViewHolder(View itemView){
            super(itemView);
            imageFigure = itemView.findViewById(R.id.iv_figure);
            textCoverPrice = itemView.findViewById(R.id.tv_cover_price);
            textOriginPrice = itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                  leveraging interface to invoke outside
                    if (onSecKillRecyclerView != null){
                        onSecKillRecyclerView.onItemClick(getLayoutPosition());
                    }

                }
            });

        }
    }

    //set onclick interface and setter so outside class can use
    public interface OnSecKillRecyclerView{
        public void onItemClick(int position);
    }
    private OnSecKillRecyclerView onSecKillRecyclerView;

    public void setOnSecKillRecyclerView(OnSecKillRecyclerView onSecKillRecyclerView) {
        this.onSecKillRecyclerView = onSecKillRecyclerView;
    }
}
