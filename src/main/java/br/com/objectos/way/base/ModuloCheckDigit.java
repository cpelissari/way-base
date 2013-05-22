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

import java.util.Arrays;

import javax.annotation.concurrent.ThreadSafe;

import com.google.common.primitives.Ints;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ThreadSafe
public class ModuloCheckDigit {

  private final int mod;

  private final int[] multipliers;

  private final boolean digits;

  private final int[] invalids;

  private final int replaceValue;

  private ModuloCheckDigit(int mod,
                           int[] multipliers,
                           boolean digits,
                           int[] invalids,
                           int replaceValue) {
    this.mod = mod;
    this.multipliers = multipliers;
    this.digits = digits;
    this.invalids = invalids;
    this.replaceValue = replaceValue;

    Arrays.sort(this.invalids);
  }

  private ModuloCheckDigit(int mod, int[] multipliers, boolean digits) {
    this(mod, multipliers, digits, new int[] {}, 0);
  }

  public static ModBuilder newMod(int mod) {
    return new ModBuilder(mod);
  }

  public Computation start() {
    return new Computation();
  }

  public class Computation {

    private int[] data = new int[] {};

    public Computation append(int[] data) {
      this.data = Ints.concat(this.data, data);
      return this;
    }

    public Computation append(SeqNum num) {
      this.data = Ints.concat(this.data, num.toArray());
      return this;
    }

    public CheckDigit compute() {
      int sum = 0;

      int mindex = 0;
      for (int reversei = data.length; reversei > 0; reversei--) {
        int i = reversei - 1;
        int data = this.data[i];

        mindex = mindex < multipliers.length ? mindex : 0;
        int multiplier = multipliers[mindex++];
        int times = data * multiplier;

        if (!digits) {
          sum = sum + times;
        } else {
          int[] digitArray = Base10.toArray(times);
          for (int j = 0; j < digitArray.length; j++) {
            sum = sum + digitArray[j];
          }
        }
      }

      int partial = sum % mod;
      int digit = mod - partial;

      int invalid = Arrays.binarySearch(invalids, digit);
      if (invalid >= 0) {
        digit = replaceValue;
      }

      return new CheckDigit(data, digit);
    }

  }

  public static class ModBuilder {

    private final int mod;

    public ModBuilder(int mod) {
      this.mod = mod;
    }

    public MultiplierBuilder multiplierRangeOf(int start, int end) {
      int[] multipliers = new int[end - start + 1];
      for (int i = start; i <= end; i++) {
        multipliers[i - start] = i;
      }
      return new MultiplierBuilder(mod, multipliers);
    }

    public MultiplierBuilder multipliers(int... multipliers) {
      return new MultiplierBuilder(mod, multipliers);
    }

  }

  public static class MultiplierBuilder {

    private final int mod;
    private final int[] multipliers;
    private boolean digits = false;

    public MultiplierBuilder(int mod, int[] multipliers) {
      this.mod = mod;
      this.multipliers = multipliers;
    }

    public ModuloCheckDigit build() {
      return new ModuloCheckDigit(mod, multipliers, digits);
    }

    public WhenResultBuilder whenResult(int... invalids) {
      return new WhenResultBuilder(mod, multipliers, digits, invalids);
    }

    public MultiplierBuilder digits() {
      this.digits = true;
      return this;
    }

  }

  public static class WhenResultBuilder {

    private final int mod;
    private final int[] multipliers;
    private final boolean digits;
    private final int[] invalids;

    public WhenResultBuilder(int mod, int[] multipliers, boolean digits, int[] invalids) {
      this.mod = mod;
      this.multipliers = multipliers;
      this.digits = digits;
      this.invalids = invalids;
    }

    public FinalBuilder replaceWith(int replaceValue) {
      return new FinalBuilder(mod, multipliers, digits, invalids, replaceValue);
    }

  }

  public static class FinalBuilder {

    private final int mod;
    private final int[] multipliers;
    private final boolean digits;
    private final int[] invalids;
    private final int replaceValue;

    public FinalBuilder(int mod, int[] multipliers, boolean digits, int[] invalids, int replaceValue) {
      this.mod = mod;
      this.multipliers = multipliers;
      this.digits = digits;
      this.invalids = invalids;
      this.replaceValue = replaceValue;
    }

    public ModuloCheckDigit build() {
      return new ModuloCheckDigit(mod, multipliers, digits, invalids, replaceValue);
    }

  }

}