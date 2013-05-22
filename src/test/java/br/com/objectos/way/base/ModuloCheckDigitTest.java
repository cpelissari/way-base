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
package br.com.objectos.way.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class ModuloCheckDigitTest {

  public void boleto() {
    ModuloCheckDigit checker = ModuloCheckDigit.newMod(11)
        .multiplierRangeOf(2, 9)
        .whenResult(0, 10, 11).replaceWith(1)
        .build();

    CheckDigit res = checker.start()
        .append(new int[] { 3, 4, 1 }) // código do banco
        .append(new int[] { 9 }) // código da moeda
        .append(new int[] { 1, 6, 6, 7 }) // fator de vencimento
        .append(new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 4, 5 }) // valor
        .append(new int[] { 1, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8 }) // Carteira
        .append(new int[] { 0, 0, 5, 7, 1, 2, 3, 4, 5, 7 }) // Ag.
        .append(new int[] { 0, 0, 0 }) // livres
        .compute();

    assertThat(res, hasToString(equalTo("3419166700000123451101234567880057123457000-6")));
  }

  public void boleto_replace_value() {
    ModuloCheckDigit checker = ModuloCheckDigit.newMod(11)
        .multiplierRangeOf(1, 2)
        .whenResult(0, 10, 11).replaceWith(1)
        .build();

    CheckDigit res = checker.start()
        .append(new int[] { 5, 1 }) // res == 11
        .compute();

    assertThat(res, hasToString(equalTo("51-1")));
  }

  public void linha_digitavel() {
    ModuloCheckDigit checker = ModuloCheckDigit.newMod(10)
        .multipliers(2, 1)
        .digits()
        .whenResult(10).replaceWith(0)
        .build();

    CheckDigit res = checker.start()
        .append(new int[] { 7, 1, 2, 3, 4, 5, 7, 0, 0, 0 })
        .compute();

    assertThat(res, hasToString(equalTo("7123457000-1")));
  }

}