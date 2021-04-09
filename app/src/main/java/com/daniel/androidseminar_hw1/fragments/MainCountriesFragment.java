package com.daniel.androidseminar_hw1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.androidseminar_hw1.MainActivity;
import com.daniel.androidseminar_hw1.R;
import com.daniel.androidseminar_hw1.recyclers.RecyclerAdapter;
import com.daniel.androidseminar_hw1.utils.retrofit.Country;
import com.daniel.androidseminar_hw1.utils.retrofit.CountryAPI;
import com.daniel.androidseminar_hw1.utils.retrofit.CountryController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainCountriesFragment extends Fragment {

    private CountryAPI countryAPI;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private OnFirstFragmentInteractionListener mListener;

    public MainCountriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.countryAPI = new CountryController().init();
        final View v = inflater.inflate(R.layout.fragment_main_countries, container, false);

        recyclerView = v.findViewById(R.id.MAIN_recycler);

        Call<List<Country>> call = countryAPI.getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    List<Country> countries = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    recyclerAdapter = new RecyclerAdapter(v.getContext(), countries, new RecyclerAdapter.ItemClickListener() {
                        @Override
                        public void OnItemClick(Country country) {
                            MainActivity ma = (MainActivity) getActivity();
                            ma.LoadSecFragment(country);
                        }
                    });
                    recyclerView.setAdapter(recyclerAdapter);

                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
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
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }
}
