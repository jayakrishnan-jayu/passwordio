package com.example.passwordio.fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.passwordio.models.PasswordGeneratorSettingsPreference;
import com.example.passwordio.R;
import com.example.passwordio.Settings;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratorFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    Settings settings;
    PasswordGeneratorSettingsPreference pref;

    public GeneratorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneratorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneratorFragment newInstance(String param1, String param2) {
        GeneratorFragment fragment = new GeneratorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        settings = new Settings(getContext());

        pref = settings.getGeneratorSettingsPreference();

        if (!pref.special && !pref.small && !pref.number && !pref.capital) pref.small = true;
        if (pref.passwordLength > 120 || pref.passwordLength < 5) pref.passwordLength = 8;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchCapital = getView().findViewById(R.id.genAZSwitch);
        switchSmall = getView().findViewById(R.id.genazSwitch);
        switchNumber = getView().findViewById(R.id.gen09Switch);
        switchSpecial = getView().findViewById(R.id.genSpecialSwitch);

        switchSpecial.setChecked(pref.special);
        switchNumber.setChecked(pref.number);
        switchCapital.setChecked(pref.capital);
        switchSmall.setChecked(pref.small);

        switchCapital.setOnCheckedChangeListener(this);
        switchSmall.setOnCheckedChangeListener(this);
        switchNumber.setOnCheckedChangeListener(this);
        switchSpecial.setOnCheckedChangeListener(this);

//        specialDecBtn = getView().findViewById(R.id.genSpecialDecrement);
//        specialIncBtn = getView().findViewById(R.id.genSpecialIncrement);
//        numberDecBtn = getView().findViewById(R.id.genNumberDecrement);
//        numberIncBtn = getView().findViewById(R.id.genNumberIncrement);

//        numberValue = getView().findViewById(R.id.genSpecialValue);
//        specialValue = getView().findViewById(R.id.genSpecialValue);

        seekbarValue = getView().findViewById(R.id.genSeekValue);

        passLengthSeekBar = getView().findViewById(R.id.genPasswordLengthSeekBar);

        copyImageView = getView().findViewById(R.id.genCopy);
        refreshImageView = getView().findViewById(R.id.genRefresh);

        copyImageView.setOnClickListener(this);
        refreshImageView.setOnClickListener(this);

        resultTextView = getView().findViewById(R.id.genPasswordTextView);

        passLengthSeekBar.setMin(5);
        passLengthSeekBar.setMax(120);
        passLengthSeekBar.setProgress(pref.passwordLength);

        seekbarValue.setText(String.valueOf(passLengthSeekBar.getProgress()));
        passLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarValue.setText(String.valueOf(i));
                setRandomString();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setRandomString();
                pref.passwordLength = seekBar.getProgress();
                settings.setGeneratorSettingsPrefernece(pref);
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
        for (int i = 0; i < n; ++i)
            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
        resultTextView.setText(sb.toString());
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.genazSwitch:
            case R.id.genAZSwitch:
            case R.id.gen09Switch:
            case R.id.genSpecialSwitch:
                if (!switchSpecial.isChecked() && !switchNumber.isChecked() && !switchCapital.isChecked() && !switchSmall.isChecked()) {
                    switchSmall.setChecked(true);
                }
                pref.small = switchSmall.isChecked();
                pref.capital = switchCapital.isChecked();
                pref.number = switchNumber.isChecked();
                pref.special = switchSpecial.isChecked();
                settings.setGeneratorSettingsPrefernece(pref);
        }
        setRandomString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.genRefresh:
                setRandomString();
                break;
            case R.id.genCopy:
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password Copied!", resultTextView.getText());
                clipboard.setPrimaryClip(clip);
        }
    }

}