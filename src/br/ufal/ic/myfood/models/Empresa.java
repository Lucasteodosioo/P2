package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.io.Serializable;

public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private int idDono;
    private String nomeDono;

    public Empresa(int id, String nome, String endereco, String tipoCozinha, int idDono, String nomeDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.idDono = idDono;
        this.nomeDono = nomeDono;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getTipoCozinha() { return tipoCozinha; }
    public int getIdDono() { return idDono; }

    public String getAtributo(String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoException();
        switch (atributo) {
            case "nome":       return nome;
            case "endereco":   return endereco;
            case "tipoCozinha": return tipoCozinha;
            case "dono":       return nomeDono;
            default: throw new AtributoInvalidoException();
        }
    }
}
