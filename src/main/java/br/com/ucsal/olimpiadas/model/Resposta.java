package br.com.ucsal.olimpiadas.model;

/**
 * SRP — representa apenas os dados de uma resposta dada pelo participante.
 */
public class Resposta {
    private long questaoId;
    private char alternativaMarcada;
    private boolean correta;

    public long getQuestaoId() {
         return questaoId;
    }
    public void setQuestaoId(long questaoId) {
         this.questaoId = questaoId;
    }

    public char getAlternativaMarcada() {
         return alternativaMarcada; 
    }
    public void setAlternativaMarcada(char c) {
         this.alternativaMarcada = c;
    }

    public boolean isCorreta() {
         return correta;
    }
    public void setCorreta(boolean correta) { 
        this.correta = correta;
    }
}
