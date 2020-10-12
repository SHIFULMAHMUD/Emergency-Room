package com.android.emergencymedicalsystem.remote;

import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.model.Nurse;
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
            @Field(Constant.KEY_LATITUDE) String latitude,
            @Field(Constant.KEY_LONGITUDE) String longitude);

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

    @GET("get_covid_center.php")
    Call<List<CovidTestCenter>> getCovidCenter(
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
    @GET("general_nurse.php")
    Call<List<Nurse>> getGeneralNurse(
            @Query("name") String name,
            @Query("cell") String cell,
            @Query("hospital") String hospital
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
    Call<List<Nurse>> getCovidNurse(
            @Query("name") String name,
            @Query("cell") String cell,
            @Query("hospital") String hospital
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
}