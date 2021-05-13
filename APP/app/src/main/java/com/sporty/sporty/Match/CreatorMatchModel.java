package com.sporty.sporty.Match;

public class CreatorMatchModel {

    public String giorno;
    public String fasciaOraria;
    public String citta;
    public String modalita;
    public String sport;


    public CreatorMatchModel() {
    }

    public CreatorMatchModel(String giorno, String fasciaOraria, String citta, String modalita, String sport) {
        this.giorno = giorno;
        this.fasciaOraria = fasciaOraria;
        this.citta = citta;
        this.modalita = modalita;
        this.sport = sport;
    }
}