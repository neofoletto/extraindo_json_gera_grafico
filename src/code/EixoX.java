/*
 *                     GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                             Preamble
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 */

package code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author neo
 * @create 08/08/2019
 */
public class EixoX {
  private String nome = "";
  private List<Double> dado = new ArrayList<Double>();

  /**
   * Construtor vazio
   */
  public EixoX() {

  }

  /**
   * Retorna uma String contendo o nome do eixo.
   *
   * @return String
   */
  public String getNome() {
    return nome;
  }

  /**
   *
   * @param nome; String => Recebe nome do eixo;
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Retorna uma List<Double> com os dados do eixo.
   *
   * @return List<Double>.
   */
  public List<Double> getDado() {
    return dado;
  }

  /**
   * Recebe dados Double e armazena-o em um List<Double>, assim compondo o eixo x.
   *
   * @param dado: Double => Recebe dado Double.
   */
  public void setDado(double dado) {
    this.dado.add(dado);
  }
}
