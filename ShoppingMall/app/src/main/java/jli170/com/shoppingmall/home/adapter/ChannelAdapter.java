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

public class ChannelAdapter extends BaseAdapter {
    Context context;
    List<ResultBeanData.ResultBean.ChannelInfoBean> data;

    public ChannelAdapter(Context context, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.context = context;
        data = channel_info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_channel, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_channel);
            viewHolder.textView = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = data.get(position);
        Glide.with(context).load(Constants.BASE_IMAGE_URL + channelInfoBean.getImage()).into(viewHolder.imageView);
        viewHolder.textView.setText(channelInfoBean.getChannel_name());
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

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

}
