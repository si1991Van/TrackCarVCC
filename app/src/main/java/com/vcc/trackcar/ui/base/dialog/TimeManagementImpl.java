package com.vcc.trackcar.ui.base.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by bacnd4 on 9/23/2019.
 * Company VTCT
 */
public class TimeManagementImpl implements TimeManagement {
    private static final String DATE_PICKER = "mDatePicker";
    private static final String DAY_FIELD = "day";
    private static final String ID = "id";
    private static final String ANDROID = "android";
    private static final String TAG = TimeManagementImpl.class.getName();
    private Context mContext;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private SingleDateAndTimePickerDialog.Builder mSingleDateAndTimePickerDialog;
    private Calendar mCalendar;

    public TimeManagementImpl(Context context) {
        mContext = context;
        mCalendar = Calendar.getInstance();
    }

    @Override
    public TimeManagement dialogDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener) {
        mDatePickerDialog =
                new DatePickerDialog(mContext, onDateSetListener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    @Override
    public TimeManagement dialogDatePickerOnlyMonthAndYear(DatePickerDialog.OnDateSetListener onDateSetListener) {
        mDatePickerDialog =
                new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        try {
            Field[] datePickerDialogFields = mDatePickerDialog.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker =
                            (DatePicker) datePickerDialogField.get(mDatePickerDialog);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int daySpinnerId =
                                Resources.getSystem().getIdentifier("day", "id", "android");
                        if (daySpinnerId != 0) {
                            View daySpinner = datePicker.findViewById(daySpinnerId);
                            if (daySpinner != null) {
                                //Ẩn cột date, chỉ còn lại month và year
                                daySpinner.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException: ", e);
        }
        return this;
    }

    @Override
    public void showDatePickerDialog() {
        if (mDatePickerDialog == null) {
            return;
        }
        mDatePickerDialog.show();
    }

    @Override
    public TimeManagement dialogTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        mTimePickerDialog = new TimePickerDialog(mContext, onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        return this;
    }

    @Override
    public void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            return;
        }
        mTimePickerDialog.show();
    }

    @Override
    public TimeManagement dialogTimeAndDatePicker(SingleDateAndTimePickerDialog.Listener listener) {
        mSingleDateAndTimePickerDialog = new SingleDateAndTimePickerDialog.Builder(mContext)
                .title("Chọn lịch hẹn !")
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(true)
                .displayMonth(true)
                .displayYears(true)
                .mustBeOnFuture()
                .minDateRange(mCalendar.getTime())
                .defaultDate(mCalendar.getTime())
                .listener(listener);
        return this;
    }

    @Override
    public void showTimeAndDatePickerDialog() {
        if (mSingleDateAndTimePickerDialog == null) {
            return;
        }
        mSingleDateAndTimePickerDialog.display();
    }
}
