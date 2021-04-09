package com.daniel.androidseminar_hw1.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.androidseminar_hw1.MainActivity;
import com.daniel.androidseminar_hw1.R;
import com.daniel.androidseminar_hw1.recyclers.RecyclerAdapter;
import com.daniel.androidseminar_hw1.utils.retrofit.Country;
import com.daniel.androidseminar_hw1.utils.retrofit.CountryAPI;
import com.daniel.androidseminar_hw1.utils.retrofit.CountryController;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorderCountriesFragment extends Fragment {

    private OnSecondFragmentInteractionListener mListener;

    private CountryAPI countryAPI;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private MaterialButton button;
    private ArrayList<Country> borders;

    public BorderCountriesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View v = inflater.inflate(R.layout.fragment_border_countries, container, false);

        this.countryAPI = new CountryController().init();
        recyclerView = v.findViewById(R.id.border_recycler);
        button = (MaterialButton) v.findViewById(R.id.back_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go_back_fragment();
            }
        });

        Intent i = getActivity().getIntent();

        Country country = (Country) i.getSerializableExtra("Country");
        this.borders = new ArrayList<>();
        String codes = "";

        for (String alphaCode : country.getBorders()) {
            codes += alphaCode.toLowerCase() + ";";
        }

        if (codes.length() != 0){
            Call<List<Country>> call = countryAPI.getBorderCountries(codes.substring(0, codes.length() - 1));
            call.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if (response.isSuccessful()) {
                        List<Country> responseCountries = response.body();

                        if (responseCountries.size() == 0)
                            Toast.makeText(getActivity(), "Sorry No Borders", Toast.LENGTH_LONG).show();
                        else {
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                            recyclerAdapter = new RecyclerAdapter(v.getContext(), responseCountries, new RecyclerAdapter.ItemClickListener() {
                                @Override
                                public void OnItemClick(Country country) {

                                }
                            });
                            recyclerView.setAdapter(recyclerAdapter);
                        }
                    } else {
                        System.out.println(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }else {
            Toast.makeText(getActivity(), "Sorry No Borders", Toast.LENGTH_LONG).show();
        }

        return v;
    }

    private void Go_back_fragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.GoBack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSecondFragmentInteractionListener) {
            mListener = (OnSecondFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSecondFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSecondFragmentInteraction(Uri uri);
    }
}
