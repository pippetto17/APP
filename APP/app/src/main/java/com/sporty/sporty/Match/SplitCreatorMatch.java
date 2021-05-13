package com.sporty.sporty.Match;

public interface SplitCreatorMatch {

    public static CreatorMatchModel[] returnCreatorMatch(String stringFromDatabase) {

        //Divide i match con il target $
        String[] arrayMatches = stringFromDatabase.split("\\$");

        //Array di tutti i match gia splittati
        CreatorMatchModel[] arrayMatchesWithInfo = new CreatorMatchModel[arrayMatches.length];

        //Array di appoggio per la separazione delle info
        String[] informazioniMatch;

        //Divide i campi di ogni singolo match
        for (int i = 0; i < arrayMatches.length; i++) {

            //Divide le info del match
            informazioniMatch = arrayMatches[i].split("\\*");

            //Istanzia un nuovo match e lo aggiunge all'array
            arrayMatchesWithInfo[i] = new CreatorMatchModel(informazioniMatch[0]
                    , informazioniMatch[1]
                    , informazioniMatch[2]
                    , informazioniMatch[3]
                    , informazioniMatch[4]
            );

        }

        //Restituisce l'array dei match con tutte le loro info
        return arrayMatchesWithInfo;
    }


}