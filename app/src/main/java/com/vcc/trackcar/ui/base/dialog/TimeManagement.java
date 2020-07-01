package com.vcc.trackcar.ui.base.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

/**
 * Created by bacnd4 on 9/23/2019.
 * Company VTCT
 */
public interface TimeManagement {
    TimeManagement dialogDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener);

    TimeManagement dialogDatePickerOnlyMonthAndYear(DatePickerDialog.OnDateSetListener onDateSetListener);

    void showDatePickerDialog();

    TimeManagement dialogTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener);

    void showTimePickerDialog();

    TimeManagement dialogTimeAndDatePicker(SingleDateAndTimePickerDialog.Listener listener);

    void showTimeAndDatePickerDialog();
}
