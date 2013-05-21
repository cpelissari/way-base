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
public class SeqNumColTest {

  public void inteiro() {
    SeqNumCol res = SeqNumCol.integer("", 1, 3, 341);
    assertThat(res, hasToString(equalTo("341")));
  }
  public void inteiro_pad() {
    SeqNumCol res = SeqNumCol.integer("", 1, 3, 34);
    assertThat(res, hasToString(equalTo("034")));
  }

  public void valor_fin() {
    ValorFinanceiroImpl val = new ValorFinanceiroImpl(12345678.12);
    SeqNumCol res = SeqNumCol.valorFin("", 10, 19, val);
    assertThat(res, hasToString(equalTo("1234567812")));
  }
  public void valor_fin_pad() {
    ValorFinanceiroImpl val = new ValorFinanceiroImpl(123.45);
    SeqNumCol res = SeqNumCol.valorFin("", 10, 19, val);
    assertThat(res, hasToString(equalTo("0000012345")));
  }

  public void has_int_value() {
    HasIntValue agencia = new CanHazIntValue();
    SeqNumCol res = SeqNumCol.hasIntValue("", 32, 35, agencia);
    assertThat(res, hasToString(equalTo("0123")));
  }

  private class CanHazIntValue implements HasIntValue {
    @Override
    public int intValue() {
      return 123;
    }
  }

}