package com.hnhongxiang.code.codescan;

import android.icu.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class excelDate2android {

        private  final int SECONDS_PER_MINUTE = 60;
        private  final int MINUTES_PER_HOUR = 60;
        private  final int HOURS_PER_DAY = 24;
        private  final int SECONDS_PER_DAY = (HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
        /**
         一天的毫秒数
         **/
        private  final long DAY_MILLISECONDS = SECONDS_PER_DAY * 1000L;


        /**
         转换方法
         @parma numberString 要转换的浮点数
         @parma format 要获得的格式 例如"hh:mm:ss"
         **/
        public  String toDate(int i) {
            Calendar calendar = new GregorianCalendar();
            setCalendar(calendar, i, 0, false);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            String gang = "/";
            return year+gang+month+gang+day;
        }
/*        public  String toDate(double numberString) {
            int wholeDays = (int)Math.floor(numberString);
            int millisecondsInday = (int)((numberString - wholeDays) * DAY_MILLISECONDS + 0.5);
            Calendar calendar = new GregorianCalendar();
            setCalendar(calendar, wholeDays, millisecondsInday, false);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            String gang = "/";
            String mao = ":";
          int hour= calendar.get(Calendar.HOUR);
            int min= calendar.get(Calendar.MINUTE);
           int sec= calendar.get(Calendar.SECOND);
            return year+gang+month+gang+day+" "+hour+mao+min+mao+sec;

        }*/
        private  void setCalendar(Calendar calendar, int wholeDays,
                                        int millisecondsInDay, boolean use1904windowing) {
            int startYear = 1900;
            int dayAdjust = -1; // Excel thinks 2/29/1900 is a valid date, which it isn't
            if (use1904windowing) {
                startYear = 1904;
                dayAdjust = 1; // 1904 date windowing uses 1/2/1904 as the first day
            }
            else if (wholeDays < 61) {
                // Date is prior to 3/1/1900, so adjust because Excel thinks 2/29/1900 exists
                // If Excel date == 2/29/1900, will become 3/1/1900 in Java representation
                dayAdjust = 0;
            }
            calendar.set(startYear,0, wholeDays + dayAdjust, 0, 0, 0);
            calendar.set(GregorianCalendar.MILLISECOND, millisecondsInDay);
        }
    }
