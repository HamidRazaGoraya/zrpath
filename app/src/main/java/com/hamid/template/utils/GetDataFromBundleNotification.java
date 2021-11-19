package com.hamid.template.utils;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.Iterator;
import java.util.Map;

public class GetDataFromBundleNotification {
    public static final String RESERVED_PREFIX = "google.";
    public static final String FROM = "from";
    public static final String RAW_DATA = "rawData";
    public static final String MESSAGE_TYPE = "message_type";
    public static final String COLLAPSE_KEY = "collapse_key";
    public static final String MSGID_SERVER = "message_id";
    public static final String TO = "google.to";
    public static final String MSGID = "google.message_id";
    public static final String TTL = "google.ttl";
    public static final String SENT_TIME = "google.sent_time";
    public static final String ORIGINAL_PRIORITY = "google.original_priority";
    public static final String DELIVERED_PRIORITY = "google.delivered_priority";
    public static final String PRIORITY_V19 = "google.priority";
    public static final String PRIORITY_REDUCED_V19 = "google.priority_reduced";
    public static final String RESERVED_CLIENT_LIB_PREFIX = "google.c.";
    public static final String SENDER_ID = "google.c.sender.id";

    Bundle bundle;

    private Map<String, String> data;

    public GetDataFromBundleNotification(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    public Map<String, String> getData() {
        if (this.data == null) {
            this.data = extractDeveloperDefinedPayload(this.bundle);
        }

        return this.data;
    }

    public ArrayMap<String, String> extractDeveloperDefinedPayload(Bundle var0) {
        ArrayMap var1 = new ArrayMap();
        Iterator var2 = var0.keySet().iterator();

        while (var2.hasNext()) {
            String var3 = (String) var2.next();
            Object var4 = var0.get(var3);
            if (var4 instanceof String) {
                String var5 = (String) var4;
                if (!var3.startsWith("google.") && !var3.startsWith("gcm.") && !var3.equals("from") && !var3.equals("message_type") && !var3.equals("collapse_key")) {
                    var1.put(var3, var5);
                }
            }
        }

        return var1;
    }
}
