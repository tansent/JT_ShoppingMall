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

/**
 * Created by Jingtian(Tansent).
 */

public class RecommendGridViewAdapter extends BaseAdapter {
    Context context;
    List<ResultBeanData.ResultBean.RecommendInfoBean> list;
    public RecommendGridViewAdapter(Context context, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.context = context;
        list = recommend_info;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.ivRecommend = convertView.findViewById(R.id.iv_recommend);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(Constants.BASE_IMAGE_URL + list.get(i).getFigure()).into(viewHolder.ivRecommend);
        viewHolder.tvName.setText(list.get(i).getName());
        viewHolder.tvPrice.setText("$"+list.get(i).getCover_price());

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
        ImageView ivRecommend;
        TextView tvName;
        TextView tvPrice;
    }

}
