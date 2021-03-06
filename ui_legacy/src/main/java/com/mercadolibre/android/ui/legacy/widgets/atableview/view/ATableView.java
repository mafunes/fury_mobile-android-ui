package com.mercadolibre.android.ui.legacy.widgets.atableview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.mercadolibre.android.ui.legacy.widgets.atableview.internal.ATableViewAdapter;
import com.mercadolibre.android.ui.legacy.widgets.atableview.internal.ATableViewCellClickListener;
import com.mercadolibre.android.ui.legacy.widgets.atableview.protocol.ATableViewDataSource;
import com.mercadolibre.android.ui.legacy.widgets.atableview.protocol.ATableViewDelegate;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell.ATableViewCellSeparatorStyle;

/**
 * A table with iOS style for Android.
 */
@Deprecated
public class ATableView extends ListView {
    private static final ATableViewStyle DEFAULT_STYLE = ATableViewStyle.Plain;

    private ATableViewCellSeparatorStyle mSeparatorStyle = ATableViewCellSeparatorStyle.SingleLine;
    private int mSeparatorColor = -1;
    private ATableViewStyle mStyle = DEFAULT_STYLE;
    private ATableViewDataSource mDataSource;
    private ATableViewDelegate mDelegate = new ATableViewDelegate();

    public enum ATableViewStyle {
        Plain, Grouped
    }

    ;

    public ATableView(ATableViewStyle style, Context context) {
        super(context);
        mStyle = style;

        setSelector(android.R.color.transparent);
        setDivider(null);
        setDividerHeight(0);
        setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        setScrollingCacheEnabled(false);
    }

    public ATableView(Context context) {
        super(context);
    }

    public ATableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ATableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ATableViewCellSeparatorStyle getSeparatorStyle() {
        return mSeparatorStyle;
    }

    public void setSeparatorStyle(ATableViewCellSeparatorStyle separatorStyle) {
        mSeparatorStyle = separatorStyle;
    }

    public int getSeparatorColor() {
        return mSeparatorColor;
    }

    public void setSeparatorColor(int resId) {
        mSeparatorColor = resId;
    }

    public ATableViewStyle getStyle() {
        return mStyle;
    }

    public ATableViewDataSource getDataSource() {
        return mDataSource;
    }

    public void setDataSource(ATableViewDataSource dataSource) {
        mDataSource = dataSource;
    }

    public ATableViewDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(ATableViewDelegate delegate) {
        mDelegate = delegate;
    }

    public ATableViewAdapter getInternalAdapter() {

        // fixes bugs for tables which includes header / footer.
        ATableViewAdapter adapter = null;
        if (getAdapter() instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) getAdapter();
            adapter = (ATableViewAdapter) headerAdapter.getWrappedAdapter();
        } else {
            adapter = (ATableViewAdapter) getAdapter();
        }

        return adapter;
    }

    public void reloadData() {
        getInternalAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onAttachedToWindow() {
        setAdapter(new ATableViewAdapter(this));
        setOnItemClickListener(new ATableViewCellClickListener(this));
        super.onAttachedToWindow();
    }
}
