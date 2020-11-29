package com.android.emergencymedicalsystem.remote;

import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.model.Hospital;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.Passenger;
import com.android.emergencymedicalsystem.model.Payment;
import com.android.emergencymedicalsystem.model.Sample;
import com.android.emergencymedicalsystem.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
            @Field(Constant.KEY_DATE) String date,
            @Field(Constant.KEY_LATITUDE) String latitude,
            @Field(Constant.KEY_LONGITUDE) String longitude,
            @Field(Constant.KEY_TOKEN) String token);
    @FormUrlEncoded
    @POST("api.php")
    Call<User> sendNotification(
            @Field(Constant.KEY_TITLE) String title,
            @Field(Constant.KEY_MESSAGE) String message,
            @Field(Constant.KEY_TOKEN) String token);
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
    @POST("bkash_bill_payment.php")
    Call<Payment> billPayment(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_BILL) String bill,
            @Field(Constant.KEY_TRX_ID) String trxid,
            @Field(Constant.KEY_NOTE) String note);

    @FormUrlEncoded
    @POST("cash_payment.php")
    Call<Payment> paymentCash(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_AMOUNT) String amount,
            @Field(Constant.KEY_CASH) String cash,
            @Field(Constant.KEY_COMMENT) String comment);

    @FormUrlEncoded
    @POST("cash_bill_payment.php")
    Call<Payment> billPaymentCash(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_BILL) String bill,
            @Field(Constant.KEY_CASH) String cash,
            @Field(Constant.KEY_NOTE) String note);

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
    @POST("card_bill_payment.php")
    Call<Payment> billPaymentCard(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_BILL) String bill,
            @Field(Constant.KEY_CARD_NUMBER) String cardnumber,
            @Field(Constant.KEY_CARD_CVC) String cardcvc,
            @Field(Constant.KEY_CARD_HOLDER) String cardholdername,
            @Field(Constant.KEY_CARD_EXPIRY) String cardexpirydate,
            @Field(Constant.KEY_NOTE) String note);

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
    @GET("get_ambulance.php")
    Call<List<Ambulance>> getStatus(
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
    @GET("get_ambulance_info.php")
    Call<List<Ambulance>> getAmbulance(
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
            @Field(Constant.KEY_AREA) String area,
            @Field(Constant.KEY_DATE) String date);
    @FormUrlEncoded
    @POST("hire_ambulance.php")
    Call<Ambulance> hireAmbulance(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_STATUS) String status,
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
            @Query("division") String division
    );

    //for signup
    @Multipart
    @POST("signup.php")
    Call<Ambulance> register(
            @Part MultipartBody.Part file,
            @Part(Constant.KEY_FILE) RequestBody name,
            @Part(Constant.KEY_NAME) RequestBody username,
            @Part(Constant.KEY_CELL) RequestBody cell,
            @Part(Constant.KEY_PASSWORD) RequestBody password,
            @Part(Constant.KEY_DIVISION) RequestBody division,
            @Part(Constant.KEY_AREA) RequestBody area,
            @Part(Constant.KEY_TYPE) RequestBody type,
            @Part(Constant.KEY_FAIR) RequestBody fair,
            @Part(Constant.KEY_VEHICLE_REG_NO) RequestBody registration,
            @Part(Constant.KEY_FACILITY) RequestBody facility,
            @Part(Constant.KEY_STATUS) RequestBody status,
            @Part(Constant.KEY_TOKEN) RequestBody token);
    @GET("get_amb_fair.php")
    Call<List<Ambulance>> getDriverFair(
            @Query("cell") String cell
    );
    @FormUrlEncoded
    @POST("api.php")
    Call<Ambulance> sendDriverNotification(
            @Field(Constant.KEY_TITLE) String title,
            @Field(Constant.KEY_MESSAGE) String message,
            @Field(Constant.KEY_TOKEN) String token);
    @GET("profile.php")
    Call<List<Passenger>> getPassenger(
            @Query("cell") String cell
    );
    @GET("get_ambulance.php")
    Call<List<Ambulance>> getDriverProfile(
            @Query("cell") String cell
    );
    @GET("get_passenger_token.php")
    Call<List<Ambulance>> getToken(
            @Query("cell") String cell
    );
    @GET("get_ambulance.php")
    Call<List<Ambulance>> getDriverAmbulance(
            @Query("cell") String cell
    );

    //for login
    @FormUrlEncoded
    @POST("driver_login.php")
    Call<Ambulance> driverLogin(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);
    @FormUrlEncoded
    @POST("amb_latlng.php")
    Call<Ambulance> ambLatLng(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_LATITUDE) String latitude,
            @Field(Constant.KEY_LONGITUDE) String longitude);

    @FormUrlEncoded
    @POST("update_ambulance.php")
    Call<Ambulance> updateProfile(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_VEHICLE_REG_NO) String regno,
            @Field(Constant.KEY_TYPE) String type,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_DIVISION) String division,
            @Field(Constant.KEY_AREA) String area,
            @Field(Constant.KEY_FAIR) String fair,
            @Field(Constant.KEY_FACILITY) String facility);

    @FormUrlEncoded
    @POST("update_ride_status.php")
    Call<Ambulance> updateStatus(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_STATUS) String status,
            @Field(Constant.KEY_REQUEST) String request);
    //for get ride data
    @GET("ride_data.php")
    Call<List<Ambulance>> getRideData(
            @Query("cell") String cell
    );

    @FormUrlEncoded
    @POST("confirm_ride.php")
    Call<Ambulance> confirmReq(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_STATUS) String status,
            @Field(Constant.KEY_REQUEST) String request);
    @FormUrlEncoded
    @POST("ride_time.php")
    Call<Ambulance> sendReq(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_TIME) String time);
    @FormUrlEncoded
    @POST("reject_ride.php")
    Call<Ambulance> rejectReq(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_STATUS) String status,
            @Field(Constant.KEY_REQUEST) String request);

}