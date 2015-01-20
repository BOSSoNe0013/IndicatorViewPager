package com.b1project.indicatorviewpager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

/**
 * Lu Nutrition
 * fr.mondelezinternational.nutrition.ui.util
 * Created by Cyril Bosselut on 13/11/14.
 */
public class IndicatorViewPager extends android.support.v4.view.ViewPager {
    private static final String TAG = IndicatorViewPager.class.getSimpleName();

    public static final int INDICATOR_SHAPE_SQUARE = 0x00;
    public static final int INDICATOR_SHAPE_CIRCLE = 0x01;

    public static final int INDICATOR_POSITION_TOP = 0x00;
    public static final int INDICATOR_POSITION_BOTTOM = 0x01;

    private int mIndicatorShape = INDICATOR_SHAPE_SQUARE;
    private int mIndicatorPosition = INDICATOR_POSITION_TOP;

    private int mIndicatorColor = 0xff808080;
    private int mIndicatorSelectionColor = 0xff000000;

    private int mIndicatorVerticalMargin = 0;
    private int mIndicatorPadding = 2;
    private int mIndicatorRadius = 5;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mCurrentOffset;

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorViewPager);
        mIndicatorPosition = a.getInt(R.styleable.IndicatorViewPager_indicatorPosition, INDICATOR_POSITION_TOP);
        mIndicatorShape = a.getInt(R.styleable.IndicatorViewPager_indicatorShape, INDICATOR_SHAPE_SQUARE);
        mIndicatorColor = a.getColor(R.styleable.IndicatorViewPager_indicatorColor, 0xff808080);
        mIndicatorSelectionColor = a.getColor(R.styleable.IndicatorViewPager_indicatorSelectionColor, 0xff000000);
        mIndicatorVerticalMargin = a.getDimensionPixelOffset(R.styleable.IndicatorViewPager_indicatorVerticalMargin, 0);
        mIndicatorPadding = a.getDimensionPixelOffset(R.styleable.IndicatorViewPager_indicatorPadding, 2);
        mIndicatorRadius = a.getDimensionPixelOffset(R.styleable.IndicatorViewPager_indicatorRadius, 5);
        a.recycle();
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        mCurrentOffset = getWidth() * position + offsetPixels;
    }

    /**
     * {@inheritDoc}
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        PagerAdapter pagerAdapter = getAdapter();
        if(pagerAdapter != null) {
            int width = getWidth();
            Resources r = getResources();
            float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorPadding, r.getDisplayMetrics());
            float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorVerticalMargin, r.getDisplayMetrics());
            float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorRadius, r.getDisplayMetrics());
            float indicatorWidth = ((2 * radius + padding) * getChildCount()) - padding;
            float cx = (width - indicatorWidth) / 2 + radius + mCurrentOffset;
            float cy = padding + radius + margin;
            float tx = cx - radius;
            float ty = margin;

            if (mIndicatorPosition == INDICATOR_POSITION_BOTTOM && getPaddingBottom() < (radius + padding) * 2) {
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), (int) (getPaddingBottom() + (radius + padding) * 2));
            }
            else if (mIndicatorPosition == INDICATOR_POSITION_TOP && getPaddingTop() < (radius + padding) * 2){
                setPadding(getPaddingLeft(), (int) (getPaddingTop() + (radius + padding) * 2), getPaddingRight(), getPaddingBottom());
            }

            if (mIndicatorPosition == INDICATOR_POSITION_BOTTOM) {
                cy = getHeight() - padding - radius - margin;
                ty = getHeight() - radius * 2 - margin;
            }

            canvas.save();

            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                if (i == getCurrentItem()) {
                    mPaint.setColor(mIndicatorSelectionColor);
                } else {
                    mPaint.setColor(mIndicatorColor);
                }
                switch (mIndicatorShape) {
                    case INDICATOR_SHAPE_SQUARE:
                        canvas.drawRect(tx, ty, tx + radius * 2, ty + radius * 2, mPaint);
                        break;
                    case INDICATOR_SHAPE_CIRCLE:
                        canvas.drawCircle(cx, cy, radius, mPaint);
                        break;
                }
                cx += 2 * radius + padding;
                tx = cx - radius;
            }
            canvas.restore();
        }
		super.onDraw(canvas);
    }
}
