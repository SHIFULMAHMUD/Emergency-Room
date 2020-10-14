package com.android.emergencymedicalsystem.user.covid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.adapter.ResultAdapter;
import com.android.emergencymedicalsystem.model.Sample;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CovidResultFragment extends Fragment {
    private List<Sample> resultList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultAdapter adapter;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    public CovidResultFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_covid_result, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =this.getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
//Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(getContext(), "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        else {
            getResultData(getCell);
        }


        return view;
    }
    public void getResultData(String cell) {
        Call<List<Sample>> call = apiInterface.getCovidResult(cell);
        call.enqueue(new Callback<List<Sample>>() {
            @Override
            public void onResponse(Call<List<Sample>> call, Response<List<Sample>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                resultList = response.body();
                adapter = new ResultAdapter(getActivity(), resultList);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Sample>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
