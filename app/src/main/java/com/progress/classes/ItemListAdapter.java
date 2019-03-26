package com.progress.classes;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.progress.web.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter implements Filterable {
    private List<Item> items;
    private Activity activity;

    public ItemListAdapter(List<Item> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.option_list, parent, false );

        Item item = items.get(position);

        TextView tv_item = view.findViewById(R.id.tv_option_list_item);

        tv_item.setText(item.getValue());

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filter = new FilterResults();
                String text = (String) constraint;

                if (constraint.length() == 0)
                    filter.values = items;
                else {
                    List<Item> filterItem = new ArrayList<>();

                    for (Item item: items) {
                        if (item.getValue().toLowerCase().contains(text.toLowerCase())) {
                            filterItem.add(item);
                        }
                    }

                    filter.values = filterItem;
                }

                return filter;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
