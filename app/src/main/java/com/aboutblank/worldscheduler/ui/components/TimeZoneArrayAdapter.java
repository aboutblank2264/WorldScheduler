package com.aboutblank.worldscheduler.ui.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.time.TimeZone;
import com.aboutblank.worldscheduler.ui.TimeZoneUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeZoneArrayAdapter extends ArrayAdapter<TimeZone> {
    private List<TimeZone> items;

    public TimeZoneArrayAdapter(@NonNull final Context context, final int resource, final int textViewResourceId, @NonNull final List<TimeZone> objects) {
        super(context, resource, textViewResourceId, new ArrayList<TimeZone>());
        items = new ArrayList<>(objects.size());
        items.addAll(objects);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clock_picker_list_item, null);
        }
        TextView text = convertView.findViewById(R.id.picker_text);
        text.setText(TimeZoneUtils.format(getItem(position).toString()));

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {

        @Override
        public CharSequence convertResultToString(final Object resultValue) {
            return TimeZoneUtils.format(resultValue.toString());
        }

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                List<TimeZone> suggestions = new ArrayList<>();

                for (TimeZone s : items) {
                    if (contains(s, constraint.toString())) {
                        suggestions.add(s);
                    }
                }
                results.values = suggestions;
                results.count = suggestions.size();
            }
            return results;
        }

        private boolean contains(TimeZone parent, String constraint) {
            return TimeZoneUtils.format(parent.getCity()).toLowerCase().startsWith(constraint) ||
                    parent.getContinent().toLowerCase().startsWith(constraint);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                addAll((List<TimeZone>) results.values);
            }
        }
    };
}
