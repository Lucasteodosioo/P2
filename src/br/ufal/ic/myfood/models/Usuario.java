package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getEndereco() { return endereco; }

    public String getAtributo(String atributo) throws Exception {
        switch (atributo) {
            case "nome": return nome;
            case "email": return email;
            case "senha": return senha;
            case "endereco": return endereco;
            default: throw new Exception("Atributo nao encontrado.");
        }
    }
}
