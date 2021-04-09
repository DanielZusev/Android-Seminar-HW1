package com.daniel.androidseminar_hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import com.daniel.androidseminar_hw1.fragments.BorderCountriesFragment;
import com.daniel.androidseminar_hw1.utils.retrofit.Country;
import com.daniel.androidseminar_hw1.fragments.MainCountriesFragment;


public class MainActivity extends AppCompatActivity implements MainCountriesFragment.OnFirstFragmentInteractionListener, BorderCountriesFragment.OnSecondFragmentInteractionListener{

    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MainCountriesFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment, "0").commit();
        }
    }

    public void LoadSecFragment(Country country) {
        BorderCountriesFragment borderCountriesFragment = new BorderCountriesFragment();

        getIntent().putExtra("Country", country);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container,
                borderCountriesFragment,
                (getSupportFragmentManager().getBackStackEntryCount() - 1) + "").addToBackStack(null).commit();

        flag = 1;
    }

    public void GoBack() { // return to first fragment

        Fragment f;
        getSupportFragmentManager().popBackStack();
        int num = getSupportFragmentManager().getBackStackEntryCount();
        f = getSupportFragmentManager().findFragmentByTag("0");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f, (getSupportFragmentManager().getBackStackEntryCount() - 1) + "").commit();

        flag = 0;
    }


    @Override
    public void onBackPressed() { // override the back button on android phones

        if (flag == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public void onSecondFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFirstFragmentInteraction(Uri uri) {

    }
}