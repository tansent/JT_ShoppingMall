package jli170.com.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;
//import com.youth.banner.listener.OnLoadImageListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.app.GoodsInfoActivity;
import jli170.com.shoppingmall.home.bean.GoodsBean;
import jli170.com.shoppingmall.home.bean.ResultBeanData;
import jli170.com.shoppingmall.utils.Constants;

/**
 * Created by Jingtian(Tansent).
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {


    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsbean";
    private LayoutInflater mLayoutInflater; //view
    private Context context;
    private ResultBeanData.ResultBean resultBean;

    /**
     * 当前类型
     */
    public int currentType = BANNER;


    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.context = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(context); //better than View
    }

    /*
    set viewholder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), context);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), context);
        } else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), context);
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), context);
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null), context);
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null), context);
        }
        return null;
    }

    /*
    set data
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommViewHolder = (RecommendViewHolder) holder;
            recommViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        Context context;
        View view;
        private Banner banner;

        public BannerViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            banner = (Banner) view.findViewById(R.id.banner);

        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            List<String> imageUrl = new LinkedList<>();
            List<String> bannerTitles = new LinkedList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imageUrl.add(Constants.BASE_IMAGE_URL + banner_info.get(i).getImage());
                bannerTitles.add("title " + i);
            }
            Log.e("Adapter", imageUrl.toString());
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(imageUrl);
            banner.setBannerTitles(bannerTitles);
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context,"" + position,Toast.LENGTH_SHORT).show();
//                    startGoodsActivityInfo(goodsBean);
                }
            });
            banner.start();
//            banner.setImages(imageUrl, new OnLoadImageListener() {
//                @Override
//                public void OnLoadImage(ImageView view, Object url) {
//                    //connect internet to load images using "Glide"
//                    Glide.with(context).load(Constants.BASE_IMAGE_URL + url).into(view);
//
//                }
//            });
        }
    }

    private void startGoodsActivityInfo(GoodsBean goodsBean) {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        context.startActivity(intent);
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    private class ChannelViewHolder extends RecyclerView.ViewHolder {

        Context context;
        GridView gridView;
        ChannelAdapter adapter;

        public ChannelViewHolder(View view, final Context context) {
            super(view);
            this.context = context;
            gridView = (GridView) view.findViewById(R.id.gv_channel);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            // it is grid view, need to show with an adapter
            adapter = new ChannelAdapter(context, channel_info);
            gridView.setAdapter(adapter);
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ViewPager viewPager;

        public ActViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            viewPager = view.findViewById(R.id.act_viewpager);
            //viewPager doesn't has a onItemClickListener on the outside, need to set inside on its image
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {

            viewPager.setPageMargin(20); //set margin for each pager in between
            viewPager.setOffscreenPageLimit(3);//>=3
            //setPageTransformer 决定动画效果
            viewPager.setPageTransformer(true,
                    new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));

            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView view = new ImageView(context);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    //绑定数据
                    Glide.with(context)
                            .load(Constants.BASE_IMAGE_URL + act_info.get(position).getIcon_url())
                            .into(view);
                    container.addView(view);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
        }
    }


    private long dt;

    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        TextView tvTime;
        private RecyclerView recyclerView;
        private SeckillRecyclerViewAdapter adapter;
        Context context;

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt -= 1000;
                //SimpleDateFormat can transform mili-second to date format
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String format = formatter.format(new Date(dt));
                tvTime.setText(format);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt < 0) {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SeckillViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {

            // set recycleview adapter
            adapter = new SeckillRecyclerViewAdapter(context, seckill_info.getList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

            //set item click listener
            adapter.setOnSecKillRecyclerView(new SeckillRecyclerViewAdapter.OnSecKillRecyclerView() {
                @Override
                public void onItemClick(int position) {
//                    Toast.makeText(context,"" + position,Toast.LENGTH_SHORT).show();

                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(seckill_info.getList().get(position).getCover_price());
                    goodsBean.setFigure(seckill_info.getList().get(position).getFigure());
                    goodsBean.setName(seckill_info.getList().get(position).getName());
                    goodsBean.setProduct_id(seckill_info.getList().get(position).getProduct_id());
                    startGoodsActivityInfo(goodsBean);
                }
            });

            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());

            handler.sendEmptyMessageDelayed(0, 1000);
        }

    }


    private class RecommendViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView tvMoreRecom;
        GridView gvRecommend;

        public RecommendViewHolder(View view, final Context context) {
            super(view);
            this.context = context;
            tvMoreRecom = view.findViewById(R.id.tv_more_recommend);
            gvRecommend = view.findViewById(R.id.gv_recommend);


        }


        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(context, recommend_info);
            gvRecommend.setAdapter(adapter);

            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Toast.makeText(context,"" + i,Toast.LENGTH_SHORT).show();
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommend_info.get(position).getCover_price());
                    goodsBean.setFigure(recommend_info.get(position).getFigure());
                    goodsBean.setName(recommend_info.get(position).getName());
                    goodsBean.setProduct_id(recommend_info.get(position).getProduct_id());
                    startGoodsActivityInfo(goodsBean);
                }
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public HotViewHolder(View view, final Context context) {
            super(view);
            tv_more_hot = (TextView) view.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) view.findViewById(R.id.gv_hot);
            this.mContext = context;


        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hot_info);
            gv_hot.setAdapter(adapter);


            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                     Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hot_info.get(position).getCover_price());
                    goodsBean.setFigure(hot_info.get(position).getFigure());
                    goodsBean.setName(hot_info.get(position).getName());
                    goodsBean.setProduct_id(hot_info.get(position).getProduct_id());
                    startGoodsActivityInfo(goodsBean);
                }
            });
        }
    }
}
