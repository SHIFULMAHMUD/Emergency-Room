package com.android.emergencymedicalsystem;

public class Constant {

    public static final String BASE_URL = "http://finalproject24.com/emergency_service/android/";

    public static final String KEY_NAME="name";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_CELL="cell";
    public static final String KEY_DIVISION="division";
    public static final String KEY_AREA="area";
    public static final String KEY_ADDRESS="address";
    public static final String KEY_BLOOD_GROUP="blood";
    public static final String KEY_LATITUDE="latitude";
    public static final String KEY_LONGITUDE="longitude";
    public static final String KEY_GENDER="gender";
    public static final String KEY_AGE="age";
    public static final String KEY_STATUS="status";


    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.android.emergencymedicalsystem"; //package name

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}
