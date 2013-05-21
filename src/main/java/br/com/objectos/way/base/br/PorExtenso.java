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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class PorExtenso {

  static final PorExtenso INTEIRO = new PorExtensoInteiro();

  PorExtenso() {
  }

  public static PorExtenso inteiro() {
    return INTEIRO;
  }

  public static DecimalBuilder novoDecimal() {
    return new DecimalBuilder();
  }

  public abstract String toString(int numero);
  public abstract String toString(double numero);

  public static class DecimalBuilder {

    private int decimais;
    private String[] unidadeInteira;
    private String[] unidadeDecimal;

    public UnidadeBuilder comCasas(int decimais) {
      return new UnidadeBuilder(decimais);
    }

    public class UnidadeBuilder {

      public UnidadeBuilder(int decimais) {
        DecimalBuilder.this.decimais = decimais;
      }

      public PorExtensoDecimalBuilder unidades(String singular, String plural) {
        unidadeInteira = new String[] { singular, plural };
        return new PorExtensoDecimalBuilder();
      }
    }

    public class PorExtensoDecimalBuilder {
      public PorExtenso e(String singular, String plural) {
        unidadeDecimal = new String[] { singular, plural };
        return new PorExtensoDecimal(decimais, unidadeInteira, unidadeDecimal);
      }
    }

  }

}