package com.aboutblank.worldscheduler.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.aboutblank.worldscheduler.backend.time.TimeZone;

import java.util.ArrayList;
import java.util.List;

public class TimeZoneArrayAdapter extends ArrayAdapter<TimeZone> {
    private List<TimeZone> items;

    public TimeZoneArrayAdapter(@NonNull final Context context, final int resource, final int textViewResourceId, @NonNull final List<TimeZone> objects) {
        super(context, resource, textViewResourceId, new ArrayList<TimeZone>());
        items = new ArrayList<>(objects.size());
        items.addAll(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {

        @Override
        public CharSequence convertResultToString(final Object resultValue) {
            return resultValue.toString().replace("_", " ");
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
            return parent.getCity().toLowerCase().startsWith(constraint) ||
                    parent.getContinent().toLowerCase().startsWith(constraint);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                addAll((List<TimeZone>) results.values);
            } else {
                addAll(items);
            }
//            notifyDataSetChanged();
        }
    };
}
