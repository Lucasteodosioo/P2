package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;

    private int numero;
    private int idCliente;
    private String nomeCliente;
    private int idEmpresa;
    private String nomeEmpresa;
    private String estado;
    private List<Produto> produtos;

    public Pedido(int numero, int idCliente, String nomeCliente, int idEmpresa, String nomeEmpresa) {
        this.numero = numero;
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
    }

    public int getNumero() { return numero; }
    public int getIdCliente() { return idCliente; }
    public int getIdEmpresa() { return idEmpresa; }
    public String getEstado() { return estado; }
    public List<Produto> getProdutos() { return produtos; }

    public void setEstado(String estado) { this.estado = estado; }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public void removerProduto(String nomeProduto) throws Exception {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(nomeProduto)) {
                produtos.remove(i);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public String getAtributo(String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoException();
        switch (atributo) {
            case "cliente":  return nomeCliente;
            case "empresa":  return nomeEmpresa;
            case "estado":   return estado;
            case "produtos": return listarProdutos();
            case "valor":    return calcularValor();
            default: throw new AtributoNaoPedidoException();
        }
    }

    private String listarProdutos() {
        List<String> nomes = new ArrayList<>();
        for (Produto produto : produtos) {
            nomes.add(produto.getNome());
        }

        String nomesCertos = String.join(", ", nomes);
        return "{[" + nomesCertos + "]}";
    }

    private String calcularValor() {
        float total = 0;
        for (Produto p : produtos) total += p.getValor();
        return String.format(java.util.Locale.US, "%.2f", total);
    }
}
