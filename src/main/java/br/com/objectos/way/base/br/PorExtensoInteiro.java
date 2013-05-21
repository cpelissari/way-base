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

import br.com.objectos.way.base.Base10;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PorExtensoInteiro extends PorExtenso {

  private final String[] NUMEROS = {
    null, "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove",
    "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"
  };

  private final String[][] DEZENAS = {
    { null, "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove" },
    { "dez", "dez", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa" }
  };

  private final String[] CENTENAS = {
    null, "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos",
  };

  private final String[] MILHAR = {
    null, "mil", "milhão", "bilhão", "trilhão"
  };
  private final String[] MILHARES = {
    null, "mil", "milhões", "bilhões", "trilhões"
  };

  PorExtensoInteiro() {
  }

  @Override
  public String toString(int numero) {
    if (numero == 0) {
      return "zero";
    }

    List<String> list = newArrayList();

    int[] milhares = explodir(numero);
    for (int i = 0; i < milhares.length; i++) {
      int milhar = milhares[i];
      String b = milharPorExtenso(milhar);

      if (!Strings.isNullOrEmpty(b)) {
        String[] array = milhar == 1 ? MILHAR : MILHARES;
        String mil = array[milhares.length - i - 1];
        list.add(Joiner.on(" ").skipNulls().join(b, mil));
      }
    }

    return Joiner.on(" e ").skipNulls().join(list);
  }

  @Override
  public String toString(double numero) {
    return toString((int) numero);
  }

  @VisibleForTesting
  int[] explodir(int val) {
    int size = (int) Math.abs(Math.log10(val));
    size = size / 3 + 1;
    int[] data = new int[size];

    int i = size;
    int temp = val;
    do {
      data[--i] = temp % 1000;
      temp = temp / 1000;
    } while (temp > 0);

    return data;
  }

  private String milharPorExtenso(int val) {
    if (val == 100) {
      return "cem";
    }

    List<String> list = newArrayList();

    int centena = val / 100;
    list.add(CENTENAS[centena]);

    int dezena = val % 100;
    list.add(dezenaPorExtenso(dezena));

    return Joiner.on(" e ").skipNulls().join(list);
  }

  private String dezenaPorExtenso(int dezena) {
    if (dezena < 20) {
      return NUMEROS[dezena];
    }

    List<String> list = newArrayList();

    int[] algarismos = Base10.toArray(dezena);
    for (int i = 0; i < algarismos.length; i++) {
      int pow = algarismos.length - i - 1;
      int algarismo = algarismos[i];
      String b = DEZENAS[pow][algarismo];
      list.add(b);
    }

    return Joiner.on(" e ").skipNulls().join(list);
  }

}