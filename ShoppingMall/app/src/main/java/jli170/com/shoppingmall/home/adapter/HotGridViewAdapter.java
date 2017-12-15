package jli170.com.shoppingmall.home.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.home.bean.ResultBeanData;
import jli170.com.shoppingmall.utils.Constants;


public class HotGridViewAdapter extends BaseAdapter {
    Context context;
    List<ResultBeanData.ResultBean.HotInfoBean> list;
    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.context = mContext;
        list = hot_info;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_hot_grid_view, null);
            viewHolder.ivHot = convertView.findViewById(R.id.iv_hot);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = list.get(i);
        Glide.with(context).load(Constants.BASE_IMAGE_URL + hotInfoBean.getFigure()).into(viewHolder.ivHot);
        viewHolder.tvName.setText(hotInfoBean.getName());
        viewHolder.tvPrice.setText("$" + hotInfoBean.getCover_price());
        return convertView;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        ImageView ivHot;
        TextView tvName;
        TextView tvPrice;
    }


//    private Context mContext;
//    private List<ResultBeanData.ResultBean.HotInfoBean> data;
//
//    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> data) {
//        this.mContext = mContext;
//        this.data = data;
//    }
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
//            holder = new ViewHolder();
//            holder.ivHot = convertView.findViewById(R.id.iv_hot);
//            holder.tvName = convertView.findViewById(R.id.tv_name);
//            holder.tvPrice = convertView.findViewById(R.id.tv_price);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(position);
//        Glide.with(mContext)
//                .load(Constants.BASE_IMAGE_URL +hotInfoBean.getFigure())
//                .into(holder.ivHot);
//        holder.tvName.setText(hotInfoBean.getName());
//        holder.tvPrice.setText("$" + hotInfoBean.getCover_price());
//        return convertView;
//    }
//
//    static class ViewHolder {
//        ImageView ivHot;
//        TextView tvName;
//        TextView tvPrice;
//
//    }
}
