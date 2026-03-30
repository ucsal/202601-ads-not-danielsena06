package br.com.ucsal.olimpiadas.repository;

/**
 * ISP — interface focada: define apenas um método para gerar ID.
 * DIP — os repositórios dependem dessa abstração, não de implementações concretas.
 */
public interface IdGenerator {
    long proximo();
}
