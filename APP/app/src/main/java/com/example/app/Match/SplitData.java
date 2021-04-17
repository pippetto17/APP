package com.example.app.Match;

public interface SplitData {

    public static Match[] returnInfoMatch (String stringFromDatabase) {

        //Divide i match
        String[] arrayMatches = stringFromDatabase.split("\\$");

        Match[] xd = new Match[arrayMatches.length];

        String[] arraySingleMatch = new String[arrayMatches.length];

        String[] info = new String[3];

        //Divide i campi di ogni singolo match
        for(int i = 0; i < arrayMatches.length; i++){

            info = arrayMatches[i].split("\\*");

            xd[i] = new Match(info[0], info[1], info[2]);

        }

        return xd;

    }


}
