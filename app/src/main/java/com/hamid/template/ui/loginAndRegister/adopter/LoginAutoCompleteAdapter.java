package com.hamid.template.ui.loginAndRegister.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.hamid.template.R;
import com.hamid.template.ui.loginAndRegister.model.Savepassowrd;
import com.hamid.template.utils.TextViewRegular;

import java.util.ArrayList;
import java.util.List;


public class LoginAutoCompleteAdapter extends ArrayAdapter<Savepassowrd.Passwords> implements Filterable {
    Context context;
    int resource, textViewResourceId;
    List<Savepassowrd.Passwords> items, tempItems, suggestions;

    public LoginAutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<Savepassowrd.Passwords> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Savepassowrd.Passwords>(items); // this makes the difference.
        suggestions = new ArrayList<Savepassowrd.Passwords>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(R.layout.row_autocomplete, parent, false);
            }
        }
        Savepassowrd.Passwords people = items.get(position);
        if (people != null) {
            TextViewRegular lblName =  view.findViewById(R.id.tv_key);
            if (lblName != null)
                lblName.setText(people.getEmail());


        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Savepassowrd.Passwords) resultValue).getEmail();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Savepassowrd.Passwords people : tempItems) {
                    if (people.getEmail().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Savepassowrd.Passwords> filterList = (ArrayList<Savepassowrd.Passwords>) results.values;
            if (results != null && results.count > 0) {
                clear();
                if (filterList.size()>0){
                    for (Savepassowrd.Passwords people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }

            }
        }
    };
}
