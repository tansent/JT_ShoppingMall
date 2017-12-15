package jli170.com.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jli170.com.shoppingmall.R;
import jli170.com.shoppingmall.home.adapter.HomeFragmentAdapter;
import jli170.com.shoppingmall.home.bean.GoodsBean;
import jli170.com.shoppingmall.shoppingcart.utils.CartStorage;
import jli170.com.shoppingmall.utils.Constants;

public class GoodsInfoActivity extends Activity {

    @InjectView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @InjectView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @InjectView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @InjectView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @InjectView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @InjectView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @InjectView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @InjectView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @InjectView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @InjectView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @InjectView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @InjectView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @InjectView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @InjectView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @InjectView(R.id.tv_more_home)
    TextView tvMoreHome;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;
    GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.inject(this);
        //receive data from intent

        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsbean");
        if (goodsBean != null){
//            Toast.makeText(this,goodsBean.toString(),Toast.LENGTH_SHORT).show();
            setDataforView(goodsBean);
        }
    }

    private void setDataforView(GoodsBean goodsBean) {
        String name = goodsBean.getName();
        String cover_price = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String product_id = goodsBean.getProduct_id();
        Glide.with(this).load(Constants.BASE_IMAGE_URL + figure).into(ivGoodInfoImage);
        if (name != null) {
            tvGoodInfoName.setText(name);
        }
        if (cover_price != null) {
            tvGoodInfoPrice.setText("$" + cover_price);
        }
        setWebView(product_id);
    }

    private void setWebView(String product_id) {

        if (product_id != null) {
            //http://192.168.51.104:8080/atguigu/json/GOODSINFO_URL.json2691
//            wbGoodInfoMore.loadUrl(Constants.GOODSINFO_URL + product_id);
            wbGoodInfoMore.loadUrl("http://www.atguigu.com");

            //启用支持javascript
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setBuiltInZoomControls(true);

            //优先使用缓存
            wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // tel:110
                    System.out.println("跳转url:" + url);
                    view.loadUrl(url); //use our web view to load web page
                    return true;
                    // return super.shouldOverrideUrlLoading(view, url); //use default system browser to load page
                }
            });
        }
    }


    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.btn_good_info_addcart, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_good_info_more:
                Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_good_info_addcart:
                CartStorage.getCartStorage().addData(goodsBean);
                Toast.makeText(this, "Add to Cart successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_callcenter:
                Toast.makeText(this, "tv_good_info_callcenter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "tv_good_info_collection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                Toast.makeText(this, "tv_good_info_cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_share:
                Toast.makeText(this, "tv_more_share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "tv_more_search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(this, "tv_more_home", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
