package com.example.mobilesporta.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class    MapConst {

    public static final String	STATUS_MATCH_NONE_KEY						= "NONE";
    public static final String	STATUS_MATCH_CONFIRMING_KEY				    = "CONFIRMING";
    public static final String	STATUS_MATCH_DONE_KEY						= "DONE";

    public static final String	STATUS_MATCH_NONE					= "N";
    public static final String	STATUS_MATCH_CONFIRMING			    = "C";
    public static final String	STATUS_MATCH_DONE					= "D";

    public static Map<String, String> STATUS_MATCH_MAP = new LinkedHashMap<>();
    static{
        STATUS_MATCH_MAP.put(STATUS_MATCH_NONE_KEY, STATUS_MATCH_NONE);
        STATUS_MATCH_MAP.put(STATUS_MATCH_CONFIRMING_KEY, STATUS_MATCH_CONFIRMING);
        STATUS_MATCH_MAP.put(STATUS_MATCH_DONE_KEY, STATUS_MATCH_DONE);
    }

    public static Map<String, String> STADIUM_ZONE_MAP = new LinkedHashMap<String, String>();

    static{
        STADIUM_ZONE_MAP.put("Ba_Dinh", "Quận Ba Đình");
        STADIUM_ZONE_MAP.put("Hoan_Kiem", "Quận Hoàn Kiếm");
        STADIUM_ZONE_MAP.put("Tay_Ho", "Quận Tây Hồ");
        STADIUM_ZONE_MAP.put("Long_Bien", "Quận Long Biên");
        STADIUM_ZONE_MAP.put("Cau_Giay", "Quận Cầu Giấy");
        STADIUM_ZONE_MAP.put("Dong_Da", "Quận Đống Đa");
        STADIUM_ZONE_MAP.put("Hai_Ba_Trung", "Quận Hai Bà Trưng");
        STADIUM_ZONE_MAP.put("Hoang_Mai", "Quận Hoàng Mai");
        STADIUM_ZONE_MAP.put("Thanh_Xuan", "Quận Thanh Xuân");
        STADIUM_ZONE_MAP.put("Nam_Tu_Liem", "Quận Nam Từ Liêm");
        STADIUM_ZONE_MAP.put("Bac_Tu_Liem", "Quận Bắc Từ Liêm");
        STADIUM_ZONE_MAP.put("Ha_Dong", "Quận Hà Đông");
    }
}
