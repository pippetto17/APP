package com.example.app.Match;

public interface SplitData {

    public static Match[] returnInfoMatch (String stringFromDatabase) {

        //Divide i match con il target $
        String[] arrayMatches = stringFromDatabase.split("\\$");

        //Array di tutti i match gia splittati
        Match[] arrayMatchesWithInfo = new Match[arrayMatches.length];

        //Array di appoggio per la separazione delle info
        String[] infos;

        //Divide i campi di ogni singolo match
        for(int i = 0; i < arrayMatches.length; i++){

            //Divide le info del match
            infos = arrayMatches[i].split("\\*");

            //Istanzia un nuovo match e lo aggiunge all'array
            arrayMatchesWithInfo[i] = new Match(infos[0], infos[1], infos[2]);

        }

        //Restituisce l'array dei match con tutte le loro info
        return arrayMatchesWithInfo;

    }


}
