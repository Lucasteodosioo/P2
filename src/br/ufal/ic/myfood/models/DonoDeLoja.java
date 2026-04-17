package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class DonoDeLoja extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public DonoDeLoja(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() { return cpf; }

    @Override
    public String getAtributo(String atributo) throws Exception {
        if (atributo.equals("cpf")) return cpf;
        return super.getAtributo(atributo);
    }
}
