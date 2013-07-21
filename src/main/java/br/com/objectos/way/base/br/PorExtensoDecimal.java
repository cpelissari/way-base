/*
 * Copyright 2013 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.way.base.br;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Joiner;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PorExtensoDecimal extends PorExtenso {

  private final int decimais;
  private final String[] unidadeInteira;
  private final String[] unidadeDecimal;

  PorExtensoDecimal(int decimais, String[] unidadeInteira, String[] unidadeDecimal) {
    this.decimais = decimais;
    this.unidadeInteira = unidadeInteira;
    this.unidadeDecimal = unidadeDecimal;
  }

  @Override
  public String toString(int numero) {
    return toString((double) numero);
  }

  @Override
  public String toString(double numero) {
    numero = Math.abs(numero);

    List<String> list = newArrayList();

    int parteInteira = (int) numero;
    String inteira = INTEIRO.toString(parteInteira);
    inteira = inteira + " " + unidade(unidadeInteira, parteInteira);
    list.add(inteira);

    int parteDecimal = (int) Math.round((numero % 1) * Math.pow(10, decimais));
    if (parteDecimal > 0) {
      String decimal = INTEIRO.toString(parteDecimal);
      decimal = decimal + " " + unidade(unidadeDecimal, parteDecimal);
      list.add(decimal);
    }

    return Joiner.on(" e ").join(list);
  }

  private String unidade(String[] unidades, int valor) {
    return valor == 1 ? unidades[0] : unidades[1];
  }

}