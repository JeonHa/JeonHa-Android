package com.song2.jeonha.UI.Main.QRcode;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.song2.jeonha.R;

public class QRcodeActivity extends AppCompatActivity implements  DecoratedBarcodeView.TorchListener {

    private CaptureManager manager;
    private boolean isFlashOn = false;// 플래시가 켜져 있는지
    private ImageView iv_qrcode_back;
    private TextView btFlash;
    private DecoratedBarcodeView barcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        iv_qrcode_back =findViewById(R.id.iv_qrcode_back);
        barcodeView = findViewById(R.id.db_qr);

        manager = new CaptureManager(this,barcodeView);
        manager.initializeFromIntent(getIntent(),savedInstanceState);
        manager.decode();

        btFlash = findViewById(R.id.bt_flash);

        iv_qrcode_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });


        btFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn){
                    barcodeView.setTorchOff();
                }else{
                    barcodeView.setTorchOn();
                }
            }
        });
    }

    @Override
    public void onTorchOn() {

        btFlash.setText("플래시끄기");
        isFlashOn = true;
    }

    @Override
    public void onTorchOff() {
        btFlash.setText("플래시켜기");
        isFlashOn = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        manager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        manager.onSaveInstanceState(outState);
    }

}

