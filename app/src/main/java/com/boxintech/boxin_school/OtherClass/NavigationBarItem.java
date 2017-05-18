package com.boxintech.boxin_school.OtherClass;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LZL on 2017/3/25.
 */

public class NavigationBarItem {
    LinearLayout layout;
    ImageView imageView;
    TextView textView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public TextView getTextView() {
        return textView;
    }
}
