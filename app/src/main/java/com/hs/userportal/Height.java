package com.hs.userportal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapters.MyHealthsAdapter;
import config.StaticHolder;
import networkmngr.NetworkChangeListener;
import ui.BaseActivity;
import ui.GraphHandlerActivity;
import utils.AppConstant;
import utils.MyMarkerView;
import utils.PreferenceHelper;
import utils.Utils;

public class Height extends GraphHandlerActivity {

    private WebView weight_graphView;
    private ListView weight_listId;
    private Button bsave;
    private ProgressDialog progress;
    private JSONObject sendData;
    private String mId , mDateFormat =  "%b '%y", mFormDate, mToDate, mIntervalMode;
    private TextView wt_heading;
    private MiscellaneousTasks misc;
    private JsonObjectRequest jr;
    private RequestQueue queue;
    private List<String> chartValues = new ArrayList<String>();
    private List<String> chartValues1 = new ArrayList<String>();
    private List<String> chartDates = new ArrayList<String>();
    private Services service;
    private String parenthistory_ID;
    private MyHealthsAdapter adapter;
    private ArrayList<HashMap<String, String>> weight_contentlists = new ArrayList<HashMap<String, String>>();
    private LineChart linechart     ;
    private int maxYrange = 0 , mRotationAngle = 0;
    private double mMaxHeight = 0.0;
    private long mDateMaxValue, mDateMinValue;
    private boolean mIsToAddMaxMinValue = true;
    private List<Long> mEpocList = new ArrayList<Long>();
    private List<String> mValueList = new ArrayList<String>();
    private long mFormEpocDate = 0, mEpocToDate = 0;
    private double mRangeToInDouble =0 , mRangeFromInDouble = 0 ;
    private JSONArray mJsonArrayToSend,  mTckValuesJsonArray = null;
    private RelativeLayout mListViewHeaderRl;
    private List<String> mDateList = new ArrayList<>();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle avedInstanceState) {
        super.onCreate(avedInstanceState);
        setContentView(R.layout.weight_layout);
        mListViewHeaderRl = (RelativeLayout)findViewById(R.id.header);
        service = new Services(Height.this);
        setupActionBar();
        mActionBar.setTitle("Height");
        queue = Volley.newRequestQueue(this);

        weight_graphView = (WebView) findViewById(R.id.weight_graphView);
        weight_graphView.setFocusable(true);
        weight_graphView.setFocusableInTouchMode(true);
        WebSettings settings = weight_graphView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
        weight_graphView.setInitialScale(1);
        weight_graphView.addJavascriptInterface(new MyJavaScriptInterface(this), "Interface");


        weight_listId = (ListView) findViewById(R.id.weight_listId);
        bsave = (Button) findViewById(R.id.bsave);
        wt_heading = (TextView) findViewById(R.id.wt_heading);
        wt_heading.setText("Height (cm)");
        misc = new MiscellaneousTasks(Height.this);
        Intent z = getIntent();
        mId = z.getStringExtra("id");

        if (!NetworkChangeListener.getNetworkStatus().isConnected()) {
            Toast.makeText(Height.this, "No internet connection. Please retry", Toast.LENGTH_SHORT).show();
        } else {
            new Authentication(Height.this, "Height", "").execute();
        }

        // new BackgroundProcess().execute();
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        weight_listId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parenthistory_ID = weight_contentlists.get(position).get("PatientHistoryId");
                final Dialog dialog = new Dialog(Height.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.unsaved_alert_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                TextView messageTv = (TextView) dialog.findViewById(R.id.message);
                TextView titleTv = (TextView) dialog.findViewById(R.id.title);
                titleTv.setText("Delete Height");
                TextView okBTN = (TextView) dialog.findViewById(R.id.btn_ok);
                TextView stayButton = (TextView) dialog.findViewById(R.id.stay_btn);
                messageTv.setText("Are you sure you want to delete the selected file(s)?");

                stayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                okBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        deleteheight();
                    }
                });
                dialog.show();
            }
        });

        linechart = (LineChart) findViewById(R.id.lineChart);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                linechart.getLayoutParams();
        params.height = Math.round(height / 2);
        linechart.setLayoutParams(params);
      //  MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        // set the marker to the chart
       // linechart.setMarkerView(mv);
        new BackgroundProcess().execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            weight_listId.setVisibility(View.GONE);
            mListViewHeaderRl.setVisibility(View.GONE);
            mActionBar.hide();
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            weight_listId.setVisibility(View.VISIBLE);
            mListViewHeaderRl.setVisibility(View.VISIBLE);
            mActionBar.show();
        }
    }

    public void setLinechart() {
        linechart.setDrawGridBackground(false);
        for (int i = 0; i < chartValues.size(); i++) {
            if (maxYrange < Math.round(Float.parseFloat(chartValues.get(i)))) {
                maxYrange = Math.round(Float.parseFloat(chartValues.get(i)));
            }
        }
        linechart.setDescription("");
        linechart.setNoDataTextDescription("You need to provide data for the chart.");
        // enable touch gestures
        linechart.setTouchEnabled(true);
        // enable scaling and dragging
        linechart.setDragEnabled(true);
        linechart.setScaleEnabled(true);
        linechart.setPinchZoom(true);
        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
        llXAxis.setEnabled(false);
        XAxis xAxis = linechart.getXAxis();
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setXOffset(0f);
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisMinValue(0f);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line
        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setEnabled(false);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(0f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(tf);
        ll2.setEnabled(false);

        YAxis leftAxis = linechart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        /*leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);*/
        leftAxis.setAxisMaxValue(maxYrange + maxYrange / 4);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setYOffset(0f);
        leftAxis.enableGridDashedLine(0f, 0f, 0f);
        leftAxis.setDrawZeroLine(false);
        // leftAxis.setEnabled(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        linechart.getAxisRight().setEnabled(false);

        //linechart.getViewPortHandler().setMaximumScaleY(2f);
        //linechart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(chartValues.size(), 100);

//        linechart.setVisibleXRange(20);
//        linechart.setVisibleYRange(20f, AxisDependency.LEFT);
//        linechart.centerViewTo(20, 50, AxisDependency.LEFT);

        linechart.animateX(2500);
        //linechart.invalidate();
        // get the legend (only possible after setting data)
        Legend l = linechart.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setEnabled(false);
        // don't forget to refresh the
        //drawing
        linechart.invalidate();
    }

    private void setData(int count, float range) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = Float.parseFloat(chartValues.get(i));
            values.add(new Entry(i, val));
        }
        LineDataSet set1;
        if (linechart.getData() != null &&
                linechart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) linechart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            linechart.getData().notifyDataChanged();
            linechart.notifyDataSetChanged();
        } else {
            // create a data set and give it a type
            set1 = new LineDataSet(values, getIntent().getStringExtra("chartNames"));
            set1.disableDashedLine();
            set1.setColor(Color.parseColor("#FF8409"));
            set1.setCircleColor(Color.parseColor("#FF8409"));
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3.5f);
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(7f);
            set1.setDrawFilled(false);
            set1.setDrawValues(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            // set data
            linechart.setData(data);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new BackgroundProcess().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreferenceHelper.setString(PreferenceHelper.PreferenceKey.FROM_DATE,"");
        mPreferenceHelper.setString(PreferenceHelper.PreferenceKey.TO_DATE,"");

        SharedPreferences.Editor mEditor = mAddGraphDetailSharedPreferences.edit();
        mEditor.putInt("userChoiceSpinner", 0);
        mEditor.commit();
    }

    class BackgroundProcess extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        JSONObject receiveData1;
        boolean isDataAvailable = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Height.this);
            progress.setCancelable(false);
            progress.setMessage("Loading...");
            progress.setIndeterminate(true);
            isDataAvailable = false;
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject sendData1 = new JSONObject();
            // SimpleDateFormat simpleDateFormatDash = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat simpleDateFormatDash = new SimpleDateFormat("yyyy-MM-dd"); //Removed hour minute second
            mDateList.clear();
            try {

                sendData1.put("UserId", mId);
                sendData1.put("profileParameter", "health");
                sendData1.put("htype", "height");
                receiveData1 = service.patienBasicDetails(sendData1);
                String data = receiveData1.getString("d");
                JSONObject cut = new JSONObject(data);
                JSONArray jsonArray = cut.getJSONArray("Table");
                HashMap<String, String> hmap;
                weight_contentlists.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    isDataAvailable = true;
                    hmap = new HashMap<String, String>();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String PatientHistoryId = obj.getString("PatientHistoryId");
                    String ID = obj.getString("ID");
                    String height = obj.getString("height");
                    if (!TextUtils.isEmpty(height)) {
                        double heightInDouble = Double.parseDouble(height);
                        if (mMaxHeight <= heightInDouble) {
                            mMaxHeight = heightInDouble;
                        }
                    }
                    String fromdate = obj.getString("fromdate");
                    String dateWithoutHour[] = fromdate.split("T");
                    String onlyDate = dateWithoutHour[0] ;
                    String correctDate = Utils.correctDateFormat(onlyDate);
                    mDateList.add(correctDate);
                    hmap.put("PatientHistoryId", PatientHistoryId);
                    hmap.put("ID", ID);
                    hmap.put("weight", height);
                    hmap.put("fromdate", onlyDate);

                    Date date = null;
                    try {
                        date = simpleDateFormatDash.parse(onlyDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long epoch = date.getTime();

                    if (mFormEpocDate > 0) {
                        if (epoch <= mEpocToDate && epoch >= mFormEpocDate) {
                            weight_contentlists.add(hmap);
                        }
                    } else {
                        weight_contentlists.add(hmap);
                    }


                }
                Helper.sortHealthListByDate(weight_contentlists);

                JSONArray jsonArray1 = new JSONArray();
                for(int i=0; i< weight_contentlists.size() ; i++){

                    Date date = null;
                    HashMap<String, String> mapValue = weight_contentlists.get(i);
                    try {
                        String fromdate = mapValue.get("fromdate");
                        date = simpleDateFormatDash.parse(fromdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long epoch = date.getTime();
                    mEpocList.add(epoch);
                    mValueList.add(mapValue.get("weight"));
                    if(mIsToAddMaxMinValue && i == 0){
                        mDateMinValue = epoch;
                    }
                    if(mIsToAddMaxMinValue && i == (weight_contentlists.size() -1)){
                        mDateMaxValue = epoch;
                    }

                    if (mFormEpocDate > 0) {
                        if (epoch <= mEpocToDate && epoch >= mFormEpocDate) {
                            JSONArray innerJsonArray = new JSONArray();
                            innerJsonArray.put(epoch);
                            innerJsonArray.put(mapValue.get("weight"));
                            jsonArray1.put(innerJsonArray);
                        }
                    } else {
                        JSONArray innerJsonArray = new JSONArray();
                        innerJsonArray.put(epoch);
                        innerJsonArray.put(mapValue.get("weight"));
                        jsonArray1.put(innerJsonArray);
                    }
                }
                JSONObject outerJsonObject = new JSONObject();
                outerJsonObject.put("key", "Height(cm)");
                outerJsonObject.put("values", jsonArray1);
                mJsonArrayToSend = new JSONArray();
                mJsonArrayToSend.put(outerJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(receiveData1);// TODO Auto-generated method stub
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(isDataAvailable){
                setDateList(mDateList);
                if(adapter == null){
                    adapter = new MyHealthsAdapter(Height.this);
                    adapter.setListData(weight_contentlists);
                    weight_listId.setAdapter(adapter);
                }else{
                    adapter.setListData(weight_contentlists);
                    adapter.notifyDataSetChanged();
                }
                Utility.setListViewHeightBasedOnChildren(weight_listId);
                weight_graphView.loadUrl("file:///android_asset/html/index.html");
                if(progress != null && progress.isShowing()){
                    progress.dismiss();
                }
            }else {
                Intent i = new Intent(Height.this, AddWeight.class);
                i.putExtra("id", mId);
                i.putExtra("htype", "height");
                startActivity(i);
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphheader, menu);
        return true;
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;

            case R.id.add:
                Intent i = new Intent(Height.this, AddWeight.class);
                i.putExtra("id", mId);
                i.putExtra("htype", "height");
                startActivity(i);
                //finish();
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;

            case R.id.option:
                Intent addGraphDetailsIntent = new Intent(Height.this, AddGraphDetails.class);
                startActivityForResult(addGraphDetailsIntent, AppConstant.HEIGHT_REQUEST_CODE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();

            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = listView.getPaddingTop()
                    + listView.getPaddingBottom();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                if (listItem instanceof ViewGroup) {
                    listItem.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                }
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight() + 30;
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight()
                    * (listAdapter.getCount() - 1) + 30);
            listView.setLayoutParams(params);
        }
    }

    private void deleteheight() {
        progress = new ProgressDialog(Height.this);
        progress.setMessage("Deleting .....");
        progress.show();
        sendData = new JSONObject();
        try {
            sendData.put("patientHistoryId", parenthistory_ID);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        StaticHolder sttc_holdr = new StaticHolder(Height.this, StaticHolder.Services_static.deleteSingularDetails);
        String url = sttc_holdr.request_Url();
        jr = new JsonObjectRequest(Request.Method.POST, url, sendData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("d").equalsIgnoreCase("success")) {
                        progress.dismiss();
                        Toast.makeText(Height.this, response.getString("d").toString(), Toast.LENGTH_SHORT).show();
                        new BackgroundProcess().execute();
                    } else {
                        Toast.makeText(Height.this, response.getString("d").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while deleting data, Try Later", Toast.LENGTH_SHORT).show();
                progress.dismiss();
                finish();
            }
        }) {

        };
        queue.add(jr);
    }

    public void startBackgroundprocess() {
        //new BackgroundProcess().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.HEIGHT_REQUEST_CODE && resultCode == RESULT_OK) {
            mFormDate = data.getStringExtra("fromDate");
            mToDate = data.getStringExtra("toDate");
            mIsToAddMaxMinValue = false;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null, date2 = null;

            try {
                date1 = simpleDateFormat.parse(mFormDate);
                date2 = simpleDateFormat.parse(mToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mFormEpocDate = date1.getTime();
            mEpocToDate = date2.getTime();
            mIntervalMode = data.getStringExtra("intervalMode");
            mRotationAngle = 90;

            if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[0])) {
                //Daily
                mDateFormat = "%d %b '%y";
                mTckValuesJsonArray = getJsonForDaily(mFormDate, mToDate);
            } else if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[1])) {
                //Weekly
                mTckValuesJsonArray = getJsonForWeekly(mFormDate, mToDate);
                mDateFormat = "%d %b '%y";
            } else if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[2])) {
                //Monthly
                mTckValuesJsonArray = getJsonForMonthly(mFormDate, mToDate);
                mDateFormat = "%b '%y";
            } else if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[3])) {
                //Quarterly
                mTckValuesJsonArray = getJsonForQuaterly(mFormDate, mToDate);
                mDateFormat = "%b '%y";
            } else if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[4])) {
                //Semi-Annually
                mTckValuesJsonArray = getJsonForSemiAnnually(mFormDate, mToDate);
                mDateFormat = "%b '%y";
            } else if (mIntervalMode.equalsIgnoreCase(AppConstant.mDurationModeArray[5])) {
                //Annually
                mTckValuesJsonArray = getJsonForYearly(mFormDate, mToDate);
                mDateFormat = "%Y";
                mRotationAngle = 0;
            }

            for(int i = 0; i< mTckValuesJsonArray.length() ; i++){
                if(i==0){
                    try {
                        Object a = mTckValuesJsonArray.get(0);
                        String stringToConvert = String.valueOf(a);
                        mDateMinValue = Long.parseLong(stringToConvert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(i == (mTckValuesJsonArray.length() -1)){
                    try {
                        int pos = ((mTckValuesJsonArray.length() -1));
                        Object a = mTckValuesJsonArray.get(pos);
                        String stringToConvert = String.valueOf(a);
                        mDateMaxValue = Long.parseLong(stringToConvert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class MyJavaScriptInterface {
        private Context context;

        MyJavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public String passDataToHtml() {
            return mJsonArrayToSend.toString();
        }

        @JavascriptInterface
        public int getMaxData() {
            int i = (int) mMaxHeight;
            return (i + 20);
        }

        @JavascriptInterface
        public int getRotationAngle() {
            return mRotationAngle;
        }

        @JavascriptInterface
        public String getTickValues() {
            if(mTckValuesJsonArray == null){
                return "null";
            }else{
                return mTckValuesJsonArray.toString();
            }
        }

        @JavascriptInterface
        public String getDateFormat() {
            return mDateFormat;
        }

        @JavascriptInterface
        public long minDateValue() {
            Log.e("ayaz", "min: "+mDateMinValue);
            return mDateMinValue;
        }

        @JavascriptInterface
        public int getRangeTo() {
            return (int)mRangeToInDouble;
        }


        @JavascriptInterface
        public int getRangeFrom() {
            return (int)mRangeFromInDouble;
        }

        @JavascriptInterface
        public long maxDateValue() {
            Log.e("ayaz", "max: "+mDateMaxValue);
            return mDateMaxValue;
        }
    }
}
