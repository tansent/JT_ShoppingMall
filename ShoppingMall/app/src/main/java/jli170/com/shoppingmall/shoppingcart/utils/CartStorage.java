package jli170.com.shoppingmall.shoppingcart.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;
import java.util.List;

import jli170.com.shoppingmall.app.MyApplication;
import jli170.com.shoppingmall.home.bean.GoodsBean;
import jli170.com.shoppingmall.utils.CacheUtils;

/**
 * Cart Helper class, to mulipulate cart data and save then locally
 */

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private static CartStorage cartStorage;
    private Context context;
    private SparseArray<GoodsBean> sparseArray; //to replace hashMap

    public static CartStorage getCartStorage(){
        if (cartStorage == null){
            cartStorage = new CartStorage(MyApplication.getContext());
        }
        return cartStorage;
    }

    public CartStorage(Context context) {
        this.context = context;
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }

    /*
    read data from local, then add them to the sparse array
    (locally, data is saved as List, but for convinience of mulipulating data,
     we transfer them to sparseArray)
     */
    private void listToSparseArray() {
        List<GoodsBean> list = getAllData();
        for(int i=0; i<list.size();i++){
            GoodsBean goodsBean = list.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    //getAllData from local shared Preference file
    public List<GoodsBean> getAllData() {
        List<GoodsBean> list = new LinkedList<>();
        //get data from local
        String json = CacheUtils.getString(context, JSON_CART);
        //parse json file using Gson
        if (!TextUtils.isEmpty(json)){
            list = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>(){}.getType());
        }
        return list;
    }


    public void addData(GoodsBean goodsBean){
        //1.add data to ram(sparseArray)
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if(tempData != null){
            tempData.setNumber(tempData.getNumber()+1); //1 click, 1 more item
        }else {
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),tempData);

        //2 sync to local file(sharedPreference)
        saveLocal();
    }

    public void deleteData(GoodsBean goodsBean){
        //1. delete in ram
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2. sync to local file(sharedPreference)
        saveLocal();
    }

    public void updateData(GoodsBean goodsBean){
        //1. update in ram
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //2. sync to local file(sharedPreference)
        saveLocal();
    }

    /*
    sync data to shred Preference(local / non-sdcard)
     */
    private void saveLocal(){
        // SparseArray to list
        List<GoodsBean> list = sparseArrayToList();
        // using Gson to transfer list to string
        String json = new Gson().toJson(list);
        // save String type data
        CacheUtils.saveString(context,JSON_CART,json);
    }

    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> list = new LinkedList<>();
        for(int i=0;i<sparseArray.size();i++){
            list.add(sparseArray.valueAt(i));
        }
        return list;
    }


}
