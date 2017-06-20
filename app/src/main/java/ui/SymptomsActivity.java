package ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applozic.audiovideo.activity.AudioCallActivityV2;
import com.applozic.audiovideo.activity.VideoActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.hs.userportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import adapters.SymptomsAdapter;
import config.StaticHolder;
import models.Symptoms;
import networkmngr.NetworkChangeListener;
import utils.AppConstant;
import utils.PreferenceHelper;

/**
 * Created by ayaz on 6/6/17.
 */

public class SymptomsActivity extends BaseActivity {
    private Activity mActivity;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int MY_PERMISSIONS_REQUEST_READ_FILE = 5;
    private int PICK_FROM_GALLERY = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private String mCoversationType;
    private Uri selectedImageUri;
    private String selectedPath;
    private SymptomsDialog mSymptomsDialog;
    private TextView mSymptomsTextView;
    /* private String symptomsArry[] = {"Pain", "Anxiety", "Fatigue", "Headache", "Infection", "Depression", "Diabtees mellitus", "Shortnes of breath",
             "Skin Rash", "Swelling", "Stress", "Fever", "Weight Loss", "Common Cold", "Diarrhea", "Allergy", "Vomiting", "Dizziness", "Abdominal Pain", "Itch",
             "Joint pain", "Constipation", "Chest pain", "Weight Gain", "Muscle Pain", "Bleeding", "Asthma", "Sore Throat", "HyperTension", "Hair Loss",
             "Migraine", "Blood Pressure", "Blindness"};*/
    private List<Symptoms> mSymptomsList = new ArrayList<>();
    private String symptomsList = "";
    private EditText mNoteEditText;
    private static RequestQueue mRequestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        mRequestQueue = Volley.newRequestQueue(this);

        // Arrays.sort(symptomsArry);
        /*for (int i = 0; i < symptomsArry.length; i++) {
            Symptoms symptoms = new Symptoms();
            symptoms.setName(symptomsArry[i]);
            mSymptomsList.add(symptoms);
        }*/

        setupActionBar();
        mActionBar.hide();

        ImageView backImage = (ImageView) findViewById(R.id.back_image);
        TextView headerTitleTv = (TextView) findViewById(R.id.header_title_tv);
        headerTitleTv.setText("Symptoms");
        backImage.setOnClickListener(mOnClickListener);

        mActivity = this;
        permissionStatus = mActivity.getSharedPreferences("permissionStatus", MODE_PRIVATE);
        mCoversationType = getIntent().getStringExtra("chatType");
        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(mOnClickListener);
        mNoteEditText = (EditText) findViewById(R.id.enter_notes_et);

        Button attatchButton = (Button) findViewById(R.id.attach_button);
        attatchButton.setOnClickListener(mOnClickListener);


        mSymptomsTextView = (TextView) findViewById(R.id.symptoms_tv);
        mSymptomsTextView.setOnClickListener(mOnClickListener);
        mSymptomsTextView.setText("Please choose symptoms.");

       /* String list = "";
        for (Symptoms symptoms : mSymptomsList) {
            if (symptoms.isChecked()) {
                list += symptoms.getName() + " ,";
            }
        }
        if (list.length() > 0) {
            list = list.substring(0, list.length() - 1);
        }
        if (TextUtils.isEmpty(list)) {
            mSymptomsTextView.setText("Please choose symptoms.");
        } else {
            mSymptomsTextView.setText(list);
        }*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (NetworkChangeListener.getNetworkStatus().isConnected()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
            getAllSymptoms();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.continue_button) {
                addPatientSymptoms();
                if (mCoversationType.equalsIgnoreCase("audio")) {
                    Intent audioCallIntent = new Intent(SymptomsActivity.this, AudioCallActivityV2.class);
                    audioCallIntent.putExtra("CONTACT_ID", "be2ce808-6250-4874-a239-31d60d1d8567");
                    startActivity(audioCallIntent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else if (mCoversationType.equalsIgnoreCase("video")) {
                    Intent videoCallIntent = new Intent(SymptomsActivity.this, VideoActivity.class);
                    videoCallIntent.putExtra("CONTACT_ID", "be2ce808-6250-4874-a239-31d60d1d8567");
                    videoCallIntent.putExtra("symptoms", symptomsList);
                    videoCallIntent.putExtra("notes", mNoteEditText.getEditableText().toString());
                    startActivity(videoCallIntent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else if (mCoversationType.equalsIgnoreCase("chat")) {
                    Intent intent = new Intent(SymptomsActivity.this, ConversationActivity.class);
                    intent.putExtra(ConversationUIService.USER_ID, "be2ce808-6250-4874-a239-31d60d1d8567");
                    intent.putExtra(ConversationUIService.DISPLAY_NAME, "shalini"); //put it for displaying the title.
                    intent.putExtra(ConversationUIService.TAKE_ORDER, true); //Skip chat list for showing on back press
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
                /*//Intent intent = new Intent(SymptomsActivity.this, DoctorPrescriptionActivity.class);
                Intent intent = null;
                if (AppConstant.isPatient) {
                    intent = new Intent(SymptomsActivity.this, PastVisitActivity.class);
                } else {
                    intent = new Intent(SymptomsActivity.this, DoctorPrescriptionActivity.class);
                }
                intent.putExtra("chatType", mCoversationType);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);*/
            } else if (id == R.id.attach_button) {
                if (!NetworkChangeListener.getNetworkStatus().isConnected()) {
                    Toast.makeText(mActivity, "No internet connection. Please retry", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            } else if (id == R.id.symptoms_tv) {
                mSymptomsDialog = new SymptomsDialog(SymptomsActivity.this);
                mSymptomsDialog.show();
            } else if (R.id.back_image == id) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

        }
    };

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void uploadFile() {
        final Dialog dialog = new Dialog(SymptomsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.uploadfile_alertbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView item1 = (TextView) dialog.findViewById(R.id.item1_tv);
        item1.setVisibility(View.GONE);
        TextView item2 = (TextView) dialog.findViewById(R.id.item2_tv);

        title.setText("Alert");
        item2.setText("Upload files");


        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                askRunTimePermissions();
            }
        });

        dialog.show();
    }

    private void askRunTimePermissions() {

        if (ActivityCompat.checkSelfPermission(mActivity, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mActivity, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionsRequired[1])) {
                //Show Information about why you need the permission
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
                builder.setTitle("Need multiple permissions");
                builder.setMessage("This app needs camera and storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(mActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
                builder.setTitle("Need multiple permissions");
                builder.setMessage("This app needs camera and storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(mActivity.getBaseContext(), "Go to permissions to grant  camera and storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(mActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionsRequired[1])) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
                builder.setTitle("Need multiple permissions");
                builder.setMessage("This app needs camera and storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(mActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(mActivity.getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_PERMISSIONS_REQUEST_READ_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }


    private void proceedAfterPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SymptomsActivity.this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Take from gallery", "Take from Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        switch (which) {
                            case 0:
                                openGallery(10);
                                break;
                            case 1:

                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 100);
                                break;

                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }


    private void openGallery(int req_code) {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data.getData() != null) {
                selectedImageUri = data.getData();
            } else {
                Log.d("selectedPath1 : ", "Came here its null !");
                Toast.makeText(getApplicationContext(), "failed to get Image!", Toast.LENGTH_LONG).show();
            }

            if (requestCode == 100 && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                selectedPath = getPath(selectedImageUri);
                //preview.setImageURI(selectedImageUri);
                Log.d("selectedPath1 : ", selectedPath);

            }

            if (requestCode == 10)

            {

                selectedPath = getPath(selectedImageUri);
                //preview.setImageURI(selectedImageUri);
                Log.d("selectedPath1 : ", selectedPath);

            }

        }

    }


    private String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    private class SymptomsDialog extends Dialog implements View.OnClickListener {

        private ListView listView;
        private EditText filterText = null;
        private SymptomsAdapter symptomsAdapter = null;
        private Button mOkButton;

        public SymptomsDialog(Context context) {
            super(context);

            /** Design the dialog in main.xml file */
            setContentView(R.layout.symptoms_alert_dialog);
            this.setTitle("Select symptoms");
            filterText = (EditText) findViewById(R.id.symptoms_search);
            mOkButton = (Button) findViewById(R.id.ok_button);
            filterText.addTextChangedListener(filterTextWatcher);
            listView = (ListView) findViewById(R.id.symptoms_list);
            symptomsAdapter = new SymptomsAdapter(SymptomsActivity.this, mSymptomsList);
            listView.setAdapter(symptomsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                    Symptoms symptoms = symptomsAdapter.getItem(position);
                    symptoms.toggleChecked();
                    SymptomsAdapter.SymptomsViewHolder viewHolder = (SymptomsAdapter.SymptomsViewHolder) item.getTag();
                    viewHolder.getCheckBox().setChecked(symptoms.isChecked());
                }
            });

            mOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    symptomsList = "";
                    for (Symptoms symptoms : mSymptomsList) {
                        if (symptoms.isChecked()) {
                            symptomsList += symptoms.getName() + ", ";
                        }
                    }
                    mSymptomsDialog.dismiss();
                    if (symptomsList.length() > 0) {
                        symptomsList = symptomsList.substring(0, symptomsList.length() - 2);
                    }
                    mSymptomsTextView.setText(symptomsList);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        private TextWatcher filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Symptoms> symptomsFilteredList = new ArrayList<Symptoms>();
                if (!TextUtils.isEmpty(s)) {
                    for (Symptoms symptomName : mSymptomsList) {
                        if (symptomName.getName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            symptomsFilteredList.add(symptomName);
                        }
                    }
                } else {
                    symptomsFilteredList = mSymptomsList;
                }
                symptomsAdapter.setData(symptomsFilteredList);
                symptomsAdapter.notifyDataSetChanged();
            }
        };

        @Override
        public void onStop() {
            filterText.removeTextChangedListener(filterTextWatcher);
        }
    }

    private ProgressDialog mProgressDialog;

    private void getAllSymptoms() {
        StaticHolder static_holder = new StaticHolder(this, StaticHolder.Services_static.GetAllSymptoms);
        String url = static_holder.request_Url();
        JSONObject data = new JSONObject();
        JsonObjectRequest symptomsJsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data = response.getString("d");
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("Table");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.optString("SymptomName");
                        Symptoms symptoms = new Symptoms();
                        symptoms.setName(name);
                        mSymptomsList.add(symptoms);
                    }
                    mProgressDialog.dismiss();
                } catch (JSONException je) {
                    mProgressDialog.dismiss();
                    je.printStackTrace();
                    onBackPressed();
                    Toast.makeText(getBaseContext(), "Some error occurred.Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onBackPressed();
                mProgressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Some error occurred.Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(symptomsJsonObjectRequest);
    }

    private void addPatientSymptoms() {
        StaticHolder static_holder = new StaticHolder(this, StaticHolder.Services_static.AddPatientSymptoms);
        String url = static_holder.request_Url();
        JSONObject data = new JSONObject();
        try {
            data.put("patientId", mPreferenceHelper.getString(PreferenceHelper.PreferenceKey.USER_ID));
            data.put("symptoms", mSymptomsTextView.getText());
            data.put("patientNotes", mNoteEditText.getEditableText().toString());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        JsonObjectRequest symptomsJsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("GetMember", "Received Data: " + response);
                    String data = response.getString("d");
                    JSONObject jsonObject = new JSONObject(data);
                    String consultId = jsonObject.optString("d");
                    mProgressDialog.dismiss();
                } catch (JSONException je) {
                    mProgressDialog.dismiss();
                    je.printStackTrace();
                    onBackPressed();
                    Toast.makeText(getBaseContext(), "Some error occurred.Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onBackPressed();
                mProgressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Some error occurred.Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(symptomsJsonObjectRequest);
    }

}
