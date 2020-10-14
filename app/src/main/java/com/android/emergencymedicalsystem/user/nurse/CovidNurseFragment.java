package com.android.emergencymedicalsystem.user.nurse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CovidNurseFragment extends Fragment {
    private List<Nurse> nurseList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ApiInterface apiInterface;
    Button btnSearch;
    EditText txtSearch;
    private ProgressBar progressBar;
    public CovidNurseFragment() {
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
        View view= inflater.inflate(R.layout.fragment_covid_nurse, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        btnSearch=view.findViewById(R.id.btn_search);
        txtSearch=view.findViewById(R.id.txt_search);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(getContext(), "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        else {
            getNurseData();
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search=txtSearch.getText().toString();

                if (search.isEmpty())
                {
                    Toasty.info(getActivity(), "Please enter division name!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    getData(search);

                }

            }
        });

        return view;
    }
    public void getNurseData() {
        Call<List<Nurse>> call = apiInterface.getCovidNurse();
        call.enqueue(new Callback<List<Nurse>>() {
            @Override
            public void onResponse(Call<List<Nurse>> call, Response<List<Nurse>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                nurseList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), nurseList);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Nurse>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(String text) {
        Call<List<Nurse>> call = apiInterface.getSearchCovidNurse(text);
        call.enqueue(new Callback<List<Nurse>>() {
            @Override
            public void onResponse(Call<List<Nurse>> call, Response<List<Nurse>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                nurseList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), nurseList);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Nurse>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
