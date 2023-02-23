package br.com.sicredi.cooperativismo.entity.enums;

public enum CpfValidoStatusEnum {

    CAPAZ(1, "ABLE_TO_VOTE"),
    IMCAPAZ(2, "UNABLE_TO_VOTE");

    private int cod;
    private String descricao;

    private CpfValidoStatusEnum(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static CpfValidoStatusEnum toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (CpfValidoStatusEnum x : CpfValidoStatusEnum.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Codigo inválido: " + cod);
    }

}
