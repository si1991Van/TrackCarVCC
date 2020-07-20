package com.vcc.trackcar.ui.giam_sat_xe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcc.trackcar.R;
import com.vcc.trackcar.ui.giam_sat_xe.CarStatusDTO;
import com.vcc.trackcar.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CarStatusAdapter extends ArrayAdapter<CarStatusDTO> {
    private List<CarStatusDTO> mList;
    private List<CarStatusDTO> dataListAllItems;
    private int resource;
    private LayoutInflater layoutInflater;
    private OnItemCatVehicle onItemClick;

    public CarStatusAdapter(Context context, int resource, List<CarStatusDTO> listCar, OnItemCatVehicle itemClick) {
        super(context, resource, listCar);
        this.resource = resource;
        this.mList = listCar;
        this.onItemClick = itemClick;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(resource, null);
        }
        TextView tv_bien_so = view.findViewById(R.id.tv_bien_so);
        tv_bien_so.setText(mList.get(position).getName());
        LinearLayout layout_item_car = view.findViewById(R.id.layout_item_car);
        layout_item_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClickItem(mList.get(position));
            }
        });


        return view;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((CarStatusDTO) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (CarStatusAdapter.class) {
                    dataListAllItems = new ArrayList<CarStatusDTO>(mList);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (CarStatusAdapter.class) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = StringUtil.removeAccentStr(constraint.toString().toLowerCase().trim());

                ArrayList<CarStatusDTO> matchValues = new ArrayList<CarStatusDTO>();

                for (CarStatusDTO dataItem : dataListAllItems) {
                    if (StringUtil.removeAccentStr(dataItem.getName().toLowerCase()).contains(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                mList = (ArrayList<CarStatusDTO>) results.values;
            } else {
                mList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public interface OnItemCatVehicle {
        void onClickItem(CarStatusDTO carDto);
    }

    public void setList(List<CarStatusDTO> list) {
        mList = list;
        dataListAllItems = list;
        notifyDataSetChanged();
    }
}
