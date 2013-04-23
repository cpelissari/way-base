/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.base.text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class DecimalFormats {

  private DecimalFormats() {
  }

  public static DecimalFormat newCurrencyWithLocale(String language) {
    Locale locale = new Locale(language);
    DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
    return new DecimalFormat("#,##0.00", symbols);
  }

}