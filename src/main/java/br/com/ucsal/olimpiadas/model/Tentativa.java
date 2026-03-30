package br.com.ucsal.olimpiadas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * SRP — representa apenas os dados de uma tentativa de prova.
 */
public class Tentativa {
    private long id;
    private long participanteId;
    private long provaId;
    private final List<Resposta> respostas = new ArrayList<>();

    public long getId() {
         return id; 
    }
    public void setId(long id) { 
        this.id = id; 
    }

    public long getParticipanteId() {
        return participanteId;
    }
    public void setParticipanteId(long pid){
        this.participanteId = pid;
    }

    public long getProvaId() {
        return provaId; 
    }
    public void setProvaId(long provaId) {
        this.provaId = provaId;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}
