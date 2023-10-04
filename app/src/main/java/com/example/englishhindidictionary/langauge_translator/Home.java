package com.example.englishhindidictionary.langauge_translator;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.example.englishhindidictionary.BuildConfig;
import com.example.englishhindidictionary.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    LinearLayout adsize;
    String imageFilePath;
    File photoFile;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home);

        FirebaseApp.initializeApp(this);


        this.adsize = (LinearLayout) findViewById(R.id.adsize);
        if (isOnline()) {
            this.adsize.setVisibility(View.VISIBLE);

            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else {
            this.adsize.setVisibility(View.GONE);
        }
        findViewById(R.id.imgLangTranslator).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission("1");
                } else {
                    Home.this.goTranslate();
                }
            }
        });
        findViewById(R.id.imgHistory).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission(ExifInterface.GPS_MEASUREMENT_2D);
                } else {
                    Home.this.goHistory();
                }
            }
        });
        findViewById(R.id.imgChatTranslator).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission(ExifInterface.GPS_MEASUREMENT_3D);
                } else {
                    Home.this.goVideoChat();
                }
            }
        });
        findViewById(R.id.imgDictionary).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission("4");
                } else {
                    Home.this.goDictionary();
                }
            }
        });
        findViewById(R.id.dictionary).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission("4");
                } else {
                    Home.this.goDictionary();
                }
            }
        });

        findViewById(R.id.imgCameraTransator).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission("5");
                } else {
                    Home.this.openCameraIntent();
                }
            }
        });
        findViewById(R.id.imgGalleryTransator).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Home.this.askPermission("6");
                } else {
                    Home.this.goRead();
                }
            }
        });

        findViewById(R.id.imgRateUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id="
                                    + appPackageName)));
                }

            }
        });

        findViewById(R.id.imgPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String url = "https://www.google.co.in/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                } catch (Exception e) {

                }

            }
        });

        findViewById(R.id.imgShareApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.SUBJECT", "Now " + getString(R.string.app_name) + " Available on Google Playstore please download it on share it");
                shareIntent.putExtra("android.intent.extra.TEXT", " - https://play.google.com/store/apps/details?id=" + getPackageName() + " \n\n");
                startActivity(Intent.createChooser(shareIntent, "Share Via"));

            }
        });




    }


    public void goTranslate() {
        startActivity(new Intent(this, TextLangaugeActivity.class));
        SplashScreen.showAdmobInterstitial();

    }


    public void goHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
        SplashScreen.showAdmobInterstitial();

    }


    public void goVideoChat() {
        startActivity(new Intent(this, AudioMessageChatActivity.class));
        SplashScreen.showAdmobInterstitial();
    }


    public void goDictionary() {
        startActivity(new Intent(this, OfflineDictionaryActivity.class));
        SplashScreen.showAdmobInterstitial();
    }


    public void goRead() {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 101);
    }

    public void askPermission(final String str) {
        Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO").withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                    return;
                }
                if (str.equals("1")) {
                    Home.this.goTranslate();
                } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    Home.this.goHistory();
                } else if (str.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
                    Home.this.goVideoChat();
                } else if (str.equals("4")) {
                    Home.this.goDictionary();
                } else if (str.equals("5")) {
                    Home.this.openCameraIntent();
                } else if (str.equals("6")) {
                    Home.this.goRead();
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    public void openCameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                this.photoFile = createImageFile();
            } catch (IOException unused) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            File file = this.photoFile;
            if (file != null) {


                intent.putExtra("output", FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file));
                startActivityForResult(intent, 100);
            }
        }
    }

    private File createImageFile() throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File createTempFile = File.createTempFile("IMG_" + format + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.imageFilePath = createTempFile.getAbsolutePath();
        return createTempFile;
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                try {
                    runTextRec(MediaStore.Images.Media.getBitmap(getContentResolver(), activityResult.getUri()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (i2 == 204) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == 100) {
            Intent intent2 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            File file = new File(this.imageFilePath);
            Uri fromFile = Uri.fromFile(file);
            intent2.setData(fromFile);
            sendBroadcast(intent2);
            if (file.length() > 0) {
                CropImage.activity(fromFile).start(this);
            }
        } else if (i == 101) {
            try {
                String[] strArr = {"_data"};
                Cursor query = getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
                query.moveToFirst();
                String string = query.getString(query.getColumnIndex(strArr[0]));
                query.close();
                File file2 = new File(string);
                Uri fromFile2 = Uri.fromFile(file2);
                if (file2.length() > 0) {
                    CropImage.activity(fromFile2).start(this);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static Bitmap adjustImageOrientation(File file, Bitmap bitmap) {
        int i;
        try {
            int i2 = 0;
            int attributeInt = new android.media.ExifInterface(file.getAbsolutePath()).getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            if (attributeInt == 3) {
                i = 180;
            } else if (attributeInt == 6) {
                i = 90;
            } else if (attributeInt != 8) {
                if (attributeInt == 1) {
                    i2 = 1;
                }
                i = i2;
            } else {
                i = 270;
            }
            Matrix matrix = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (i > 1) {
                matrix.preRotate((float) i);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            }
            return bitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException unused) {
            return null;
        }
    }

    private void runTextRec(Bitmap bitmap) {
        FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                if (firebaseVisionText.getText().length() <= 0) {
                    Toast.makeText(Home.this.getApplicationContext(), "No text Found", 0).show();
                    return;
                }
                Intent intent = new Intent(Home.this, TextLangaugeActivity.class);
                intent.putExtra("text", firebaseVisionText.getText());
                Home.this.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception exc) {
                Log.e("====", "onFailure: " + exc.getMessage());
                Toast.makeText(Home.this.getApplicationContext(), "Unable to fetch text from image", 0).show();
            }
        });
    }

    public void onBackPressed() {
        openExitAppDialog();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void openExitAppDialog() {
        final Dialog dialog = new Dialog(this, R.style.ExitDialogStyle);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_app_exit);
        dialog.setCanceledOnTouchOutside(true);
        dialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                Home.this.finish();
//                SplashActivity.showAdmobInterstitial();
            }
        });
        dialog.show();
    }
}
