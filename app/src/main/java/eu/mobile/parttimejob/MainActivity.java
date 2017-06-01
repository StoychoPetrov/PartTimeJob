package eu.mobile.parttimejob;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DrawerLayout.DrawerListener{

    private ImageButton     mDrawerButton;
    private DrawerLayout    mDrawerLayout;
    private RelativeLayout  mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setListeners();
    }

    private void initUI(){
        mDrawerButton   = (ImageButton)     findViewById(R.id.drawer_button);
        mDrawerLayout   = (DrawerLayout)    findViewById(R.id.drawer_layout);
        mContentLayout  = (RelativeLayout)  findViewById(R.id.content_layout);
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
}
