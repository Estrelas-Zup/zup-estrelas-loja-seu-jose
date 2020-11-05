package br.com.zup.estrelas.seujose.enums;

public enum Categoria {

      MOTOR("Motor"), SUSPENCAO("Suspenção"), DIRECAO("Direção"), TRANSMICAO("Transmissão"), FUNILARIA("Funilaria"),
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
