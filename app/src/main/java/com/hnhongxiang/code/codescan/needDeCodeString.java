package com.hnhongxiang.code.codescan;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

class needDeCodeString {


    // static float[] codes ={d0,d5,d10,d50,d90,d95,d100,mv,sd,yizhixing};
    private  String [] datas ;
    private  String[] arg;
    public  String getData(int i){
        return addDot(datas[i]);
    }
    public String getDate(){

            int l = Integer.parseInt(arg[1]);

        return new excelDate2android().toDate(l) ;
    }
    public String getid(){
             return arg[2];
    }
    public String getPname(){
        return arg[3];
    }
    public String getDengji(){
        return arg[4];
    }
    public String getBeizhu(){
        if(arg[arg.length - 1] == null){
            return "-";
        }else  return arg[arg.length - 1];
    }
    public  needDeCodeString(Activity activity, String result){
         String index = "HNHX";
         if( !result.startsWith(index)){
             String s = "0";
             String notHNHX=activity.getString(R.string.NoHNHXqr);
             arg=new String[]{s,s,s,notHNHX,s,s};
             datas= new String[]{s,s,s,s,s,s,s,s,s,s};
             return;
         }
         arg = result.split("-");
         String s1 = arg[arg.length-2];
        String s2 = s1.replace("4","0");
         String s3 =s2.replace("g","7");
         String s4 = s3.replace("d","4");
         String s5 =s4.replace("a","1");

        int PPP =s5.indexOf("P");
        int QQQ =s5.indexOf("Q");
        int RRR =s5.indexOf("R");
        int SSS =s5.indexOf("S");
        int TTT =s5.indexOf("T");
        int UUU =s5.indexOf("U");
        int VVV =s5.indexOf("V");
        int WWW =s5.indexOf("W");
        int XXX =s5.indexOf("X");

        datas= new String[]{
        s5.substring(0,PPP),//d0
        s5.substring(PPP+1,QQQ),//d5
        s5.substring(QQQ+1,RRR),//d10
        s5.substring(RRR+1,SSS),//d50
        s5.substring(SSS+1,TTT),//d90
        s5.substring(TTT+1,UUU),//d95
        s5.substring(UUU+1,VVV),//d100
        s5.substring(VVV+1,WWW),//mv
        s5.substring(WWW+1,XXX),//yizhixing
        s5.substring(XXX+1)//sd
        };
        Log.i("datas",datas.toString());
    }
    //数字倒数第3位前加点
    String addDot(String s){
        StringBuffer stringBuffer = new StringBuffer(s);
        int l = s.length()-3;
        if(l<=0){
            l=0;
            return stringBuffer.insert(l,"0.").toString();
        }
        return stringBuffer.insert(l,".").toString();

    }
}
