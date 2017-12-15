package jli170.com.addsubview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jingtian(Tansent).
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView iv_sub;
    private TextView tv_value;
    private ImageView iv_add;
    private int value = 1; //by default
    private int minValue = 1;
    private int maxValue = 5;

    /*
    constructor with TWO parameters
     */
    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = findViewById(R.id.iv_sub);
        tv_value = findViewById(R.id.tv_value);
        iv_add = findViewById(R.id.iv_add);

        int val = getValue();
        setValue(val);

        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
        }
    }

    private void addNumber() {
        if (value < maxValue) value++;
        setValue(value);
        if (onNumberChangeListener!=null){
            onNumberChangeListener.onChange(value);
        }
    }

    private void subNumber() {
        if (value > minValue) value--;
        setValue(value);
        if (onNumberChangeListener!=null){
            onNumberChangeListener.onChange(value);
        }
    }

    public int getValue() {
        String valueStr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /*
    when data is changed
     */
    public interface OnNumberChangeListener{
        public void onChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
