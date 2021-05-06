package com.example.app.Match;

public interface SplitSavedMatch {

    /**
     * Prende la stringa dal database, elimina il primo carattere $ e splitta tutti i macth
     * dividendoli per il simbolo $
     * @param stringFromDatabase
     * @return
     */
    public static String[] returnSavedMatches (String stringFromDatabase) {

        //Divide i match con il target $
        StringBuffer sb= new StringBuffer(stringFromDatabase);
        sb.deleteCharAt(0);

        String[] arrayMatches = sb.toString().split("\\$");

        return arrayMatches;
    }
}
