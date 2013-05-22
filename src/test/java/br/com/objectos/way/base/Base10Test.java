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

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class Base10Test {

  public void log_10() {
    int val = 5432;
    double log = Math.log10(val);
    int res = (int) Math.abs(log);
    assertThat(res, equalTo(3));
  }

  public void int_to_array_0() {
    int val = 0;
    int[] res = Base10.toArray(val);
    assertThat(res, equalTo(new int[] { 0 }));
  }

  public void int_to_array() {
    int val = 5;
    int[] res = Base10.toArray(val);
    assertThat(res, equalTo(new int[] { 5 }));
  }

  public void int_to_array_2() {
    int val = 67;
    int[] res = Base10.toArray(val);
    assertThat(res, equalTo(new int[] { 6, 7 }));
  }

  public void int_to_array_4() {
    int val = 5432;
    int[] res = Base10.toArray(val);
    assertThat(res, equalTo(new int[] { 5, 4, 3, 2 }));
  }

}