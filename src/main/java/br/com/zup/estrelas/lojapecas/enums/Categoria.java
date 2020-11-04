package br.com.zup.estrelas.lojapecas.enums;

public enum Categoria {

    MOTOR("motor"), PERFORMANCE("performance"), SUSPENCAO("Suspencao"),
    FUNILARIA("funilaria"), ACESSORIOS("acessorios");

    private String value;

    Categoria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
