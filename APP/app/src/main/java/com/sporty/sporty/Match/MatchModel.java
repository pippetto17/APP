package com.sporty.sporty.Match;

public class MatchModel {

    public String nomeCognomeCreatore;
    public String giorno;
    public String fasciaOraria;
    public String citta;
    public String modalita;
    public String emailCreatore;
    public String info;
    public String eta;
    public String idMatch;
    public String kindSport;


    public MatchModel() {
    }

    public MatchModel(String nomeCognomeCreatore, String giorno, String fasciaOraria, String citta, String modalita, String emailCreatore, String info, String eta, String idMatch, String kindSport) {
        this.nomeCognomeCreatore = nomeCognomeCreatore;
        this.giorno = giorno;
        this.fasciaOraria = fasciaOraria;
        this.citta = citta;
        this.modalita = modalita;
        this.emailCreatore = emailCreatore;
        this.info = info;
        this.eta = eta;
        this.idMatch = idMatch;
        this.kindSport = kindSport;
    }
}
