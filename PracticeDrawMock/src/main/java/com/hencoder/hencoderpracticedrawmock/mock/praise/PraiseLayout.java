package com.hencoder.hencoderpracticedrawmock.mock.praise;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hencoder.hencoderpracticedrawmock.R;

/**
 * Created by lizhiguang on 2017/12/15.
 */

public class PraiseLayout extends LinearLayout {
    private EditText mEditNumber, mEditTime;
    private Button mButton;
    private PraiseView mPraiseView, mPraiseViewSmall, mPraiseViewBig;

    public PraiseLayout(Context context) {
        this(context, null);
    }

    public PraiseLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mEditNumber = (EditText) findViewById(R.id.praise_number);
        mEditTime = (EditText) findViewById(R.id.praise_time);
        mButton = (Button) findViewById(R.id.praise_button);
        mPraiseView = (PraiseView) findViewById(R.id.praise_view);
        mPraiseViewBig = (PraiseView) findViewById(R.id.praise_view_big);
        mPraiseViewSmall = (PraiseView) findViewById(R.id.praise_view_small);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditTime.getText())) {
                    int duration = Integer.parseInt(mEditTime.getText().toString());
                    mPraiseView.setDuration(duration);
                    mPraiseViewSmall.setDuration(duration);
                    mPraiseViewBig.setDuration(duration);
                }
                if (!TextUtils.isEmpty(mEditNumber.getText())) {
                    int number = Integer.parseInt(mEditNumber.getText().toString());
                    mPraiseView.setNumber(number);
                    mPraiseViewBig.setNumber(number);
                    mPraiseViewSmall.setNumber(number);
                }
            }
        });
    }
}
