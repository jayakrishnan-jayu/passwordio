package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class PasswordGeneratorActivity extends AppCompatActivity  implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String numbers = "0123456789";
    private static final String smallCharacters = "qwertyuiopasdfghjklzxcvbnm";
    private static final String capitalCharacters = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String specialCharacters = "!@#$%^&*";
    private static final Random random = new Random();
    private static StringBuilder sb;

    TextView resultTextView, seekbarValue, numberValue, specialValue;
    Switch switchCapital, switchSmall, switchNumber, switchSpecial;
    ImageView copyImageView, refreshImageView;
    SeekBar passLengthSeekBar;
    Button specialIncBtn, specialDecBtn, numberIncBtn, numberDecBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        switchCapital = findViewById(R.id.genAZSwitch);
        switchSmall = findViewById(R.id.genazSwitch);
        switchNumber = findViewById(R.id.gen09Switch);
        switchSpecial = findViewById(R.id.genSpecialSwitch);

        switchSmall.setChecked(true);
        switchCapital.setOnCheckedChangeListener(this);
        switchSmall.setOnCheckedChangeListener(this);
        switchNumber.setOnCheckedChangeListener(this);
        switchSpecial.setOnCheckedChangeListener(this);

//        specialDecBtn = findViewById(R.id.genSpecialDecrement);
//        specialIncBtn = findViewById(R.id.genSpecialIncrement);
//        numberDecBtn = findViewById(R.id.genNumberDecrement);
//        numberIncBtn = findViewById(R.id.genNumberIncrement);

//        numberValue = findViewById(R.id.genSpecialValue);
//        specialValue = findViewById(R.id.genSpecialValue);

        seekbarValue = findViewById(R.id.genSeekValue);

        passLengthSeekBar = findViewById(R.id.genPasswordLengthSeekBar);

        copyImageView = findViewById(R.id.genCopy);
        refreshImageView = findViewById(R.id.genRefresh);

        copyImageView.setOnClickListener(this);
        refreshImageView.setOnClickListener(this);

        resultTextView = findViewById(R.id.genPasswordTextView);

        passLengthSeekBar.setMin(5);
        passLengthSeekBar.setMax(120);
        passLengthSeekBar.setProgress(16);
        seekbarValue.setText(String.valueOf(passLengthSeekBar.getProgress()));
        passLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setRandomString();
            }
        });
        setRandomString();

    }

    private void setRandomString() {
        String allowedCharacters = "";
        int n = passLengthSeekBar.getProgress();
        if (switchSmall.isChecked()) allowedCharacters += smallCharacters;
        if (switchCapital.isChecked()) allowedCharacters += capitalCharacters;
        if (switchNumber.isChecked()) allowedCharacters += numbers;
        if (switchSpecial.isChecked()) allowedCharacters += specialCharacters;
        sb = new StringBuilder(n);
        for(int i=0;i<n;++i)
            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
        resultTextView.setText(sb.toString());
    }




    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()) {
            case R.id.genazSwitch:
            case R.id.genAZSwitch:
            case R.id.gen09Switch:
            case R.id.genSpecialSwitch:
                if (!switchSpecial.isChecked() && !switchNumber.isChecked() && !switchCapital.isChecked() && !switchSmall.isChecked()){
                    switchSmall.setChecked(true);
                }
        }
        setRandomString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.genRefresh:
                setRandomString();
                break;
            case R.id.genCopy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password Copied!", resultTextView.getText());
                clipboard.setPrimaryClip(clip);
        }
    }
}