package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.*;
import br.ufal.ic.myfood.exceptions.*;

import java.io.Serializable;
import java.util.*;

public class PedidoService implements Serializable{
    private static final long serialVersionUID = 1L;

    private Map<Integer, Pedido> pedidos;
    // idCliente -> idEmpresa -> numero do pedido em aberto
    private Map<Integer, Map<Integer, Integer>> pedidosAbertos;
    // idCliente -> idEmpresa -> lista de numeros de pedidos (ordem de criação)
    private Map<Integer, Map<Integer, List<Integer>>> historicoPedidos;
    private int proximoNumero;

    public PedidoService() {
        pedidos = new HashMap<>();
        pedidosAbertos = new HashMap<>();
        historicoPedidos = new HashMap<>();
        proximoNumero = 1;
    }

    // CRIAR PEDIDO

    public int criarPedido(int idCliente, int idEmpresa, Map<Integer, Usuario> usuarios,
                           Map<Integer, Empresa> empresas) throws Exception {

        Usuario cliente = usuarios.get(idCliente);
        if (cliente instanceof DonoDeLoja) throw new DonoPedidoException();

        // Verifica se já existe pedido aberto para essa empresa
        Map<Integer, Integer> abertosCliente = pedidosAbertos.getOrDefault(idCliente, new HashMap<>());
        if (abertosCliente.containsKey(idEmpresa)) throw new PedidoDuplicadoException();

        String nomeCliente = cliente.getNome();
        String nomeEmpresa = empresas.get(idEmpresa).getNome();

        int numero = proximoNumero++;
        Pedido p = new Pedido(numero, idCliente, nomeCliente, idEmpresa, nomeEmpresa);
        pedidos.put(numero, p);

        pedidosAbertos.computeIfAbsent(idCliente, k -> new HashMap<>()).put(idEmpresa, numero);
        historicoPedidos.computeIfAbsent(idCliente, k -> new HashMap<>())
                .computeIfAbsent(idEmpresa, k -> new ArrayList<>()).add(numero);

        return numero;
    }

    // ADICIONAR PRODUTO

    public void adicionarProduto(int numeroPedido, int idProduto,
                                 Map<Integer, Produto> produtos) throws Exception {

        Pedido pedido = pedidos.get(numeroPedido);
        if (pedido == null) throw new PedidoNaoAbertoException();
        if (!pedido.getEstado().equals("aberto")) throw new PedidoFechadoAdicionarException();

        Produto produto = produtos.get(idProduto);
        if (produto.getIdEmpresa() != pedido.getIdEmpresa()) throw new ProdutoEmpresaInvalidaException();

        pedido.adicionarProduto(produto);
    }

    // REMOVER PRODUTO

    public void removerProduto(int numeroPedido, String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) throw new ProdutoInvalidoException();

        Pedido pedido = pedidos.get(numeroPedido);
        if (pedido == null) throw new PedidoNaoEncontradoException();
        if (!pedido.getEstado().equals("aberto")) throw new PedidoFechadoRemoverException();

        pedido.removerProduto(nomeProduto);
    }

    // FECHAR PEDIDO

    public void fecharPedido(int numeroPedido) throws Exception {
        Pedido pedido = pedidos.get(numeroPedido);
        if (pedido == null) throw new PedidoNaoEncontradoException();

        pedido.setEstado("preparando");

        // Remove dos pedidos abertos
        Map<Integer, Integer> abertosCliente = pedidosAbertos.get(pedido.getIdCliente());
        if (abertosCliente != null) abertosCliente.remove(pedido.getIdEmpresa());
    }

    // CONSULTAS

    public String getPedidos(int numeroPedido, String atributo) throws Exception {
        Pedido pedido = pedidos.get(numeroPedido);
        if (pedido == null) throw new PedidoNaoEncontradoException();
        return pedido.getAtributo(atributo);
    }

    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) throws Exception {
        List<Integer> lista = historicoPedidos
                .getOrDefault(idCliente, new HashMap<>())
                .getOrDefault(idEmpresa, new ArrayList<>());

        if (indice >= lista.size()) throw new IndiceMaiorQueEsperadoException();
        return lista.get(indice);
    }

    // RESET

    public void zerar() {
        pedidos = new HashMap<>();
        pedidosAbertos = new HashMap<>();
        historicoPedidos = new HashMap<>();
        proximoNumero = 1;
    }
}
