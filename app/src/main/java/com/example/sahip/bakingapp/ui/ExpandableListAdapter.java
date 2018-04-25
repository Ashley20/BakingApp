package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.sahip.bakingapp.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mHeaderList;
    private HashMap<String, List<String>> mItemList;

    public ExpandableListAdapter(Context mContext, List<String> mHeaderList,
             HashMap<String, List<String>> mItemList) {
        this.mContext = mContext;
        this.mHeaderList = mHeaderList;
        this.mItemList = mItemList;
    }

    @Override
    public int getGroupCount() {
        return this.mHeaderList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mItemList.get(this.mHeaderList.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mHeaderList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mItemList.get(this.mHeaderList.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layoutInflater != null) {
                view = layoutInflater.inflate(R.layout.list_group, null);
            }
        }

        TextView ingredientsHeaderTv = null;
        if (view != null) {
            ingredientsHeaderTv = (TextView) view.findViewById(R.id.ingredients_header_tv);
            ingredientsHeaderTv.setTypeface(null, Typeface.BOLD);
            ingredientsHeaderTv.setText(headerTitle);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final String itemText = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                view = infalInflater.inflate(R.layout.list_item, null);
            }
        }

        if (view != null) {
            TextView itemTv = (TextView) view.findViewById(R.id.ingredients_item_tv);
            itemTv.setText(itemText);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
