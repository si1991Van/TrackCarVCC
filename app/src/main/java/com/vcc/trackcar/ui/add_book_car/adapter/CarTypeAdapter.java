package com.vcc.trackcar.ui.add_book_car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vcc.trackcar.model.getListTypeCar.LstBookCarDto;

import java.util.ArrayList;
import java.util.List;

public class CarTypeAdapter extends ArrayAdapter {
    private Context mContext;
    private List<LstBookCarDto> lstBookCarDtos = new ArrayList<>();

    public CarTypeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LstBookCarDto> list) {
        super(context, resource, list);
        mContext = context;
        lstBookCarDtos = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);

        LstBookCarDto currentLstBookCarDto = lstBookCarDtos.get(position);

//        TextView release = (TextView) listItem.findViewById(R.id.text1);
//        release.setText(currentMovie.getmRelease());

        return listItem;
    }


}
