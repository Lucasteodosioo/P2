package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.io.Serializable;
import java.util.Locale;

public class Produto implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int idEmpresa;
    private String nomeEmpresa;

    public Produto(int id, String nome, float valor, String categoria, int idEmpresa, String nomeEmpresa) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public float getValor() { return valor; }
    public String getCategoria() { return categoria; }
    public int getIdEmpresa() { return idEmpresa; }

    public void setNome(String nome) { this.nome = nome; }
    public void setValor(float valor) { this.valor = valor; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getAtributo(String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoNaoExisteException();
        switch (atributo) {
            case "nome":      return nome;
            case "valor":     return String.format(Locale.US, "%.2f", valor);
            case "categoria": return categoria;
            case "empresa":   return nomeEmpresa;
            default: throw new AtributoNaoExisteException();
        }
    }
}
