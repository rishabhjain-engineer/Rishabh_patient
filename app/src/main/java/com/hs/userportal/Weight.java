package com.hs.userportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import adapters.MyHealthsAdapter;
import config.StaticHolder;

public class Weight extends ActionBarActivity {

    private WebView weight_graphView;
    private ListView weight_listId;
    private Button bsave;
    private String id;
    private TextView wt_heading;
    JSONObject sendData;
    String parenthistory_ID;
    JsonObjectRequest jr;
    RequestQueue queue;
    MiscellaneousTasks misc;
    List<String> chartValues = new ArrayList<String>();
    List<String> chartValues1 = new ArrayList<String>();
    List<String> chartDates = new ArrayList<String>();
    private Services service;
    ProgressDialog progress;
    private MyHealthsAdapter adapter;
    private ArrayList<HashMap<String, String>> weight_contentlists = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle avedInstanceState) {
        super.onCreate(avedInstanceState);
        setContentView(R.layout.weight_layout);
        //this.getActionBar().setTitle("Weight");
        ActionBar action = getSupportActionBar();
        action.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1DBBE3")));
        action.setIcon(new ColorDrawable(Color.parseColor("#1DBBE3")));
        action.setTitle("Weight");
        action.setDisplayHomeAsUpEnabled(true);
        weight_graphView = (WebView) findViewById(R.id.weight_graphView);
        WebSettings settings = weight_graphView.getSettings();
        queue = Volley.newRequestQueue(this);
        settings.setLoadWithOverviewMode(true);
        // settings.setUseWideViewPort(true);

        // view.setInitialScale(140);
        new Authentication(Weight.this, "Weight", "").execute();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        weight_graphView.getSettings().setDisplayZoomControls(false);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        weight_listId = (ListView) findViewById(R.id.weight_listId);
        bsave = (Button) findViewById(R.id.bsave);
        wt_heading = (TextView) findViewById(R.id.wt_heading);
        wt_heading.setText("Weight (Kg)");
        service = new Services(Weight.this);
        misc = new MiscellaneousTasks(Weight.this);
        Intent z = getIntent();
        id = z.getStringExtra("id");

        //new BackgroundProcess().execute();
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
                AlertDialog dialog = new AlertDialog.Builder(Weight.this).create();
                dialog.setTitle("Delete Weight");
                dialog.setMessage("Are you sure you want to delete the Weight?");

                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();

                    }
                });

                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteWeight();
                    }
                });
                dialog.show();
            }

        });
    }

    class BackgroundProcess extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        JSONObject receiveData1;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress = new ProgressDialog(Weight.this);
            progress.setCancelable(false);
            progress.setMessage("Loading...");
            progress.setIndeterminate(true);

            progress.show();


        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            adapter = new MyHealthsAdapter(Weight.this, weight_contentlists);
            weight_listId.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(weight_listId);
            String db = null;


            // chartValues.add("")
            try {
                /*db = "<!DOCTYPE html><html><head><title></title>"
                         + "<link href='kendo.common.min.css' rel='stylesheet'/><link href='kendo.dataviz.metro.min.css' rel='stylesheet'/>"
                         + "<link href='kendo.metro.min.css' rel='stylesheet'/><script src='jquery-1.9.1.js'></script><script src='kendo.all.min.js'>"
                         + "</script></head><body><div id='example' class='k-content'>"
                         + "<div class='chart-wrapper'><div id ='compareChart'></div><br /><br /><table class='history'>"
                         //	+ divDataBullet
                         + "</table></div>"
                         + "<script>function createChart(){"
                         + "hello"
                         + "} function createCompareChart() {"
                         + misc.getJQueryCompare(chartValues,
                         chartValues, chartDates)
                         + "}"
                         + "$(document).ready(function(){setTimeout(function(){ createCompareChart();$('#example').bind('kendo:skinChange', "
                         + "function(e){ createCompareChart();});}, 100);});"
                         + "</script>"
                         + "<style scoped> .history{border-collapse: collapse;width: 100%;}.history td.chart{width: 430px;}.history .k-chart{height: 65px;width: 400px;}"
                         + ".history td.item{line-height: 65px;width: 20px;text-align: right;padding-bottom: 22px;}.chart-wrapper{width: 450px;height: 350px;}</style>"
                         + "</div></body></html>";*/
                db = "<!DOCTYPE html> <html> <head>" +
                        " <title></title>" +
                        " <link rel='stylesheet' href='kendo.common.min.css' />" +
                        " <link rel='stylesheet' href='kendo.default.min.css' />"

                        + "  <script src='jquery.min.js'></script>"
                        + "  <script src='kendo.all.min.js'></script>"
                        + " </head>"
                        + " <body>"
                        + " <div id='example'>"
                        + "  <div class='demo-section k-content wide'>"
                        + " <div id='chart' style='background: center no-repeat url('../content/shared/styles/world-map.png');'></div>"
                        + " </div> \n" +
                        "    <script>\n" +
                        "        function createChart() {\n" +
                        "            $(\"#chart\").kendoChart({\n" +
                        "                title: {\n" +
                        "                    text: \"Weight in kg\"\n" +
                        "                },\n" +
                        "                legend: {\n" +
                        "                    position: \"bottom\"\n" +
                        "                },\n" +
                        "                chartArea: {\n" +
                        "                    background: \"\"\n" +
                        "                },\n" +
                        "                seriesDefaults: {\n" +
                        "                    type: \"line\",\n" +
                        "                    style: \"smooth\"\n" +
                        "                },\n" +
                        "                series: [{\n" +
                        "                    name: \"Weight\",\n" +
                        "                    data: " + chartValues + "\n" +
                        "                }  ],\n" +
                        "                valueAxis: {\n" +
                        "                    labels: {\n" +
                        "                        format: \"{0}\"\n" +
                        "                    },\n" +
                        "                    line: {\n" +
                        "                        visible: false\n" +
                        "                    },\n" +
                        "                    axisCrossingValue: -10\n" +
                        "                },\n" +
                        "                categoryAxis: {\n" +
                        "                    categories: " + chartDates + ",\n" +
                        "                    majorGridLines: {\n" +
                        "                        visible: false\n" +
                        "                    },\n" +
                        "                    labels: {\n" +
                        "                        rotation: \"auto\"\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                tooltip: {\n" +
                        "                    visible: true,\n" +
                        "                    format: \"{0}%\",\n" +
                        "                    template: \"#= series.name #: #= value #\"\n" +
                        "                }\n" +
                        "            });\n" +
                        "        }\n" +
                        "\n" +
                        "        $(document).ready(createChart);\n" +
                        "        $(document).bind(\"kendo:skinChange\", createChart);\n" +
                        "    </script>\n" +
                        "</div>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";
            } catch (Exception e) {
                e.printStackTrace();
            }
            weight_graphView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (progress.isShowing())
                        progress.dismiss();
                }
            });

            if (chartDates.size() > 1) {
                weight_graphView.setVisibility(View.VISIBLE);
                weight_graphView.loadDataWithBaseURL("file:///android_asset/", db, "text/html", "UTF-8", "");
            } else {
                weight_graphView.setVisibility(View.GONE);
                progress.dismiss();
            }
            //  progress.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject sendData1 = new JSONObject();
            try {

                sendData1.put("UserId", id);
                sendData1.put("profileParameter", "health");
                sendData1.put("htype", "weight");
                receiveData1 = service.patienBasicDetails(sendData1);
                String data = receiveData1.getString("d");
                JSONObject cut = new JSONObject(data);
                JSONArray jsonArray = cut.getJSONArray("Table");


                HashMap<String, String> hmap;
                weight_contentlists.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    hmap = new HashMap<String, String>();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String PatientHistoryId = obj.getString("PatientHistoryId");
                    String ID = obj.getString("ID");
                    String weight = obj.getString("weight");
                    String fromdate = obj.getString("fromdate");
                    hmap.put("PatientHistoryId", PatientHistoryId);
                    hmap.put("ID", ID);
                    hmap.put("weight", weight);
                    hmap.put("fromdate", fromdate);
                    weight_contentlists.add(hmap);
                    chartValues.add(weight);
                   // chartDates.add("'" + fromdate + "'");
                    chartDates.add("");

                }
                Collections.reverse(chartValues);

             /* new Helper(). sortHashListByDate(weight_contentlists,"fromdate");
                for(int i=0;i<weight_contentlists.size();i++){
                    chartValues.add(weight_contentlists.get(i).get("weight"));
                    chartDates.add(String.valueOf(i+1));
                }
                Collections.reverse(chartValues);*/
            } catch (JSONException e) {

                e.printStackTrace();
            }


            System.out.println(receiveData1);// TODO Auto-generated method stub

            return null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weightmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;

            case R.id.add:

                Intent i = new Intent(Weight.this, AddWeight.class);
                i.putExtra("id", id);
                i.putExtra("htype", "weight");
                startActivity(i);
                finish();

                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                return true;

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

    private void deleteWeight() {
        progress = new ProgressDialog(Weight.this);
        progress.setMessage("Deleting .....");
        progress.show();
        sendData = new JSONObject();
        try {
            sendData.put("patientHistoryId", parenthistory_ID);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        StaticHolder sttc_holdr = new StaticHolder(Weight.this, StaticHolder.Services_static.deleteSingularDetails);
        String url = sttc_holdr.request_Url();
        jr = new JsonObjectRequest(Request.Method.POST, url, sendData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    if (response.getString("d").equalsIgnoreCase("success")) {
                        progress.dismiss();
                        Toast.makeText(Weight.this, response.getString("d").toString(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(Weight.this, response.getString("d").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
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
        new BackgroundProcess().execute();
    }

}
