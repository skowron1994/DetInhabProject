package com.detectinhabitants.detinhab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;



public class FilterHandler extends BaseAdapter implements Filterable {

    Context c;
    ArrayList<HabitantModel> habitantList;
    CustomFilter filter;
    ArrayList<HabitantModel> filteredHabList;

    public FilterHandler(Context context, ArrayList<HabitantModel> list){
        this.c = context;
        this.habitantList = list;
        this.filteredHabList = list;
    }


    @Override
    public int getCount() {
        return habitantList.size();
    }

    @Override
    public Object getItem(int pos) {
        return habitantList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return habitantList.indexOf(getItem(pos));
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parentp) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            inflater.inflate(R.layout.habitants, null);
        }

        TextView tvHabitantItem, tvHabitantItem2, tvHabitantItem3;
        tvHabitantItem = convertView.findViewById(R.id.tvHabitantName);
        tvHabitantItem2 = convertView.findViewById(R.id.tvHabitantSurname);
        tvHabitantItem3 = convertView.findViewById(R.id.tvHabitantRoom);

        tvHabitantItem.setText(habitantList.get(pos).getHabName());
        tvHabitantItem2.setText(habitantList.get(pos).getHabSurname());
        tvHabitantItem3.setText(String.valueOf(habitantList.get(pos).getRoomNumber()));
        return convertView;

    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }

        return filter;
    }
    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){

                constraint = constraint.toString().toUpperCase();
                ArrayList<HabitantModel> filters = new ArrayList<HabitantModel>();
                for(int i = 0;i<filteredHabList.size() ;i++){
                    if(filteredHabList.get(i).getHabName().toUpperCase().contains(constraint) || filteredHabList.get(i).getHabSurname().toUpperCase().contains(constraint)){
                        HabitantModel h = new HabitantModel();
                        h.setHabName(filteredHabList.get(i).getHabName());
                        h.setHabSurname(filteredHabList.get(i).getHabSurname());
                        h.setRoomNumber(filteredHabList.get(i).getRoomNumber());
                        filters.add(h);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }
            else{
                results.count = filteredHabList.size();
                results.values = filteredHabList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            habitantList = (ArrayList<HabitantModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
