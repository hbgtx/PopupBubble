package com.hb.popupbble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hemba on 9/22/2017.
 */

public class PopupBubble extends RelativeLayout {
    private TextView mTextView;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;

    private String TEXT = "New posts";
    private String TEXT_COLOR = "#ffffff";
    private String ICON_COLOR = "#ffffff";
    private String BACKGROUND_COLOR = "#dd424242";
    private Boolean SHOW_ICON = true;
    private Drawable ICON_DRAWABLE;

    private boolean animation = true;
    private RecyclerViewListener mRecyclerViewListener;
    private PopupBubbleClickListener mListener;

    //java inflation
    public PopupBubble(Context context) {
        super(context);
        mTextView = new TextView(context);
        mImageView = new ImageView(context);
        init();
    }

    //XML inflation
    public PopupBubble(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTextView = new TextView(context, attrs);
        mImageView = new ImageView(context);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupBubble, 0, 0);
        initAttributes(typedArray);
        typedArray.recycle();
        init();
    }

    public PopupBubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context, attrs, defStyleAttr);
        mImageView = new ImageView(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupBubble, 0, 0);
        initAttributes(typedArray);
        typedArray.recycle();
        init();
    }

    private void initAttributes(TypedArray typedArray) {
        String text = typedArray.getString(R.styleable.PopupBubble_pb_text);
        String text_color = typedArray.getString(R.styleable.PopupBubble_pb_textColor);
        String iconColor = typedArray.getString(R.styleable.PopupBubble_pb_iconColor);
        String backgroundColor = typedArray.getString(R.styleable.PopupBubble_pb_backgroundColor);
        Drawable iconDrawable = typedArray.getDrawable(R.styleable.PopupBubble_pb_icon);

        String font = typedArray.getString(R.styleable.PopupBubble_pb_font);

        if (text != null)
            TEXT = text;
        if (text_color != null)
            TEXT_COLOR = text_color;
        if (iconColor != null)
            ICON_COLOR = iconColor;
        if (backgroundColor != null)
            BACKGROUND_COLOR = backgroundColor;
        if (iconDrawable != null)
            ICON_DRAWABLE = iconDrawable;
        if (font != null)
            mTextView.setTypeface(Typeface.createFromAsset(typedArray.getResources().getAssets(), font));

        SHOW_ICON = typedArray.getBoolean(R.styleable.PopupBubble_pb_showIcon, true);
    }

    private void init() {
        //prepare background of the bubble
        setRoundedBackground();
        //set the icon
        if (SHOW_ICON)
            addIcon();
        //set the text
        addText();

        //move the whole layout to center of screen
        moveToCenter();

        //invalidate and redraw the views
        invalidate();
        requestLayout();


    }


    private void moveToCenter() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
        this.setLayoutParams(layoutParams);
    }

    private void addIcon() {
        mImageView.setId(R.id.image_view);
        if (ICON_DRAWABLE != null) {
            mImageView.setImageDrawable(ICON_DRAWABLE);
        } else {
            mImageView.setImageResource(R.drawable.ic_arrow_upward_white_18dp);
        }
        mImageView.setPadding(35, 20, 15, 25);
        mImageView.setColorFilter(Color.parseColor(ICON_COLOR), PorterDuff.Mode.SRC_ATOP);

        this.addView(mImageView);
    }

    private void addText() {
        mTextView.setText(TEXT);
        if (SHOW_ICON)
            mTextView.setPadding(00, 20, 35, 25);
        else
            mTextView.setPadding(35, 20, 35, 25);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, mImageView.getId());

        this.addView(mTextView, layoutParams);

    }

    private void setRoundedBackground() {
        RoundRectShape roundRectShape = new RoundRectShape(
                new float[]{50, 50, 50, 50, 50, 50, 50, 50},
                null,
                null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.parseColor("#DDDDDD"));

        ShapeDrawable shapeDrawable1 = new ShapeDrawable(roundRectShape);
        shapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(
                        0, 0, 0, height,

                        new int[]{Color.parseColor(BACKGROUND_COLOR),
                                Color.parseColor(BACKGROUND_COLOR)},
                        new float[]{0, 1},
                        Shader.TileMode.REPEAT
                );
                return linearGradient;
            }
        });
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable1, shapeDrawable});
        layerDrawable.setLayerInset(0, 2, 2, 0, 0);
        layerDrawable.setLayerInset(1, 0, 0, 2, 2);

        //this.setBackgroundDrawable(layerDrawable);
        this.setBackground(layerDrawable);

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();
        if (maskedAction == MotionEvent.ACTION_DOWN) {
            if (mListener != null) {
                mListener.BubbleClicked(getContext());
                if (animation) {
                    if (mRecyclerView != null)
                        mRecyclerView.smoothScrollToPosition(0);

                    AnimationUtils.popout(this, 1000, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    }).start();
                } else {
                    //remove all views
                    if (mRecyclerView != null)
                        mRecyclerView.removeAllViews();

                }
            }

        }

        invalidate();
        return true;
    }

    //on popupbubble click setter and interface
    public void setPopupBubbleListener(PopupBubbleClickListener listener) {
        mListener = listener;
    }

    public interface PopupBubbleClickListener {
        void BubbleClicked(Context context);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        this.hide();
    }

    //helper method can be accesed through object
    public void hide() {
        this.setVisibility(View.INVISIBLE);
    }

    public void show() {
        if (animation) {
            AnimationUtils.popup(this, 1000).start();
        } else {
            this.setVisibility(View.VISIBLE);
        }
    }

    public void activate() {
        this.show();
        mRecyclerViewListener = new RecyclerViewListener(this);
        this.mRecyclerView.addOnScrollListener(mRecyclerViewListener);
    }

    //to detach the bubble when it is clicked or user moved to top
    public void deactivate() {
        this.mRecyclerView.removeOnScrollListener(mRecyclerViewListener);
    }

    public void withAnimation(boolean animation) {
        this.animation = animation;
    }

    public void updateText(String text) {
        this.TEXT = text;
        this.mTextView.setText(this.TEXT);
    }

    //update font
    public void updateTypeface(Typeface typeface) {
        this.mTextView.setTypeface(typeface);
    }

    public void updateIcon(int iconRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateIcon(getResources().getDrawable(iconRes, getContext().getTheme()));
        } else {
            updateIcon(getResources().getDrawable(iconRes));
        }
    }

    public void updateIcon(Drawable drawable) {
        this.SHOW_ICON = true;
        this.ICON_DRAWABLE = drawable;
        if (ICON_DRAWABLE != null)
            mImageView.setImageDrawable(ICON_DRAWABLE);


    }


}
