package com.example.app.Match;

public interface SplitData {

    public static Match[] returnInfoMatch (String stringFromDatabase) {

        //Divide i match con il target $
        String[] arrayMatches = stringFromDatabase.split("\\$");

        //Array di tutti i match gia splittati
        Match[] arrayMatchesWithInfo = new Match[arrayMatches.length];

        //Array di appoggio per la separazione delle info
        String[] info;

        //Divide i campi di ogni singolo match
        for(int i = 0; i < arrayMatches.length; i++){

            //Divide le info del match
            info = arrayMatches[i].split("\\*");

            //Istanzia un nuovo match e lo aggiungfe all'array
            arrayMatchesWithInfo[i] = new Match(info[0], info[1], info[2]);

        }

        //Restituisce l'array dei match con tutte le loro info
        return arrayMatchesWithInfo;

    }


}
