package com.cs.heart_release_sock.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName CornTimeUtil
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/24 0:37
 **/
public class CornTimeUtil {
    private static class CornTimeUtilHolder{
        private static final CornTimeUtil instance = new CornTimeUtil();
    }

    public CornTimeUtil() {}
    public static CornTimeUtil getInstance(){return CornTimeUtilHolder.instance;}

    public String dateToCorn(LocalDateTime localDateTime){
        String cornTime = null;
        if (localDateTime != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ss mm HH dd MM ? yyyy");
            cornTime = dateTimeFormatter.format(localDateTime);
        }
        return cornTime;
    }

    public String dateToCorn(Date date){
        String cornTIme = null;
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
            cornTIme = simpleDateFormat.format(date);
        }
        return cornTIme;
    }

    public Date cornToDate(String cornTime) throws ParseException {
        Date date = null;
        if (StringUtils.isNotBlank(cornTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
            date = simpleDateFormat.parse(cornTime);
        }
        return date;
    }
}
