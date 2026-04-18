package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.exceptions.*;

import java.io.Serializable;
import java.util.*;

public class ProdutoService implements Serializable{
    private static final long serialVersionUID = 1L;

    private Map<Integer, Produto> produtos;
    // idEmpresa -> (nomeProduto -> idProduto) para controle de duplicatas
    private Map<Integer, Map<String, Integer>> empresaNomeProduto;
    // idEmpresa -> lista de ids em ordem de inserção
    private Map<Integer, List<Integer>> empresaProdutos;
    private int proximoId;

    public ProdutoService() {
        produtos = new HashMap<>();
        empresaNomeProduto = new HashMap<>();
        empresaProdutos = new HashMap<>();
        proximoId = 1;
    }

    // CRIAÇÃO

    public int criarProduto(int idEmpresa, String nome, float valor, String categoria,
                            Map<Integer, Empresa> empresas) throws Exception {
        if (!empresas.containsKey(idEmpresa)) throw new EmpresaInexistenteException();

        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Map<String, Integer> nomesEmpresa = empresaNomeProduto.getOrDefault(idEmpresa, new HashMap<>());
        if (nomesEmpresa.containsKey(nome)) throw new   ProdutoJaExisteException();

        String nomeEmpresa = empresas.get(idEmpresa).getNome();
        int id = proximoId++;
        Produto p = new Produto(id, nome, valor, categoria, idEmpresa, nomeEmpresa);
        produtos.put(id, p);

        empresaNomeProduto.computeIfAbsent(idEmpresa, k -> new HashMap<>()).put(nome, id);
        empresaProdutos.computeIfAbsent(idEmpresa, k -> new ArrayList<>()).add(id);

        return id;
    }

    // EDIÇÃO

    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws Exception {
        Produto p = produtos.get(idProduto);
        if (p == null) throw new ProdutoNaoCadastradoException();

        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        // Atualiza o mapa de nomes da empresa
        Map<String, Integer> nomesEmpresa = empresaNomeProduto.get(p.getIdEmpresa());
        nomesEmpresa.remove(p.getNome());
        nomesEmpresa.put(nome, idProduto);

        p.setNome(nome);
        p.setValor(valor);
        p.setCategoria(categoria);
    }

    // CONSULTAS

    public String getProduto(String nome, int idEmpresa, String atributo) throws Exception {
        Map<String, Integer> nomesEmpresa = empresaNomeProduto.getOrDefault(idEmpresa, new HashMap<>());
        Integer idProduto = nomesEmpresa.get(nome);
        if (idProduto == null) throw new ProdutoNaoEncontradoException();

        return produtos.get(idProduto).getAtributo(atributo);
    }

    public String listarProdutos(int idEmpresa, Map<Integer, Empresa> empresas) throws Exception {
        if (!empresas.containsKey(idEmpresa)) throw new EmpresaInexistenteException();

        List<Integer> ids = empresaProdutos.getOrDefault(idEmpresa, new ArrayList<>());
        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < ids.size(); i++) {
            sb.append(produtos.get(ids.get(i)).getNome());
            if (i < ids.size() - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }

    // RESET

    public void zerar() {
        produtos = new HashMap<>();
        empresaNomeProduto = new HashMap<>();
        empresaProdutos = new HashMap<>();
        proximoId = 1;
    }

    // VALIDAÇÕES

    private void validarNome(String nome) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
    }

    private void validarValor(float valor) throws Exception {
        if (valor < 0) throw new ValorInvalidoException();
    }

    private void validarCategoria(String categoria) throws Exception {
        if (categoria == null || categoria.trim().isEmpty()) throw new CategoriaInvalidaException();
    }
}
