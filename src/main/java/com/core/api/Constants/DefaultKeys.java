package com.core.api.Constants;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


public final class DefaultKeys {
    private DefaultKeys() {}


    public static final String PFX_USER = "USER#";
    public static final String PFX_FUND = "FUND#";
    public static final String PFX_WALLET = "WALLET#";
    public static final String PFX_TX = "TX#";
    public static final DateTimeFormatter TS_FMT =
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssSSS'Z'").withZone(ZoneOffset.UTC);
}