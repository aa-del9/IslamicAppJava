package com.oop_project.cs212.islamicapp;

public class Utils {
    public static final String BASE_URL = "https://lazycoderbd.000webhostapp.com/";
    public static final String GET_ATHKAR_API = "/api/readAthkar";

    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
            "net.a6te.lazycoder.aafwathakkir_islamicreminders.BROADCAST";
    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS_MESSAGE =
            "net.a6te.lazycoder.aafwathakkir_islamicreminders.STATUS";

    public static final String EXTENDED_IS_UPDATE_DATA =
            "net.a6te.lazycoder.aafwathakkir_islamicreminders.MESSAGE";

    public static final String BROADCAST_CONNECTION_STATUS = "locationAndGpsStatus";
    public static final String CONNECTION_STATUS = "connectionStatus";

    public static final String DATA_CONNECTION_ENABLE = "dataConnectionEnable";

    public static final String STATUS_CODE = "statusCode";

    public static final int NO_CONNECTION_CODE = 220;
    public static final int ALL_CONNECTED = 201;
}
