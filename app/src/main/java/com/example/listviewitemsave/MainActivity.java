package com.example.listviewitemsave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // listView কে পরিচয় করিয়ে দেওয়া হয়েছে ।
        listView = findViewById(R.id.listView);

        // list এর method কে call করা হয়েছে ।
        getListItem();

        // Adapter এক পরিচয় করিয়ে দেওয়া এবং ListView এর মধ্যে set করে দেওয়া হয়েছে ।
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

    } // onCreate method end here ====================

    private void getListItem() {
        hashMap = new HashMap<>();
        hashMap.put("title", "This is title 1");
        hashMap.put("price", "100tk");
        hashMap.put("description", "This is description 1");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("title", "This is title 2");
        hashMap.put("price", "200tk");
        hashMap.put("description", "This is description 2");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("title", "This is title 3");
        hashMap.put("price", "300tk");
        hashMap.put("description", "This is description 3");
        arrayList.add(hashMap);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.list_item, parent, false);

            // পরিচয় করিয়ে দেওয়া হয়েছে ।
            TextView tvTitle = myView.findViewById(R.id.tvTitle);
            TextView tvPrice = myView.findViewById(R.id.tvPrice);
            TextView tvDescription = myView.findViewById(R.id.tvDescription);
            LinearLayout mLayout = myView.findViewById(R.id.mLayout);
            Button btnDownload = myView.findViewById(R.id.btnDownload);

            // list থেকে data get করা হয়েছে ।
            HashMap<String, String> myHashMap = arrayList.get(position);
            String title = myHashMap.get("title");
            String price = myHashMap.get("price");
            String description = myHashMap.get("description");

            // list item এর মধ্যে data set করে দেওয়া হয়েছে ।
            tvTitle.setText(title);
            tvPrice.setText(price);
            tvDescription.setText(description);

            // item এর onClick লেখা হয়েছে ।
            btnDownload.setOnClickListener(v -> {

                // LinearLayout কে bitmap করা হয়েছে ।
                mLayout.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(mLayout.getDrawingCache());
                mLayout.setDrawingCacheEnabled(false);

                // যদি storage permission Enable থাকে তাহলে download করা হয়েছে ।
                if (isStoragePermissionGranted()){
                    saveImage(bitmap);
                } else {
                    isStoragePermissionGranted();
                }
            });

            return myView;
        }

    } // Adapter End Here ===========

    private boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            int READ_MEDIA_IMAGES = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (READ_MEDIA_IMAGES != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.READ_MEDIA_IMAGES);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }

        return true;

    } // isStoragePermissionGranted end here ==================

    private void saveImage(Bitmap finalBitmap) {
        String folderName = "YT Thumbnail";
        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();

        File myDir = new File(root + "/" + folderName);
        myDir.mkdirs();

        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), "Download Done!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.d("storageException", e.toString());
            e.printStackTrace();
        }
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    } // saveImage end here ============


} // public class end here ============================