package com.android.emergencymedicalsystem.remote;

import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.model.Hospital;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.Payment;
import com.android.emergencymedicalsystem.model.Sample;
import com.android.emergencymedicalsystem.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //for signup
    @FormUrlEncoded
    @POST("register.php")
     Call<User> signUp(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_DIVISION) String division,
            @Field(Constant.KEY_AREA) String area,
            @Field(Constant.KEY_BLOOD_GROUP) String blood,
            @Field(Constant.KEY_LATITUDE) String latitude,
            @Field(Constant.KEY_LONGITUDE) String longitude);

    @FormUrlEncoded
    @POST("bkash_payment.php")
    Call<Payment> payment(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_AMOUNT) String amount,
            @Field(Constant.KEY_TRX_ID) String trxid,
            @Field(Constant.KEY_COMMENT) String comment);

    @FormUrlEncoded
    @POST("card_payment.php")
    Call<Payment> paymentCard(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_AMOUNT) String amount,
            @Field(Constant.KEY_CARD_NUMBER) String cardnumber,
            @Field(Constant.KEY_CARD_CVC) String cardcvc,
            @Field(Constant.KEY_CARD_HOLDER) String cardholdername,
            @Field(Constant.KEY_CARD_EXPIRY) String cardexpirydate,
            @Field(Constant.KEY_COMMENT) String comment);

    @FormUrlEncoded
    @POST("sample.php")
    Call<Sample> collectSample(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_BLOOD_GROUP) String blood,
            @Field(Constant.KEY_GENDER) String gender,
            @Field(Constant.KEY_AGE) String age,
            @Field(Constant.KEY_STATUS) String status);

    //for login
    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);


    //for get profile data
    @GET("profile.php")
    Call<List<User>> getProfile(
            @Query("cell") String cell
    );
    @GET("get_fair.php")
    Call<List<Ambulance>> getFair(
            @Query("passenger_cell") String passenger_cell
    );

    @GET("get_covid_center.php")
    Call<List<CovidTestCenter>> getCovidCenter(
            @Query("id") String id
    );
    @GET("get_hospital.php")
    Call<List<Hospital>> getHospital(
            @Query("id") String id
    );
    @GET("get_bloodbank.php")
    Call<List<Hospital>> getBloodBank(
            @Query("id") String id
    );
    @GET("get_isolation_center.php")
    Call<List<CovidTestCenter>> getIsolationCenter(
            @Query("id") String id
    );
    @FormUrlEncoded
    @POST("update_profile.php")
    Call<User> updateProfile(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_BLOOD_GROUP) String blood,
            @Field(Constant.KEY_DIVISION) String division,
            @Field(Constant.KEY_AREA) String area);
    @FormUrlEncoded
    @POST("hire_ambulance.php")
    Call<Ambulance> hireAmbulance(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_REQUEST) String request,
            @Field(Constant.KEY_TIME) String time,
            @Field(Constant.KEY_PASSENGER_CELL) String passenger_cell);
    @FormUrlEncoded
    @POST("update_status.php")
    Call<User> updateStatus(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_STATUS) String status);
    @GET("general_nurse.php")
    Call<List<Nurse>> getGeneralNurse();
    @GET("search_general_nurse.php")
    Call<List<Nurse>> getSearchGeneralNurse(
            @Query("text") String text
    );
    @GET("get_donor.php")
    Call<List<User>> getDonor();
    @GET("get_search_donor.php")
    Call<List<User>> getSearchDonor(
            @Query("text") String text
    );
    @GET("get_dhk_area.php")
    Call<List<User>> getDhkArea();

    @GET("get_ctg_area.php")
    Call<List<User>> getCtgArea();

    @GET("get_result.php")
    Call<List<Sample>> getCovidResult(
            @Query("cell") String cell
    );
    @GET("covid_nurse.php")
    Call<List<Nurse>> getCovidNurse();
    @GET("search_covid_nurse.php")
    Call<List<Nurse>> getSearchCovidNurse(
            @Query("text") String text
    );
    @GET("user_latlng.php")
    Call<List<User>> getUserLatLng(
            @Query("cell") String cell
    );
    @GET("covid_center.php")
    Call<List<CovidTestCenter>> getCovidTestCenter(
            @Query("id") String id,
            @Query("name") String name,
            @Query("cell") String cell,
            @Query("address") String address,
            @Query("facility") String facility,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );
    @GET("hospital.php")
    Call<List<CovidTestCenter>> getHospital();
    @GET("bloodbank.php")
    Call<List<CovidTestCenter>> getBloodBank();
    @GET("isolation_center.php")
    Call<List<CovidTestCenter>> getIsolationCenter(
            @Query("id") String id,
            @Query("name") String name,
            @Query("cell") String cell,
            @Query("address") String address,
            @Query("facility") String facility,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @GET("ambulance.php")
    Call<List<Ambulance>> getAmbulance();

    @GET("search_ambulance.php")
    Call<List<Ambulance>> getSearchAmbulance(
            @Query("division") String division,
            @Query("area") String area
    );
}