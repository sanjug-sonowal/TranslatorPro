package com.translatorpro.protranslator.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import com.adjust.sdk.Adjust;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static final String TP_PREF_NAME = "translatorpro";
    public static final String TP_USER_UUID = "click_id";
    public static final String TP_EVENT_VALUE = "eventValue";
    public static final String TP_ADJUST_ATTRIBUTE = "adjust_attribute";
    public static final String TP_GPS_AD_ID = "gpsAdid";
    public static final String TP_REFERRING_LINK = "referringLink";
    public static final String TP_KEY_FIREBASE_INSTANCE_ID = "firebase_instance_id";

    public static final String TP_ADJUST_TOKEN = "hp6i1s0m0sg0";
    public static final String TP_FIREBASE_INSTANCE_ID = "5o3hkp";
    public static final String TP_END_POINT = "https://d37hvdgwlbdxcr.cloudfront.net";
    public static final String TP_KEY_CAMPAIGN = "Campaign";
    public static final String TP_KEY_IS_SECOND_TIME = "isSecondTime";

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null && cm.getActiveNetworkInfo() != null) && cm
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void setAppReceivedAttribution(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_ADJUST_ATTRIBUTE, value);
            editor.apply();
        }
    }

    public static String getAppReceivedAttribution(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
        return preferences.getString(TP_ADJUST_ATTRIBUTE, "");
    }

    public static void setAppFirebaseInstanceId(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_KEY_FIREBASE_INSTANCE_ID, value);
            editor.apply();
        }
    }

    public static String getAppFirebaseInstanceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
        return preferences.getString(TP_KEY_FIREBASE_INSTANCE_ID, "");
    }

    public static void setAppUserUUID(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME,
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_USER_UUID, value);
            editor.apply();
        }
    }

    public static String getAppUserUUID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME,
                MODE_PRIVATE);
        return preferences.getString(TP_USER_UUID, "");
    }

    public static void setGPSADId(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_GPS_AD_ID, value);
            editor.apply();
        }
    }

    public static String getGPSADId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
        return preferences.getString(TP_GPS_AD_ID, "");
    }

    public static String AppGenerateUserUUID(Context context) {
        String md5uuid = getAppUserUUID(context);
        if (md5uuid == null || md5uuid.isEmpty()) {
            String guid = "";
            final String uniqueID = UUID.randomUUID().toString();
            Date date = new Date();
            long timeMilli = date.getTime();
            guid = uniqueID + timeMilli;
            md5uuid = md5(guid);
            setAppUserUUID(context, md5uuid);
        }
        return md5uuid;
    }

    private static String md5(String s) {
        try {

            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static boolean isValidCampaign(Context context){
        try {
            String[] campDiv= getCampaign(context).split("_");
            if (campDiv.length > 0 && !campDiv[0].isEmpty()){
                String regex = "^[{]?[0-9a-fA-F]{8}"
                        + "-([0-9a-fA-F]{4}-)"
                        + "{3}[0-9a-fA-F]{12}[}]?$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(campDiv[0]);
                return m.matches();
            }

        }catch (Exception e) {
        }
        return false;
    }

    public static void setReferringLink(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_REFERRING_LINK, value);
            editor.apply();
        }
    }

    public static String getReferringLink(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
        return preferences.getString(TP_REFERRING_LINK, "");
    }

    public static void setCampaign(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TP_KEY_CAMPAIGN, value);
            editor.apply();
        }
    }

    public static String getCampaign(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, MODE_PRIVATE);
        return preferences.getString(TP_KEY_CAMPAIGN, "");
    }

    public static void setSecondTime(Context context, final boolean value) {
        SharedPreferences settings = context.getSharedPreferences(TP_PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(TP_KEY_IS_SECOND_TIME, value);
        editor.apply();
    }

    public static boolean isSecondTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TP_PREF_NAME, 0);
        return preferences.getBoolean(TP_KEY_IS_SECOND_TIME, false);
    }


    public static String AppGeneratePremiumLink(Context context) {
        String strPreURLLink = "";
        try {
            strPreURLLink = TP_END_POINT
                    + "?naming=" + URLEncoder.encode(getCampaign(context), "utf-8")
                    + "&gps_adid=" + getGPSADId(context)
                    + "&adid=" + Adjust.getAdid()
                    + "&package=" + "com.translatorpro.protranslator"
                    + "&deeplink=" + URLEncoder.encode(getReferringLink(context), "utf-8")
                    + "&click_id=" + getAppUserUUID(context)
                    + "&adjust_attribution=" + URLEncoder.encode(getAppReceivedAttribution(context), "utf-8")
                    + "&android_id=" + getAppFirebaseInstanceId(context);

            Log.e("strPreURLLink=", strPreURLLink);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strPreURLLink;
    }
}
