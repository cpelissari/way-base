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

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class SeqNum {

  private final List<SeqNumCol> cols;

  private SeqNum(Iterable<SeqNumCol> cols) {
    this.cols = ImmutableList.copyOf(cols);
  }

  public static Builder builder() {
    return new Builder();
  }

  public SeqNum minus(final String id) {
    Iterable<SeqNumCol> minus = Iterables.filter(cols, new Predicate<SeqNumCol>() {
      @Override
      public boolean apply(SeqNumCol input) {
        return !id.equals(input.id);
      }
    });
    return new SeqNum(minus);
  }

  public int[] toArray() {
    int[] res = new int[] {};
    for (SeqNumCol col : cols) {
      res = Ints.concat(res, col.data);
    }
    return res;
  }

  @Override
  public String toString() {
    return Joiner.on("").join(cols);
  }

  public static class Builder implements br.com.objectos.comuns.base.Builder<SeqNum> {

    private final Map<String, SeqNumCol> colMap = newHashMap();

    @Override
    public SeqNum build() {
      List<SeqNumCol> cols = newArrayList();

      for (SeqNumCol col : colMap.values()) {
        cols.add(col);
        col.eval(colMap);
      }

      Collections.sort(cols);
      return new SeqNum(cols);
    }

    public ColBuilder col(String id) {
      return new ColBuilder(id);
    }

    private Builder add(SeqNumCol col) {
      colMap.put(col.id, col);
      return this;
    }

    public class ColBuilder {

      private final String id;
      private int start;
      private int end;

      public ColBuilder(String id) {
        this.id = id;
      }

      public ColBuilder at(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
      }

      public Builder intArray(int[] val) {
        return add(SeqNumCol.intArray(id, start, end, val));
      }

      public Builder integer(int val) {
        return add(SeqNumCol.integer(id, start, end, val));
      }

      public Builder hasIntValue(HasIntValue val) {
        return add(SeqNumCol.hasIntValue(id, start, end, val));
      }

      public Builder valorFin(ValorFinanceiro val) {
        return add(SeqNumCol.valorFin(id, start, end, val));
      }

      public CheckDigitBuilder checkDigit(ModuloCheckDigit checker) {
        return new CheckDigitBuilder(checker);
      }

      public class CheckDigitBuilder {

        final ModuloCheckDigit checker;

        public CheckDigitBuilder(ModuloCheckDigit checker) {
          this.checker = checker;
        }

        public Builder of(String... ids) {
          List<String> checkIds = ImmutableList.copyOf(ids);
          return add(SeqNumCol.checkDigit(id, start, end, checker, checkIds));
        }

      }

    }

  }

}