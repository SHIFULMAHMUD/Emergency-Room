package com.android.emergencymedicalsystem.user.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Payment;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.LoginActivity;
import com.android.emergencymedicalsystem.user.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    EditText donor_name_Et,mobile_Et,address_Et,amount_Et,comment_Et, trx_id,txt_date;
    ProgressDialog loading;
    TextView headingtv;
    String getCell,fair,time,finalfair;
    double ridefair,ridetime,totalfair;
    private ApiInterface apiInterface;
    public Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener appointment_date;
    RadioButton radioButton,radioButtontwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Make Payment");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        trx_id=findViewById(R.id.bkash_trxid_et);
        txt_date=findViewById(R.id.card_expiry_date_et);
        donor_name_Et=findViewById(R.id.nameEdittext);
        mobile_Et=findViewById(R.id.mobile_noEdittext);
        address_Et=findViewById(R.id.full_addressEdittext);
        amount_Et=findViewById(R.id.amountEdittext);
        comment_Et=findViewById(R.id.commentEdittext);
        headingtv=findViewById(R.id.heading_text);
        mobile_Et.setText(getCell);

        getFairData(getCell);



        radioButton=findViewById(R.id.rb);
        radioButtontwo=findViewById(R.id.rb1);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        radioButtontwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialogTwo();
            }
        });

    }

    private void submit(String trx_id) {

        //Getting values from edit texts
        final String name = donor_name_Et.getText().toString().trim();
        final String cell = getCell;
        final String trxid=trx_id;
        final String address = address_Et.getText().toString().trim();
        final String amount = finalfair;
        final String comment = comment_Et.getText().toString().trim();


        //Checking  field/validation
        if (name.isEmpty()) {
            donor_name_Et.setError("Please enter your name !");
            requestFocus(donor_name_Et);
        }
        else if (cell.length()!=11 && !cell.startsWith("01")) {

            mobile_Et.setError("Please enter valid phone number !");
            requestFocus(mobile_Et);

        }

        else if (address.isEmpty()) {

            address_Et.setError("Please enter full address !");
            requestFocus(address_Et);
        }
        else if (amount.isEmpty()) {

            amount_Et.setError("Please enter payment amount !");
            requestFocus(amount_Et);
        }

        else
        {
            payment(name, cell, address, amount,trxid, comment);
        }
    }
    private void submit(String card_number,String card_cvc,String card_holder_name,String date) {

        //Getting values from edit texts
        final String name = donor_name_Et.getText().toString().trim();
        final String cell = getCell;
        final String cardnumber=card_number;
        final String cardcvc=card_cvc;
        final String cardholdername=card_holder_name;
        final String cardexpirydate=date;
        final String address = address_Et.getText().toString().trim();
        final String amount = finalfair;
        final String comment = comment_Et.getText().toString().trim();


        //Checking  field/validation
        if (name.isEmpty()) {
            donor_name_Et.setError("Please enter name !");
            requestFocus(donor_name_Et);
        }
        else if (cell.length()!=11 && cell.startsWith("01")) {

            mobile_Et.setError("Please enter valid phone number !");
            requestFocus(mobile_Et);

        }

        else if (address.isEmpty()) {

            address_Et.setError("Please enter full address !");
            requestFocus(address_Et);
        }
        else if (amount.isEmpty()) {

            amount_Et.setError("Please enter donation amount !");
            requestFocus(amount_Et);
        }

        else
        {
            paymentCard(name, cell, address, amount,cardnumber,cardcvc,cardholdername,cardexpirydate, comment);
        }
    }
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_form);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText txt_trx_id = dialog.findViewById(R.id.bkash_trxid_et);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_trx_id= txt_trx_id.getText().toString();
                if (get_trx_id.isEmpty())
                {
                    txt_trx_id.setError("Enter bKash Trx ID !");
                    txt_trx_id.requestFocus();
                }else {

                    dialog.dismiss();
                    submit(get_trx_id);
                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showCustomDialogTwo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_form_two);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText txt_card_number = dialog.findViewById(R.id.card_number_et);
        final EditText txt_card_cvc = dialog.findViewById(R.id.card_cvc_et);
        final EditText txt_card_holder_name = dialog.findViewById(R.id.card_holder_name_et);
        txt_date = dialog.findViewById(R.id.card_expiry_date_et);


        ImageButton imgType = dialog.findViewById(R.id.img_type);



        appointment_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        imgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {



                    DatePickerDialog dpd = new DatePickerDialog(PaymentActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, appointment_date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");


                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    Date d = sdf2.parse(date);
                    dpd.getDatePicker().setMinDate(d.getTime());


                    dpd.show();


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        });


        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//

                try {



                    DatePickerDialog dpd = new DatePickerDialog(PaymentActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,appointment_date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");


                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    Date d = sdf2.parse(date);
                    dpd.getDatePicker().setMinDate(d.getTime());
                    dpd.show();


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date= txt_date.getText().toString();
                String get_card_number= txt_card_number.getText().toString();
                String get_card_cvc= txt_card_cvc.getText().toString();
                String get_card_holder_name= txt_card_holder_name.getText().toString();

                if (get_card_number.isEmpty())
                {
                    txt_card_number.setError("Enter Card Number !");
                    txt_card_number.requestFocus();
                }
                else if (get_card_cvc.isEmpty())
                {
                    txt_card_cvc.setError("Enter Card CVC !");
                    txt_card_cvc.requestFocus();
                }
                else if (get_card_holder_name.isEmpty())
                {
                    txt_card_holder_name.setError("Enter Card Holder Name !");
                    txt_card_holder_name.requestFocus();
                }
                else if (date.isEmpty())
                {
                    txt_date.setError("Select expiry date!");
                    txt_date.requestFocus();
                    Toasty.error(PaymentActivity.this,"Select Expiry Date!",Toasty.LENGTH_SHORT).show();
                }

                else {

                    dialog.dismiss();
                    submit(get_card_number,get_card_cvc,get_card_holder_name,date);
                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    //for date input
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));

    }
    private void payment(String name,String cell,String address,String amount,String trxid,String comment) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Payment> call = apiInterface.payment(name, cell,address, amount, trxid,comment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {

                    String value = response.body().getValue();
                    String message = response.body().getMassage();



                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(PaymentActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else {
                    loading.dismiss();
                    Toasty.error(PaymentActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
                }


            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

                loading.dismiss();
                Toasty.error(PaymentActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
    private void paymentCard(String name,String cell,String address,String amount,String cardnumber,String cardcvc,String cardholdername,String cardexpirydate,String comment) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Payment> call = apiInterface.paymentCard(name, cell,address, amount, cardnumber,cardcvc,cardholdername,cardexpirydate,comment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(PaymentActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else {
                    loading.dismiss();
                    Toasty.error(PaymentActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

                loading.dismiss();
                Toasty.error(PaymentActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
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

                    if (fairdata.isEmpty()) {

                    } else {

                        fair = fairdata.get(0).getFair();
                        time = fairdata.get(0).getTime();
                        ridefair= Double.parseDouble(fair);
                        ridetime= Double.parseDouble(time);
                        totalfair=ridefair*ridetime;
                        finalfair= String.valueOf(totalfair);
                        headingtv.setText("Total Ride Fair : Tk "+finalfair);
                        amount_Et.setText(finalfair);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //for back button
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



