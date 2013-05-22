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
public class SeqNumTest {

  public void inteiro() {
    SeqNum res = SeqNum.builder()

        .col("Código moeda")
        .at(4, 4).integer(9)

        .col("Código banco")
        .at(1, 3).integer(341)

        .build();

    assertThat(res, hasToString(equalTo("3419")));
  }

  public void dac() {
    ModuloCheckDigit checker = ModuloCheckDigit.newMod(11)
        .multiplierRangeOf(1, 2)
        .whenResult(0, 10, 11).replaceWith(1)
        .build();

    SeqNum res = SeqNum.builder()

        .col("whatever")
        .at(1, 1).integer(5)

        .col("dac")
        .at(2, 2).checkDigit(checker).of("whatever", "xyz")

        .col("dummy")
        .at(3, 3).integer(9)

        .col("xyz")
        .at(4, 4).integer(1)

        .build();

    assertThat(res, hasToString(equalTo("5191")));
  }

}