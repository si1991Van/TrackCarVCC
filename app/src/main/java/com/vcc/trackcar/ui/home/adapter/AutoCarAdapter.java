package com.vcc.trackcar.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcc.trackcar.R;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;
import com.vcc.trackcar.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class AutoCarAdapter extends ArrayAdapter<LstBookCarDto> {
    private List<LstBookCarDto> mList;
    private List<LstBookCarDto> dataListAllItems;
    private int resource;
    private LayoutInflater layoutInflater;
    private OnItemCarClick onItemCarClick;

    public AutoCarAdapter(Context context, int resource, List<LstBookCarDto> listCar, OnItemCarClick itemCarClick) {
        super(context, resource, listCar);
        this.resource = resource;
        this.mList = listCar;
        this.onItemCarClick = itemCarClick;
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
        tv_bien_so.setText(mList.get(position).getLicenseCar());
//        TextView tv_driver_name = view.findViewById(R.id.tv_driver_name);
//        tv_driver_name.setText(mList.get(position).getDriverName());
        LinearLayout layout_item_car = view.findViewById(R.id.layout_item_car);
        layout_item_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemCarClick.onClickItemCarSearch(mList.get(position));
            }
        });


        return view;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((LstBookCarDto) resultValue).getLicenseCar();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (AutoCarAdapter.class) {
                    dataListAllItems = new ArrayList<LstBookCarDto>(mList);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (AutoCarAdapter.class) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = StringUtil.removeAccentStr(constraint.toString().toLowerCase().trim());

                ArrayList<LstBookCarDto> matchValues = new ArrayList<LstBookCarDto>();

                for (LstBookCarDto dataItem : dataListAllItems) {
                    if (StringUtil.removeAccentStr(dataItem.getLicenseCar().toLowerCase()).contains(searchStrLowerCase)) {
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
                mList = (ArrayList<LstBookCarDto>) results.values;
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

    public interface OnItemCarClick {
        void onClickItemCarSearch(LstBookCarDto carDto);
    }

    public void setList(List<LstBookCarDto> list) {
        mList = list;
        dataListAllItems = list;
        notifyDataSetChanged();
    }
}
