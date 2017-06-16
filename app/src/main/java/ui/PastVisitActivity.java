package ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.applozic.audiovideo.activity.AudioCallActivityV2;
import com.applozic.audiovideo.activity.VideoActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.hs.userportal.R;
import com.hs.userportal.ReportRecords;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by ayaz on 13/6/17.
 */

public class PastVisitActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_visit);
        setupActionBar();
        mActionBar.hide();

        ImageView backImage = (ImageView) findViewById(R.id.back_image);
        TextView headerTitleTv = (TextView) findViewById(R.id.header_title_tv);
        headerTitleTv.setText("Past Visit");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        TextView doctorName = (TextView) findViewById(R.id.doctor_name);
        TextView doctorLocation = (TextView) findViewById(R.id.city);
        TextView doctorMedicineType = (TextView) findViewById(R.id.medicine_type);
        ImageView doctorPic = (ImageView) findViewById(R.id.doctor_image_view);

        doctorName.setText("Sajat");
        doctorLocation.setText("Sector 22, Noida");
        doctorMedicineType.setText("Family Medicine");
        doctorPic.setImageResource(R.drawable.ayaz);

        ImageView showFiles = (ImageView) findViewById(R.id.show_files);
        showFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File fileReport = new File("/storage/emulated/0/Lab Pdf/Mr. Sunil  Raireport.pdf");

                PackageManager packageManager = getPackageManager();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

                if (list.size() > 0 && fileReport.isFile()) {

                    Intent objIntent = new Intent(Intent.ACTION_VIEW);
                    ///////
                    Uri uri = null;
                    //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Method m = null;
                    try {
                        m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    uri = Uri.fromFile(fileReport);
                    /*} else {
                        uri = FileProvider.getUriForFile(ReportRecords.this, getApplicationContext().getPackageName() + ".provider", fileReport);
                    }*/
                    /////
                    objIntent.setDataAndType(uri, "application/pdf");
                    objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(objIntent);//Staring the pdf viewer
                } else if (!fileReport.isFile()) {
                    Log.v("ERROR!!!!", "OOPS2");
                } else if (list.size() <= 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PastVisitActivity.this);
                    dialog.setTitle("PDF Reader not found");
                    dialog.setMessage("A PDF Reader was not found on your device. The Report is saved at " + fileReport.getAbsolutePath());
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }


            }
        });

        String mCoversationType = getIntent().getStringExtra("chatType");
        if(!TextUtils.isEmpty(mCoversationType)){
            Intent intent = null;
            if (mCoversationType.equalsIgnoreCase("audio")) {
                intent = new Intent(PastVisitActivity.this, AudioCallActivityV2.class);
                intent.putExtra("CONTACT_ID", "6fbbd98b-65e5-468e-98ee-741903caeea2");
            } else if (mCoversationType.equalsIgnoreCase("video")) {
                intent = new Intent(PastVisitActivity.this, VideoActivity.class);
                intent.putExtra("CONTACT_ID", "6fbbd98b-65e5-468e-98ee-741903caeea2");
            } else if (mCoversationType.equalsIgnoreCase("chat")) {
                intent = new Intent(PastVisitActivity.this, ConversationActivity.class);
                intent.putExtra("CONTACT_ID", "6fbbd98b-65e5-468e-98ee-741903caeea2");
                intent.putExtra(ConversationUIService.DISPLAY_NAME, "shalini"); //put it for displaying the title.
                intent.putExtra(ConversationUIService.TAKE_ORDER, true); //Skip chat list for showing on back press
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}