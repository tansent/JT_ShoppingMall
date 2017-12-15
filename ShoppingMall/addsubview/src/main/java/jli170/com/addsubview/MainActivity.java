package jli170.com.addsubview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    AddSubView addSubView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSubView = findViewById(R.id.add_sub_view);
        addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onChange(int value) {
                Toast.makeText(MainActivity.this, value+"", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
