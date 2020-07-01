package com.vcc.trackcar.ui.select_address.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.vcc.trackcar.R;
import com.vcc.trackcar.model.getDataProvinceCity.AreaProvinceCity;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdaper extends ArrayAdapter<AreaProvinceCity> {
    private List<AreaProvinceCity> mList;
    private LayoutInflater layoutInflater;
    private int layoutResourceId;
    private List<AreaProvinceCity> dataListAllItems;
    private IClickItem mCallback;
    private int mType;


    public CountriesAdaper(Context mContext, int layoutResourceId, List<AreaProvinceCity> lstProvinceArea, IClickItem callback, int type) {
        super(mContext, layoutResourceId, lstProvinceArea);
        this.layoutResourceId = layoutResourceId;
        this.mList = lstProvinceArea;
        mCallback = callback;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mType = type;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        }
        TextView textView = view.findViewById(R.id.txtNameSpinner);
        textView.setText(mList.get(position).getNameLocation());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClickProvince(mList.get(position), mType);
            }
        });
        return view;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((AreaProvinceCity)resultValue).getNameLocation();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (CountriesAdaper.class) {
                    dataListAllItems = new ArrayList<AreaProvinceCity>(mList);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (CountriesAdaper.class) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = convert(constraint.toString().toLowerCase().trim());

                ArrayList<AreaProvinceCity> matchValues = new ArrayList<AreaProvinceCity>();

                for (AreaProvinceCity dataItem : dataListAllItems) {
                    if (convert(dataItem.getNameLocation().toLowerCase().trim()).contains(searchStrLowerCase)) {
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
                mList = (ArrayList<AreaProvinceCity>)results.values;
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

    public interface IClickItem{
        void onClickProvince(AreaProvinceCity newContract, int type);
    }

    public void setList(List<AreaProvinceCity> list){
        mList = list;
        dataListAllItems = list;
        notifyDataSetChanged();
    }

    public String convert(String str) {
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("đ", "d");

        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replaceAll("Đ", "D");
        return str;
    }



}
