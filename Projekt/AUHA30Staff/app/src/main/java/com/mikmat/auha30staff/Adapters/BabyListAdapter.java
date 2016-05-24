package com.mikmat.auha30staff.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mikmat.auha30staff.Models.Baby;
import com.mikmat.auha30staff.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by matry on 15-05-2016.
 */
public class BabyListAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Baby> babyList;
    ArrayList<Baby> filterList;
    Baby baby;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ItemFilter itemFilter = new ItemFilter();

    public BabyListAdapter(Context context, ArrayList<Baby> babyList) {
        this.context = context;
        this.babyList = babyList;
        this.filterList = babyList;
    }

    @Override
    public int getCount() {
        if (babyList != null) {
            return babyList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (babyList != null) {
            return babyList.get(position);
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.baby_item, null);
        }
        baby = babyList.get(position);
        if (baby != null) {
            TextView name = (TextView) convertView.findViewById(R.id.babyName);
            name.setText(baby.getName());

            TextView age = (TextView) convertView.findViewById(R.id.babyBirth);
            age.setText(String.valueOf(dateFormat.format(baby.getBirthday())));

            TextView gender = (TextView) convertView.findViewById(R.id.babyGender);
            gender.setText(baby.getGender());

            TextView caretaker = (TextView) convertView.findViewById(R.id.babyCaretaker);
            caretaker.setText(baby.getCaretaker());

            TextView ID = (TextView) convertView.findViewById(R.id.babyID);
            ID.setText(String.valueOf(baby.getID()));
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }
        return itemFilter;

    }

    private class ItemFilter extends Filter {
        /*
        * Inspired by: http://codetheory.in/android-filters/
        * */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Baby> tmpFilterList = new ArrayList<Baby>();
                for (Baby b : filterList) {
                    if (b.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        tmpFilterList.add(b);
                    } else if (b.getCaretaker() != null && b.getCaretaker().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        tmpFilterList.add(b);
                    } else if (String.valueOf(dateFormat.format(b.getBirthday())).contains(constraint.toString())) {
                        tmpFilterList.add(b);
                    } else if (b.getID().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            tmpFilterList.add(b);
                    }

                }
                results.count = tmpFilterList.size();
                results.values = tmpFilterList;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            babyList = (ArrayList<Baby>) results.values;
            notifyDataSetChanged();
        }
    }
}
