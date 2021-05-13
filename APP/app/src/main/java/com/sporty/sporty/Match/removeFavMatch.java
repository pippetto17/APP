package com.sporty.sporty.Match;

public interface removeFavMatch {

    public static String returnNewSavedMatch(String oldFavouriteMatch, String IdMatch) {

        //Stringa dei nuovi match salvati
        String newFavouriteMatch = oldFavouriteMatch;

        //Toglie il primo elemento della stringa "$"
        StringBuffer sb = new StringBuffer(oldFavouriteMatch);
        sb.deleteCharAt(0);
        newFavouriteMatch = new String(sb);

        //Rimuove l'id del match passato come parametro
        String deleteSubString = IdMatch;
        deleteSubString = deleteSubString + "$";
        newFavouriteMatch = oldFavouriteMatch.replace(deleteSubString, "");

        return newFavouriteMatch;
    }
}
