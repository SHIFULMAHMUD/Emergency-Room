package com.android.emergencymedicalsystem;

public class Constant {

    public static final String BASE_URL = "http://finalproject24.com/emergency_service/android/";
    public static final String IMAGE_URL = "http://finalproject24.com/emergency_service/android/my_images/";
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
    public static final String KEY_REQUEST="request";
    public static final String KEY_PASSENGER_CELL="passenger_cell";
    public static final String KEY_TIME="time";
    public static final String KEY_AMOUNT="amount";
    public static final String KEY_COMMENT="comment";
    public static final String KEY_TRX_ID="trxid";
    public static final String KEY_CARD_NUMBER="cardnumber";
    public static final String KEY_CARD_CVC="cardcvc";
    public static final String KEY_CARD_HOLDER="cardholdername";
    public static final String KEY_CARD_EXPIRY="cardexpirydate";


    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.android.emergencymedicalsystem"; //package name

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";

    //This would be used to store the cell of current logged in user
    public static final String LATITUDE_SHARED_PREF = "latitude";

    //This would be used to store the cell of current logged in user
    public static final String LONGITUDE_SHARED_PREF = "longitude";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}
