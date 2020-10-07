package com.android.emergencymedicalsystem.user.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IsolationCenterFragment extends Fragment {
    private List<Nurse> nurseList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    public IsolationCenterFragment() {
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

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        getNurseData("","","");

        return view;
    }
    public void getNurseData(String name, String cell,String hospital) {
        Call<List<Nurse>> call = apiInterface.getCovidNurse(name,cell,hospital);
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
