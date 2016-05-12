package com.mat.firebasetestapp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mat.firebasetestapp.R;
import com.mat.firebasetestapp.models.User;

import java.util.ArrayList;

/**
 * Created by matry on 11-05-2016.
 */
public class userListAdaptor extends BaseAdapter{

    Context context;
    ArrayList<User> userList;
    User userInfo;

    public userListAdaptor(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        if (userList != null) {
            return userList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (userList != null) {
            return userList.get(position);
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
            convertView = inflator.inflate(R.layout.user_item, null);
        }

        userInfo = userList.get(position);
        if (userInfo != null) {
            TextView name = (TextView) convertView.findViewById(R.id.UserName);
            name.setText(userInfo.getName());

            TextView age = (TextView) convertView.findViewById(R.id.UserAge);
            age.setText(String.valueOf(userInfo.getAge()));
        }

        return convertView;
    }

}
