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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Base10 {

  private Base10() {
  }

  public static int[] toArray(int val) {
    int size = (int) Math.abs(Math.log10(val)) + 1;
    size = size <= 0 ? 1 : size;
    int[] data = new int[size];

    int i = size;
    int temp = val;
    do {
      data[--i] = temp % 10;
      temp = temp / 10;
    } while (temp > 0);

    return data;
  }

}