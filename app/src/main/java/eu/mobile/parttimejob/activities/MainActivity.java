package eu.mobile.parttimejob.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.mobile.parttimejob.R;
import eu.mobile.parttimejob.adapters.DrawerListAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DrawerLayout.DrawerListener, DrawerListAdapter.OnClickViewListener{

    private final int       PICK_IMAGE    = 1;
    private final int       CROP_IMAGE    = 2;
    private final String    DIR = Environment.getExternalStorageDirectory() + File.separator + "PartTimeJob" + File.separator;

    private ImageButton                 mDrawerButton;
    private DrawerLayout                mDrawerLayout;
    private RelativeLayout              mContentLayout;
    private RecyclerView                mDrawerRecyclerView;
    private RecyclerView.LayoutManager  mLayoutManager;
    private ImageView                   mProfileImageView;
    private DrawerListAdapter           mDrawerListAdapter;
    private Uri                         mFileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setListeners();

        loadDrawerList();
    }

    private void initUI(){
        mDrawerButton       = (ImageButton)     findViewById(R.id.drawer_button);
        mDrawerLayout       = (DrawerLayout)    findViewById(R.id.drawer_layout);
        mContentLayout      = (RelativeLayout)  findViewById(R.id.content_layout);
        mDrawerRecyclerView = (RecyclerView)    findViewById(R.id.drawer_list_view);
    }

    private void setListeners(){
        mDrawerButton.setOnClickListener(this);
        mDrawerLayout.addDrawerListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.drawer_button:
                onDrawerButtonClicked();
                break;
        }
    }

    private void loadDrawerList(){
        mDrawerListAdapter = new DrawerListAdapter(this,this);
        mDrawerRecyclerView.setAdapter(mDrawerListAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mDrawerRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void onDrawerButtonClicked(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mContentLayout.setTranslationX(slideOffset * drawerView.getWidth());
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onClickViewInDrawer(View view) {
        if(view.getId() == R.id.profile_image){
            chooseImage();
            mProfileImageView = (ImageView) view;
        }
    }

    private void chooseImage(){

        final File root = new File(DIR);
        root.mkdirs();
        String fileName                     = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory     = new File(root, fileName);
        mFileUri                            = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents    = new ArrayList<>();
        final Intent captureIntent          = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam     = packageManager.queryIntentActivities(captureIntent, 0);

        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
            cameraIntents.add(intent);
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);


        final Intent chooserIntent = Intent.createChooser(intent, "Profile photo");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(Intent.createChooser(chooserIntent, getString(R.string.select_image)), PICK_IMAGE);
    }

    private void performCrop(Uri picUri){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 500);
        cropIntent.putExtra("outputY", 500);
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,mFileUri);
        startActivityForResult(cropIntent, CROP_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            boolean isCamera;

            if(data == null)
                isCamera = true;
            else{
                final String action = data.getAction();
                isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }

            Uri selectedImageUri;
            if (isCamera)
                selectedImageUri = mFileUri;
            else
                selectedImageUri = data == null ? null : data.getData();

            performCrop(selectedImageUri);
        }
        else if(requestCode == CROP_IMAGE && resultCode == RESULT_OK){
            try {
                Bitmap profileImageView = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), data.getData());
                mProfileImageView.setImageBitmap(profileImageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
