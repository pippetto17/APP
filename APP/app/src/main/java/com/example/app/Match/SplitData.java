package com.example.app.Match;

public interface SplitData {

    public static MatchModel[] returnInfoMatch (String stringFromDatabase) {

        //Divide i match con il target $
        String[] arrayMatches = stringFromDatabase.split("\\$");

        //Array di tutti i match gia splittati
        MatchModel[] arrayMatchesWithInfo = new MatchModel[arrayMatches.length];

        //Array di appoggio per la separazione delle info
        String[] informazioniMatch;

        //Divide i campi di ogni singolo match
        for(int i = 0; i < arrayMatches.length; i++){

            //Divide le info del match
            informazioniMatch = arrayMatches[i].split("\\*");

            //Istanzia un nuovo match e lo aggiunge all'array
            arrayMatchesWithInfo[i] = new MatchModel(informazioniMatch[0]
                    , informazioniMatch[1]
                    , informazioniMatch[2]
                    , informazioniMatch[3]
                    , informazioniMatch[4]
                    , informazioniMatch[5]
                    , informazioniMatch[6]
                    , informazioniMatch[7]
                    , informazioniMatch[8]
            );

        }

        //Restituisce l'array dei match con tutte le loro info
        return arrayMatchesWithInfo;
    }


}
