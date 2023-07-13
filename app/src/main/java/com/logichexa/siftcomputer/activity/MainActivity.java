package com.logichexa.siftcomputer.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.logichexa.siftcomputer.R;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageSlider imageSlider;
    private CardView medical;
    private CardView turist;
    private CardView number;
    private CardView location;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private RewardedAd rewardedAd;
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSlider = findViewById(R.id.image_slider);
        medical = findViewById(R.id.medical);
        turist = findViewById(R.id.turist);
        number = findViewById(R.id.number);
        location = findViewById(R.id.location);
        //ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-9312766620009008/9194230366",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;


                    }
                });





        //image
        ArrayList imageList = new ArrayList<SlideModel>();
        //imageList.add(new SlideModel(String.valueOf(R.drawable.one), ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.one, null));
        imageList.add(new SlideModel(R.drawable.four, null));
        imageList.add(new SlideModel(R.drawable.five, null));
        imageList.add(new SlideModel(R.drawable.two, null));
        imageList.add(new SlideModel(R.drawable.three, null));



        imageSlider.setImageList(imageList);
        imageSlider.setImageList(imageList, ScaleTypes.FIT); // for all images

        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick(1);
                Intent intent = new Intent(getApplicationContext(),MedicalActivity.class);
                startActivity(intent);

            }
        });
        turist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick(1);
                Intent intent = new Intent(getApplicationContext(),TruestActivity.class);
                startActivity(intent);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick(1);
                String phoneNumber = "+8801746325048";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick(1);
                String latitude = "24.89989272206985";  // Latitude of the location
                String longitude = "89.35703611800838";  // Longitude of the location
                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Sifat Computer & Other's" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }
        });


        //navigation
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        // Set up the ActionBarDrawerToggle with the DrawerLayout
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void countClick(int i) {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        int a = sh.getInt("count", 0);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("count", a + i);
        myEdit.commit();
        loadAds();
        SharedPreferences sh1 = getSharedPreferences("MySharedPref", MODE_APPEND);
        int a1 = sh.getInt("count", 0);
        Toast.makeText(getApplicationContext(),"click" + a1,Toast.LENGTH_LONG).show();
    }

    private void loadAds() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        int a = sh.getInt("count", 0);
        Log.d(TAG, "Ad was loaded.");
        if(a >= 5){
            if (rewardedAd != null) {
                Activity activityContext = MainActivity.this;
                rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putInt("count", 1);
                        myEdit.commit();

                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the ActionBarDrawerToggle click events
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation menu item clicks here
        int itemId = item.getItemId();

        // Perform actions based on the selected item ID
        if(itemId == R.id.nav_item1){
            countClick(1);
            showAdressialog();
        } else if (itemId == R.id.nav_item2) {
            countClick(1);
            shownumberDialog();
        }else if (itemId == R.id.nav_item3) {
            countClick(1);
            openFacebookPage();
        }
        else if (itemId == R.id.nav_item4) {
            countClick(1);
            openWebPage();
        }


        // Close the navigation drawer after handling the click
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    private void openWebPage() {
        String webpageUrl = "https://logichexa.site/about-founder/";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageUrl));
        startActivity(browserIntent);

    }
    private void openFacebookPage() {
        String facebookUrl = "https://www.facebook.com/SifatComputerandothersBD"; // Replace with your Facebook page URL

        // If the Facebook app is not installed, open with a web browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);

    }
    private void showAdressialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("আমাদের ঠিকানা")
                .setMessage("হোটেল মোমো ইনের দক্ষিণ পাশে, ইসলাম প্লাজা, ডোকান নং-২, নওদাপাড়া, বগুড়া সদর, বগুড়া ।"
                        + "মোবাইল নাম্বার " + "  01746325048" + " হোয়াটসঅ্যাপ নম্বর" + " 01717830070")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle positive button click
                        dialog.dismiss(); // Close the dialog
                    }
                });
               /* .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click
                        dialog.dismiss(); // Close the dialog
                    }
                });*/

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void shownumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("হোয়াটসঅ্যাপ নম্বর এবং ইমেল")
                .setMessage("হোয়াটসঅ্যাপ নম্বর"+" 01717830070" + " ইমেল" + " rintusarker@gmail.com")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle positive button click
                        dialog.dismiss(); // Close the dialog
                    }
                });
              /*  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click
                        dialog.dismiss(); // Close the dialog
                    }
                });*/

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        // Close the navigation drawer when the back button is pressed
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}