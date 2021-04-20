package com.example.app.Match;

public class Match {

    public String nomeCognomeCreatore;
    public String giorno;
    public String fasciaOraria;
    public String citta;
    public String modalita;
    public String emailCreatore;
    public String info;
    public String eta;


    public Match(){}

    public Match(String nomeCognomeCreatore, String giorno, String fasciaOraria, String citta, String modalita, String emailCreatore, String info, String eta) {
        this.nomeCognomeCreatore = nomeCognomeCreatore;
        this.giorno = giorno;
        this.fasciaOraria = fasciaOraria;
        this.citta = citta;
        this.modalita = modalita;
        this.emailCreatore = emailCreatore;
        this.info = info;
        this.eta = eta;
    }
}
