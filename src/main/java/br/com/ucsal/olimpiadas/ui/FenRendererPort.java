package br.com.ucsal.olimpiadas.ui;

/**
 * ISP — interface mínima para renderização de tabuleiro; clientes (ConsoleUI) 
 *       dependem apenas deste contrato.
 * DIP — ConsoleUI depende da abstração, nunca da implementação concreta.
 */
public interface FenRendererPort {
    void renderizar(String fen);
}
