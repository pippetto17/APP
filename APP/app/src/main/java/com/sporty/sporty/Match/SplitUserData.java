package com.sporty.sporty.Match;

public interface SplitUserData {

    public static String[] returnUserData(String stringFromDatabase) {

        String[] userInfo = stringFromDatabase.split("\\*");

        return userInfo;
    }


}
