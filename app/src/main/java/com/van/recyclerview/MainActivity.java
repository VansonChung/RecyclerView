package com.van.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // "實際" item 大小為灰色 (除 Rectangle).
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            case R.id.button2:
                // "實際" item 大小為灰色.
                startActivity(new Intent(this, HorizontalActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, GridActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, FlexibleActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, OSwipeRecyclerViewActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this, TSwipeRecyclerViewActivity.class));
                break;
        }
    }
}
