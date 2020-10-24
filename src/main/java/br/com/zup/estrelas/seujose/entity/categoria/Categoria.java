package br.com.zup.estrelas.seujose.entity.categoria;

public enum Categoria {

      MOTOR("Motor"), SUSPENÇÂO("Suspenção"), DIREÇÂO("Direção"), TRANSMIÇÂO("Transmissão"), FUNILARIA("Funilaria"),
      OUTROS("Outros");

      private String value;

      Categoria(String valor) {
            this.value = valor;
      }

      public String getValue() {
            return this.value;
      }

      @Override
      public String toString() {
            return this.value;
      }

}
