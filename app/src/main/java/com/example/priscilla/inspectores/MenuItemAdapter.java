package com.example.priscilla.inspectores;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemAdapter extends ArrayAdapter<Menu> {

    Context context;
    int layoutResourceId;
    Menu menu []=null;

    public MenuItemAdapter(Context context,int layoutResourceId, Menu[] menu){
        super(context, layoutResourceId, menu);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.menu = menu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        MenuItemHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new MenuItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.textItem = (TextView)row.findViewById(R.id.textItem);
            row.setTag(holder);
        }else{
            holder = (MenuItemHolder)row.getTag();
        }

        Menu menu = this.menu[position];
        holder.textItem.setText(menu.titulo);
        holder.imgIcon.setImageResource(menu.icono);

        return row;
    }

    static class MenuItemHolder
    {
        ImageView imgIcon;
        TextView textItem;
    }

}
