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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDePorExtenso {

  @DataProvider
  public Object[][] inteiros() {
    return new Object[][] {

        { 0, "zero" },
        { 1, "um" },
        { 2, "dois" },
        { 3, "três" },
        { 4, "quatro" },
        { 5, "cinco" },
        { 6, "seis" },
        { 7, "sete" },
        { 8, "oito" },
        { 9, "nove" },

        { 10, "dez" },
        { 11, "onze" },
        { 12, "doze" },
        { 13, "treze" },
        { 14, "quatorze" },
        { 15, "quinze" },
        { 16, "dezesseis" },
        { 17, "dezessete" },
        { 18, "dezoito" },
        { 19, "dezenove" },
        { 20, "vinte" },

        { 30, "trinta" },
        { 31, "trinta e um" },
        { 32, "trinta e dois" },
        { 33, "trinta e três" },
        { 34, "trinta e quatro" },
        { 35, "trinta e cinco" },
        { 36, "trinta e seis" },
        { 37, "trinta e sete" },
        { 38, "trinta e oito" },
        { 39, "trinta e nove" },

        { 100, "cem" },
        { 101, "cento e um" },
        { 111, "cento e onze" },
        { 222, "duzentos e vinte e dois" },
        { 333, "trezentos e trinta e três" },

        { 1000, "um mil" },
        { 1001, "um mil e um" },
        { 1002, "um mil e dois" },
        { 1003, "um mil e três" },
        { 1567, "um mil e quinhentos e sessenta e sete" },

        { 2000, "dois mil" },
        { 3001, "três mil e um" },
        { 7893, "sete mil e oitocentos e noventa e três" },

        { 1000000, "um milhão" },
        { 2000000, "dois milhões" },
        { 9234567, "nove milhões e duzentos e trinta e quatro mil e quinhentos e sessenta e sete" },
    };
  }

  @Test(dataProvider = "inteiros")
  public void por_extenso_inteiro(int n, String esperado) {
    String res = PorExtenso.inteiro().toString(n);
    assertThat(res, equalTo(esperado));
  }

  public void um_mil() {
    String res = PorExtenso.inteiro().toString(1000);
    assertThat(res, equalTo("um mil"));
  }

  private final PorExtenso reais = PorExtenso
      .novoDecimal()
      .comCasas(2)
      .unidades("real", "reais")
      .e("centavo", "centavos");

  @DataProvider
  public Object[][] reais() {
    return new Object[][] {
        { 1, "um real" },
        { 2, "dois reais" },
        { 10, "dez reais" },
        { 11.05, "onze reais e cinco centavos" },
        { 100.01, "cem reais e um centavo" },
        { 300.99, "trezentos reais e noventa e nove centavos" },
        { 1000, "um mil reais" },
        { 10000, "dez mil reais" },
        { 10001.23, "dez mil e um reais e vinte e três centavos" }
    };
  }

  @Test(dataProvider = "reais")
  public void decimal_real(double val, String esperado) {
    String res = reais.toString(val);
    assertThat(res, equalTo(esperado));
  }

  @DataProvider
  public Object[][] reais_negativos() {
    return new Object[][] {
        { -1, "um real" },
        { -2, "dois reais" },
        { -10, "dez reais" },
        { -11.05, "onze reais e cinco centavos" },
        { -100.01, "cem reais e um centavo" },
        { -300.99, "trezentos reais e noventa e nove centavos" },
        { -1000, "um mil reais" },
        { -10000, "dez mil reais" },
        { -10001.23, "dez mil e um reais e vinte e três centavos" }
    };
  }

  @Test(dataProvider = "reais_negativos")
  public void decimal_real_negativo(double val, String esperado) {
    String res = reais.toString(val);
    assertThat(res, equalTo(esperado));
  }

  public void explode100() {
    PorExtensoInteiro inteiro = new PorExtensoInteiro();

    assertThat(inteiro.explodir(1), equalTo(new int[] { 1 }));
    assertThat(inteiro.explodir(11), equalTo(new int[] { 11 }));
    assertThat(inteiro.explodir(999), equalTo(new int[] { 999 }));

    assertThat(inteiro.explodir(1001), equalTo(new int[] { 1, 1 }));
    assertThat(inteiro.explodir(1011), equalTo(new int[] { 1, 11 }));
    assertThat(inteiro.explodir(1999), equalTo(new int[] { 1, 999 }));

    assertThat(inteiro.explodir(12001), equalTo(new int[] { 12, 1 }));
    assertThat(inteiro.explodir(13011), equalTo(new int[] { 13, 11 }));
    assertThat(inteiro.explodir(33999), equalTo(new int[] { 33, 999 }));
  }

}