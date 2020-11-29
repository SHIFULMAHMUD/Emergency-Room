package com.android.emergencymedicalsystem.user.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.HomeActivity;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.driver.DriverLoginActivity;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Payment;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.LoginActivity;
import com.android.emergencymedicalsystem.user.MainActivity;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {
        Dialog myDialog;
    String fair,time,getCell;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Make Payment");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        myDialog = new Dialog(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        ShowPopup();
    }
    public void ShowPopup() {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.paymentpopupmenu);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnskip);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    public void PayRideBill(View v){
        getFairData(getCell);
    }
    public void PayTestBill(View v){
        Intent intent=new Intent(PaymentActivity.this, SamplePaymentActivity.class);
        startActivity(intent);
        finish();
    }
    public void getFairData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getFair(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> fairdata;
                    fairdata = response.body();

                    if (fairdata.isEmpty() || fairdata.get(0).getTime().equals("Null")) {
                        Toasty.error(PaymentActivity.this,"You have no payment !",Toasty.LENGTH_SHORT).show();
                    } else {

                        fair = fairdata.get(0).getFair();
                        time = fairdata.get(0).getTime();

                        Intent intent=new Intent(PaymentActivity.this, RidePaymentActivity.class);
                        startActivity(intent);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
