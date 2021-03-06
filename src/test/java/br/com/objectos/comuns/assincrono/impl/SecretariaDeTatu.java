/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.assincrono.impl;

import br.com.objectos.comuns.assincrono.Agenda;
import br.com.objectos.comuns.assincrono.Agendamento;
import br.com.objectos.comuns.assincrono.AnotadorDeProgresso;
import br.com.objectos.comuns.assincrono.Configuracao;
import br.com.objectos.comuns.assincrono.Identificador;
import br.com.objectos.comuns.assincrono.SecretariaAbstrata;
import br.com.objectos.comuns.assincrono.ServicoAssincrono;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class SecretariaDeTatu extends SecretariaAbstrata<Tatu> {

  private ThreadLocal<Boolean> existente = new ThreadLocal<Boolean>();

  @Inject
  SecretariaDeTatu(Agenda<Tatu> agenda,
      AnotadorDeProgresso anotadorDeProgresso,
      ServicoAssincrono<Tatu> servicoAssincrono) {
    super(agenda, anotadorDeProgresso, servicoAssincrono);
  }

  public void naoRetornarTatus() {
    existente.set(Boolean.FALSE);
  }

  public void retornarTatus() {
    existente.set(Boolean.TRUE);
  }

  @Override
  protected Tatu buscarPor(Identificador<Tatu> identificador) {
    IdTatu id = (IdTatu) identificador;
    Boolean retornarWrapper = existente.get();
    boolean retornar = retornarWrapper == null ? false : retornarWrapper
        .booleanValue();
    return retornar ? id.novoTatu() : null;
  }

  @Override
  protected Agendamento<Tatu> novoAgendamento(
      Identificador<Tatu> identificador, Configuracao configuracao) {
    return new AgendamentoDeTatu(identificador, configuracao);
  }

}