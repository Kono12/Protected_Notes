package com.example.android.protectednotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends Fragment {
    //TODO: Make Language feature
    //TODO: DO any thing to "rate us"
    SharedPreferences Settings_Modes,LoginDataPref;
    public   Switch SoundMode,sw,screenOn,RemoveAds,Notifications;
    MediaPlayer ClickSound;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ClickSound = MediaPlayer.create(getActivity(),R.raw.click);

        sw =view.findViewById(R.id.DarkMode);
        screenOn = view.findViewById(R.id.ScreenOn_Switch);
        SoundMode=view.findViewById(R.id.Sound_Switch);
        RemoveAds=view.findViewById(R.id.RemoveAds_Switch);
        Notifications=view.findViewById(R.id.Notifications_Switch);

        SettingsSetUp();

        Settings_Modes=getActivity().getSharedPreferences("SettingsModes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Settings_Modes.edit();

        LoginDataPref=getActivity().getSharedPreferences("logindatapred", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2=LoginDataPref.edit();

        //Remove Ads
        RemoveAds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MainActivity.SoundON) ClickSound.start();

                if(b){
                    editor.putBoolean("REMOVEads",true);
                    RemoveAds.setChecked(true);
                    MainActivity.Remove=true;
                }else{
                    editor.putBoolean("REMOVEads",false);
                    RemoveAds.setChecked(false);
                    MainActivity.Remove=false;
                }
                editor.commit();
            }
        });
        //notifications
        Notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MainActivity.SoundON) ClickSound.start();

                if(b){
                    editor.putBoolean("Notification",true);
                    Notifications.setChecked(true);
                    MainActivity.NotificationsOn=true;

                }else{
                    editor.putBoolean("Notification",false);
                    Notifications.setChecked(false);
                    MainActivity.NotificationsOn=false;
                }
                editor.commit();
            }
        });

        //Dark Mode
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MainActivity.SoundON) ClickSound.start();

                if(b){
                    editor.putBoolean("Dark",true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    editor.putBoolean("Dark",false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                editor.commit();
            }
        });

        //Keep screen on
        screenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MainActivity.SoundON)ClickSound.start();

                if(b){
                    editor.putBoolean("ScreenOn",true);
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);}
                else {
                    editor.putBoolean("ScreenOn",false);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                  editor.commit();
            }
        });

        //Sound On/Off
       SoundMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (SoundMode.isChecked()) {
                   editor.putBoolean("Sounds",true);
                   ClickSound.start();
                   SoundMode.setChecked(true);
                   MainActivity.SoundON=true;
               } else {
                   editor.putBoolean("Sounds",false);
                   SoundMode.setChecked(false);
                   MainActivity.SoundON=false;
               }
               editor.commit();
           }
       });

        TextView Language= view.findViewById(R.id.Language_Button);
        Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //TODO:chane language using language button
              ShowLanguages();

            }
        });

        TextView textView=view.findViewById(R.id.resetpassword_Button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder dialogName = new AlertDialog.Builder(getActivity());
                dialogName.setTitle(R.string.Enter_Current_Password);

                final EditText EditTxtName = new EditText(getActivity());
                // change PHONE to any input type
                EditTxtName.setInputType(InputType.TYPE_CLASS_PHONE);
                dialogName.setView(EditTxtName);

                dialogName.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ToReturn = EditTxtName.getText().toString();

                       //TODO: make string password from shared pref
                        SharedPreferences pref=getActivity().getSharedPreferences("logindatapred",Context.MODE_PRIVATE);
                        String password=pref.getString("Password","0000");

                        if(ToReturn.toString().equals(password)){
                            AlertDialog.Builder dialogName = new AlertDialog.Builder(getActivity());
                            dialogName.setTitle(R.string.EnterNewPassword);

                            final EditText EditTxtName = new EditText(getActivity());
                            // change PHONE to any input type
                            EditTxtName.setInputType(InputType.TYPE_CLASS_PHONE);
                            dialogName.setView(EditTxtName);

                            dialogName.setPositiveButton(R.string.ResetPassword, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String ToReturn = EditTxtName.getText().toString();
                                   //TODO update shared pref
                                     editor2.putString("Password",ToReturn);
                                     editor2.commit();
                                    Toast.makeText(getActivity(), R.string.PasswordHasBeenReset, Toast.LENGTH_SHORT).show();
                                    dialogInterface.cancel();
                                }
                            });

                            dialogName.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            dialogName.show();


                        }else{
                            Toast.makeText(getActivity(),getString(R.string.wrongPass), Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.cancel();
                    }
                });

                dialogName.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogName.show();
            }
        });
    }

    private void ShowLanguages() {
        final String[] Languages ={"English" ,"??????????????"};

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.selectLang);
        mBuilder.setSingleChoiceItems(Languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==1){
                    setLocal("ar");
                    getActivity().recreate();
                }
                else if (i==0){
                    setLocal("en");
                    getActivity().recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void setLocal(String s){
        Locale locale =new Locale(s);
        Locale.setDefault(locale);

        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getActivity().getSharedPreferences("Language",Context.MODE_PRIVATE).edit();
        editor.putString("AppLang",s);
        editor.commit();
    }




    public  void SettingsSetUp(){
        //dark mode
        if(MainActivity.DarkMode) {
            sw.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            sw.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        // sound on
        if(MainActivity.Sounds) {
            SoundMode.setChecked(true);
            MainActivity.SoundON=true;
        } else{
            SoundMode.setChecked(false);
            MainActivity.SoundON=false;
        }
        //screen on
        if(MainActivity.Screen){
            screenOn.setChecked(true);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        } else{
            screenOn.setChecked(false);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //Remove Ads
        if(MainActivity.removeAds) {
            RemoveAds.setChecked(true);
            MainActivity.Remove=true;
        }
        else{
            RemoveAds.setChecked(false);
            MainActivity.Remove=false;
        }
       //Notifications
        if(MainActivity.notifications) {
            Notifications.setChecked(true);
        }else{
            Notifications.setChecked(false);
        }

    }

}