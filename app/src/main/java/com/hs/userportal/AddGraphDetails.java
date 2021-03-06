package com.hs.userportal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.PreferenceChangeEvent;

import ui.BaseActivity;
import utils.AppConstant;
import utils.PreferenceHelper;
import utils.Utils;

/**
 * Created by rishabh on 23/2/17.
 */
public class AddGraphDetails extends BaseActivity {

    private static EditText mDateFromEt, mDateToEt;
    private static int month2, year2, day2, month1, year1, day1;
    private Calendar mCalender;
    private Button mAddButton;
    private boolean mIsValidDate = false;
    private String mFromDate, mToDate, mDurationValue ;
    private int mDurationSpinnerPosition =0 ;
    private Spinner mDurationSpinner;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgraphdetail);
        setupActionBar();
        mActionBar.setTitle("Graph Details");
        mDateFromEt = (EditText) findViewById(R.id.datefrom_edittext);
        mDateToEt = (EditText) findViewById(R.id.dateto_edittext);
        mDurationSpinner = (Spinner) findViewById(R.id.duration_spinner);
        mAddButton = (Button) findViewById(R.id.addbutton);

        mCalender = Calendar.getInstance();
        year2 = mCalender.get(Calendar.YEAR);
        month2 = mCalender.get(Calendar.MONTH);
        day2 = mCalender.get(Calendar.DAY_OF_MONTH);
        year1 = mCalender.get(Calendar.YEAR);
        month1 = mCalender.get(Calendar.MONTH);
        day1 = mCalender.get(Calendar.DAY_OF_MONTH);

        mDateFromEt.setSelected(false);
        String fromDate = mPreferenceHelper.getString(PreferenceHelper.PreferenceKey.FROM_DATE);
        String toDate = mPreferenceHelper.getString(PreferenceHelper.PreferenceKey.TO_DATE);
        if(!TextUtils.isEmpty(fromDate)){
            mDateFromEt.setText(fromDate);
        }
        if(!TextUtils.isEmpty(toDate)){
            mDateToEt.setText(toDate);
        }

        ArrayAdapter durationAdapter = new ArrayAdapter(AddGraphDetails.this, R.layout.spinner_appearence, AppConstant.mDurationModeArray);
        durationAdapter.setDropDownViewResource(R.layout.spinner_appearence);
        mDurationSpinner.setAdapter(durationAdapter);

        int spinnerValue = mAddGraphDetailSharedPreferences.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) { mDurationSpinner.setSelection(spinnerValue);}

        mDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mDurationSpinnerPosition = position ;
                    mDurationValue = AppConstant.mDurationModeArray[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mDateFromEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        mDateFromEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasfocus) {
                if (hasfocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });
        mDateToEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment1();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        mDateToEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasfocus) {
                if (hasfocus) {
                    DialogFragment newFragment = new DatePickerFragment1();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFromDate = mDateFromEt.getText().toString();
                mToDate = mDateToEt.getText().toString();
                mIsValidDate = Utils.isDateValid(mFromDate, mToDate, "dd/MM/yyyy");
                if (!mIsValidDate) {
                    showAlertMessage("Start date must be less than End date.");
                } else {
                    mPreferenceHelper.setString(PreferenceHelper.PreferenceKey.FROM_DATE, mFromDate);
                    mPreferenceHelper.setString(PreferenceHelper.PreferenceKey.TO_DATE, mToDate);
                    SharedPreferences.Editor mEditor = mAddGraphDetailSharedPreferences.edit();
                    mEditor.putInt("userChoiceSpinner",mDurationSpinnerPosition);
                    mEditor.commit();

                    Intent intent = new Intent();
                    intent.putExtra("fromDate", mFromDate);
                    intent.putExtra("toDate", mToDate);
                    intent.putExtra("intervalMode", mDurationValue);
                    setResult(RESULT_OK, intent);
                    finish();//finishing activity
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Duration" , mDurationSpinner.getSelectedItemPosition());
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year1, month1, day1);
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // Do something with the date chosen by the user
            month1 = monthOfYear;
            day1 = dayOfMonth;
            year1 = year;
            int month = monthOfYear + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + dayOfMonth;

            if (month < 10) {

                formattedMonth = "0" + month;
            }
            if (dayOfMonth < 10) {

                formattedDayOfMonth = "0" + dayOfMonth;
            }
            mDateFromEt.setText(formattedDayOfMonth + "/" + formattedMonth + "/" + year);

        }
    }

    public static class DatePickerFragment1 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year2, month2, day2);
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            month2 = monthOfYear;
            day2 = dayOfMonth;
            year2 = year;
            int month = monthOfYear + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + dayOfMonth;

            if (month < 10) {

                formattedMonth = "0" + month;
            }
            if (dayOfMonth < 10) {

                formattedDayOfMonth = "0" + dayOfMonth;
            }
            mDateToEt.setText(formattedDayOfMonth + "/" + formattedMonth + "/" + year);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

