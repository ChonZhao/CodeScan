package com.hnhongxiang.code.codescan;

public class usersID {
    private static final String ZHAOCHONG_IMEI1 = "865611032163147";
    private final String ZHAOCHONG_ICCID1 = "89860117876098192994";

    public static boolean isUser(String s){

        if(s.equals(ZHAOCHONG_IMEI1)){
            return true;
        }else   return false;
    }
}
