package com.healthmeter.coronameter.javaop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticVar {

    public static List<List<String>> rootCountry = new ArrayList<>();
    public static List<List<String>> rootAge = new ArrayList<>();
    public static List<List<String>> rootGender = new ArrayList<>();
    public static List<List<String>> rootPreCon = new ArrayList<>();
    public static List<String> countriesList=new ArrayList<>();
    public static String selectedCountry="Empty";
    public static String urlWHO;
    public static String urlJHU;
    public static boolean isNetworkConnected;
    public static int initSpinnerCase=0;
    public static int fromLangSelected =0;
    public static String languageSelected="Empty";


    public static List<String> languageList=new ArrayList<>(Arrays.asList("Use Phone Language","English","Türkçe" ,"Deutsche","Italiano","Español","Français"));

}