package com.hs.userportal;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.RepositoryAdapter;
import base.MyFirebaseMessagingService;
import config.StaticHolder;
import fragment.DashboardFragment;
import fragment.RepositoryFragment;
import ui.BaseActivity;
import utils.AppConstant;
import utils.DirectoryUtility;
import utils.PreferenceHelper;
import utils.RepositoryUtils;

/**
 * Created by Rishabh on 15/04/17.
 */

public class GalleryReceivedData extends BaseActivity implements RepositoryAdapter.onDirectoryAction {

    private RecyclerView mRecyclerView;
    private Button mMoveButton ;
    private ImageView mCreateNewFolderImageView ;
    private Directory mDirectory;
    private RepositoryAdapter mRepositoryAdapter;
    private JSONObject sendData, receiveData;
    private RequestQueue queue, queue3;
    private JsonObjectRequest lock_folder;
    private static RequestQueue req;
    private JsonObjectRequest jr2, jr3, jr4;
    private static JsonObjectRequest s3jr;
    private NotificationHandler nHandler;
    private Handler mHandler;
    private PreferenceHelper mPreferenceHelper;
    private static String patientId = null;
    private EditText mSearchEditText;
    private Helper mhelper;
    private ImageView toolbarBackButton;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_received_data);
        setupActionBar();
        mActionBar.hide();
        mPreferenceHelper = PreferenceHelper.getInstance();
        patientId = mPreferenceHelper.getString(PreferenceHelper.PreferenceKey.USER_ID);
        Log.e("Rishabh", "Patient id := " + patientId);
        mhelper = new Helper();
        initObject();
        Intent intentFromGallery = getIntent();
        String action = intentFromGallery.getAction();
        String type = intentFromGallery.getType();

        if (!TextUtils.isEmpty(mPreferenceHelper.getString(PreferenceHelper.PreferenceKey.SESSION_ID))) {

            // Check if user is logged in .

            createLockFolder();

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    handleSendText(intentFromGallery); // Handle text being sent
                } else if (type.startsWith("image/")) {
                    Log.e("Rishabh", "Intent Received of Image type. ");
                    handleSendImage(intentFromGallery); // Handle single image being sent
                }
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleSendMultipleImages(intentFromGallery); // Handle multiple images being sent
                }
            } else {
                Log.e("Rishabh", "Other intent");
                // Handle other intents, such as being started from the home screen

            }

        } else {
            // user not logged into the app. dont allow to upload file segement here .
            showAlertMessage("Login to ScionTra application first. ");
            finish();
        }

        // TODO end of onCreate method
    }

    private void initObject() {
        toolbarBackButton = (ImageView) findViewById(R.id.toolbar_back_button);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.directory_share_listview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMoveButton = (Button) findViewById(R.id.directory_share_move_btn);
        mCreateNewFolderImageView = (ImageView) findViewById(R.id.add_new_folder);

        mMoveButton.setOnClickListener(mOnClickListener);
        mCreateNewFolderImageView.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int viewId = v.getId();

            if(viewId == R.id.directory_share_move_btn){

            }else if(viewId == R.id.add_new_folder) {
                RepositoryUtils.createNewFolder(GalleryReceivedData.this, mRepositoryAdapter.getDirectory(), new RepositoryUtils.onActionComplete() {
                    @Override
                    public void onFolderCreated(Directory directory) {
                        mRepositoryAdapter = new RepositoryAdapter(GalleryReceivedData.this, directory, GalleryReceivedData.this);
                        mRecyclerView.setAdapter(mRepositoryAdapter);
                        setBackButtonPress(directory);
                    }
                });
            }
        }
    };

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            Log.e("Rishabh", "Single imageURI from gallery := " + imageUri.getPath());
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", imageUri);
            bundle.putString("totaluri", "single");
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
            Log.e("Rishabh", "Multiple URIs from Gallery := " + imageUris.size());
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("multipleUri", imageUris);
            bundle.putString("totaluri", "multiple");
        }
    }





    private void createLockFolder() {
        req = Volley.newRequestQueue(this);
        mHandler = new Handler();
        StaticHolder sttc_holdr = new StaticHolder(this, StaticHolder.Services_static.CreateLockFolder);
        String url = sttc_holdr.request_Url();
        JSONObject data = new JSONObject();
        JSONArray array_folders = new JSONArray();
        array_folders.put("Prescription");
        array_folders.put("Insurance");
        array_folders.put("Bills");
        array_folders.put("Reports");
        Log.e("Rishabh", "Patient id := " + patientId);
        try {
            data.put("list", array_folders);
            data.put("patientId", patientId);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        lock_folder = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rishabh", "reposnse  := " + response);
                startCreatingDirectoryStructure();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        req.add(lock_folder);
    }

    public void startCreatingDirectoryStructure() {

        mDirectory = new Directory("Home");
        loadData();
    }

    private void loadData()   {
        sendData = new JSONObject();
        try {
            sendData.put("PatientId", patientId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        nHandler = NotificationHandler.getInstance(this);
        queue = Volley.newRequestQueue(this);
        queue3 = Volley.newRequestQueue(this);
        req = Volley.newRequestQueue(this);
        // mImageLoader = MyVolleySingleton.getInstance(mActivity).getImageLoader();
        StaticHolder sttc_holdr = new StaticHolder(this, StaticHolder.Services_static.GetAllObjectFromS3);
        String url = sttc_holdr.request_Url();
        JSONObject s3data = new JSONObject();
        try {
            s3data.put("Key", "");
            s3data.put("patientId", patientId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        s3jr = new JsonObjectRequest(Request.Method.POST, url, s3data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rishabh", "reposnse  load data  := " + response);
                try {
                    String data = response.optString("d");
                    JSONObject d = new JSONObject(data);

                    JSONArray s3 = d.getJSONArray("S3Objects");

                    for (int i = 0; i < s3.length(); i++) {
                        JSONObject object = s3.getJSONObject(i);
                        //ignoring useless data and thumbs
                        //thumbs are manually set in setKey method of directoryfile
                        if (object.getString("Key").contains("_thumb."))
                            continue;

                        DirectoryFile file = new DirectoryFile();
                        file.setName(DirectoryUtility.getFileName(object.getString("Key")));
                        file.setKey(object.getString("Key"));
                        file.setLastModified(object.getString("LastModified"));
                        file.setSize(object.getDouble("Size"));
                        file.setPath(DirectoryUtility.removeExtra(object.getString("Key")));
                        //this is a recursive method that will keep adding directories until file is set in hierarchy
                        DirectoryUtility.addObject(mDirectory, file, file.getPath());
                    }

//                    mRepositoryAdapter.notifyDataSetChanged();
                    mRepositoryAdapter = new RepositoryAdapter(GalleryReceivedData.this, mDirectory, GalleryReceivedData.this);
                    mRecyclerView.setAdapter(mRepositoryAdapter);
                    setBackButtonPress(mDirectory);

                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        int socketTimeout1 = 50000;
        RetryPolicy policy1 = new DefaultRetryPolicy(socketTimeout1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        s3jr.setRetryPolicy(policy1);
        req.add(s3jr);
    }


    @Override
    public void onDirectoryTouched(Directory directory) {
        mRepositoryAdapter = new RepositoryAdapter(GalleryReceivedData.this, directory, this);
        mRecyclerView.setAdapter(mRepositoryAdapter);
        setBackButtonPress(directory);
    }

    @Override
    public void onImageTouched(DirectoryFile file) {
        Intent i = new Intent(GalleryReceivedData.this, ImageActivity.class);
        i.putExtra("ImagePath", AppConstant.AMAZON_URL + file.getKey());
        startActivity(i);
    }

    void setBackButtonPress(final Directory directory){
        if(directory.getParentDirectory() == null){
            toolbarTitle.setText("Repository");
            toolbarBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            toolbarTitle.setText(directory.getDirectoryName());
            toolbarBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRepositoryAdapter = new RepositoryAdapter(GalleryReceivedData.this, directory.getParentDirectory(), GalleryReceivedData.this);
                    mRecyclerView.setAdapter(mRepositoryAdapter);
                    setBackButtonPress(directory.getParentDirectory());
                }
            });
        }
    }

    // TODO end of Main class
}
