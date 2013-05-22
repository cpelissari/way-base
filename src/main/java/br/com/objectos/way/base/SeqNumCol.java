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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;
import br.com.objectos.way.base.ModuloCheckDigit.Computation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Ints;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class SeqNumCol implements Comparable<SeqNumCol> {

  final String id;

  final int start;

  final int end;

  final int size;

  final int[] data;

  SeqNumCol(String id, int start, int end) {
    this.id = id;
    this.start = start;
    this.end = end;
    this.size = end - start + 1;
    Preconditions.checkArgument(size > 0);
    this.data = new int[size];
  }

  static SeqNumCol checkDigit(
      String id, int start, int end, ModuloCheckDigit checker, List<String> checkIds) {
    return new CheckDigitCol(id, start, end, checker, checkIds);
  }
  static SeqNumCol intArray(String id, int start, int end, int[] val) {
    return new SeqNumCol(id, start, end).toIntArray(val);
  }

  static SeqNumCol integer(String id, int start, int end, int val) {
    return new SeqNumCol(id, start, end).toInteger(val);
  }
  static SeqNumCol hasIntValue(String id, int start, int end, HasIntValue val) {
    return new SeqNumCol(id, start, end).toHasIntValue(val);
  }
  static SeqNumCol valorFin(String id, int start, int end, ValorFinanceiro val) {
    return new SeqNumCol(id, start, end).toValorFin(val);
  }

  SeqNumCol toIntArray(int[] val) {
    System.arraycopy(val, 0, this.data, this.data.length - val.length, val.length);
    return this;
  }

  SeqNumCol toInteger(int val) {
    Preconditions.checkArgument(val < Math.pow(10, size));
    int[] data = Base10.toArray(val);
    return toIntArray(data);
  }

  SeqNumCol toHasIntValue(HasIntValue val) {
    int integer = val.intValue();
    return toInteger(integer);
  }

  SeqNumCol toValorFin(ValorFinanceiro val) {
    BigDecimal bd = val.toBigDecimal();
    bd = bd.setScale(2, RoundingMode.HALF_EVEN);
    bd = bd.multiply(BigDecimal.valueOf(100));
    return toInteger(bd.intValue());
  }

  void eval(Map<String, SeqNumCol> colMap) {

  }

  @Override
  public int compareTo(SeqNumCol o) {
    return ComparisonChain.start()
        .compare(start, o.start)
        .result();
  }

  @Override
  public String toString() {
    return Ints.join("", data);
  }

  private static class CheckDigitCol extends SeqNumCol {

    private final ModuloCheckDigit checker;

    private final List<String> checkIds;

    public CheckDigitCol(String id,
                         int start,
                         int end,
                         ModuloCheckDigit checker,
                         List<String> checkIds) {
      super(id, start, end);
      this.checker = checker;
      this.checkIds = checkIds;
    }

    @Override
    void eval(Map<String, SeqNumCol> colMap) {
      Computation computation = checker.start();

      for (String id : checkIds) {
        SeqNumCol col = colMap.get(id);
        col.eval(colMap);
        computation.append(col.data);
      }

      CheckDigit digit = computation.compute();
      toInteger(digit.intValue());
    }

  }

}