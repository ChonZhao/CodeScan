package com.hnhongxiang.code.codescan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.history.HistoryActivity;
import com.google.zxing.client.android.history.HistoryItem;
import com.google.zxing.client.android.history.HistoryManager;

import java.text.DateFormat;


public class scanCode extends AppCompatActivity {

    private TextView mTextMessage, pnameTV, dateTV, idTV, d0TV, d5TV, d10TV, d50TV, d90TV, d95TV, d100TV, mvTV, sdTV, beizhuTV, dengjiTV, yizhixingTV;
    private TableLayout tableLayout;
    private int ActivityRequestCode_0 = 0;
    private static final int HISTORY_REQUEST_CODE = 0x0000bacc;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    tableLayout.setVisibility(View.INVISIBLE);
                    beizhuTV.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_qr:
                    Intent openCameraIntent = new Intent(scanCode.this, CaptureActivity.class);
                    startActivityForResult(openCameraIntent, ActivityRequestCode_0);
                    tableLayout.setVisibility(View.INVISIBLE);
                    beizhuTV.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent1 = new Intent();
                    intent1.setClass(scanCode.this, HistoryActivity.class);
                    startActivityForResult(intent1, HISTORY_REQUEST_CODE);
                    tableLayout.setVisibility(View.INVISIBLE);
                    beizhuTV.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ActivityRequestCode_0 && data != null) {
            Bundle mBundle = data.getExtras();
            String sResult = mBundle.getString("result");
            resultShow(sResult);
        }else if(resultCode == RESULT_OK && requestCode == HISTORY_REQUEST_CODE && data != null) {
            int itemNumber = data.getIntExtra(Intents.History.ITEM_NUMBER, -1);
            if (itemNumber >= 0) {
                HistoryManager historyManager = new HistoryManager(this);
                HistoryItem historyItem = historyManager.buildHistoryItem(itemNumber);
                Result re =historyItem.getResult();
                String his = re.getText();
                resultShow(his);
                DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                mTextMessage.setText(getString(R.string.saomashijian)+formatter.format(re.getTimestamp()));
            }
        }else{
                noResultToast();
            }
        }


    private void resultShow(String sResult) {
        if (sResult == "") {
            noResultToast();
            return;
        }
        // Toast.makeText(this, sResult, Toast.LENGTH_LONG).show();
        needDeCodeString nds = new needDeCodeString(this, sResult);
        mTextMessage.setText(R.string.jieguoruxia);
        pnameTV.setText(nds.getPname());
        dateTV.setText(nds.getDate());
        idTV.setText(nds.getid());
        dengjiTV.setText(nds.getDengji());

        d0TV.setText(nds.getData(0));
        d5TV.setText(nds.getData(1));
        d10TV.setText(nds.getData(2));
        d50TV.setText(nds.getData(3));
        d90TV.setText(nds.getData(4));
        d95TV.setText(nds.getData(5));
        d100TV.setText(nds.getData(6));
        mvTV.setText(nds.getData(7));
        yizhixingTV.setText(nds.getData(8));
        sdTV.setText(nds.getData(9));

        beizhuTV.setText(getString(R.string.beizhu) + nds.getBeizhu());
        beizhuTV.setVisibility(View.VISIBLE);
        tableLayout.setVisibility(View.VISIBLE);

    }


    void noResultToast(){
        Toast.makeText(this,getString(R.string.NoScanResult) , Toast.LENGTH_LONG).show();
        tableLayout.setVisibility(View.INVISIBLE);
        // ScanResult 为 获取到的字符串
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,R.string.CheckpermissionPls,Toast.LENGTH_LONG);
            this.finish();
        }
        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        //String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        if(usersID.isUser(deviceid)){
            Toast.makeText(this,"Sorry!" +R.string.title_notifications,Toast.LENGTH_LONG);
            this.finish();
        };


        setContentView(R.layout.activity_scan_code);

        mTextMessage = findViewById(R.id.message);
        pnameTV = findViewById(R.id.pname);
        dateTV = findViewById(R.id.date2);
        dengjiTV = findViewById(R.id.dengji2);
        idTV = findViewById(R.id.id2);
        d0TV = findViewById(R.id.d02);
        d5TV = findViewById(R.id.d52);
        d10TV = findViewById(R.id.d102);
        d50TV = findViewById(R.id.d502);
        d90TV = findViewById(R.id.d902);
        d95TV = findViewById(R.id.d952);
        d100TV = findViewById(R.id.d1002);
        mvTV = findViewById(R.id.mv2);
        sdTV = findViewById(R.id.sd2);
        yizhixingTV= findViewById(R.id.yizhixing2);
        beizhuTV = findViewById(R.id.beizhu2);
        tableLayout =findViewById(R.id.resultTable);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
