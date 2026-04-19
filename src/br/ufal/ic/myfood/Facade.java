package br.ufal.ic.myfood;

import br.ufal.ic.myfood.services.*;

public class Facade {

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public Facade() {
        DadosSistema dados = PersistenciaService.carregar();
        usuarioService = dados.getUsuarioService();
        empresaService = dados.getEmpresaService();
        produtoService = dados.getProdutoService();
        pedidoService  = dados.getPedidoService();
    }

    // SISTEMA

    public void zerarSistema() throws Exception {
        usuarioService.zerar();
        empresaService.zerar();
        produtoService.zerar();
        pedidoService.zerar();
        PersistenciaService.deletarArquivo();
    }

    public void encerrarSistema() throws Exception {
        PersistenciaService.salvar(usuarioService, empresaService, produtoService, pedidoService);
    }

    // USUÁRIOS

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        usuarioService.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        usuarioService.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws Exception {
        return usuarioService.login(email, senha);
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return usuarioService.getAtributoUsuario(id, atributo);
    }

    // EMPRESAS

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha, usuarioService.getUsuarios());
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        return empresaService.getEmpresasDoUsuario(idDono, usuarioService.getUsuarios());
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        return empresaService.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        return empresaService.getAtributoEmpresa(empresa, atributo);
    }

    //PRODUTOS

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws Exception {
        return produtoService.criarProduto(empresa, nome, valor, categoria, empresaService.getEmpresas());
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws Exception {
        produtoService.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return produtoService.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws Exception {
        return produtoService.listarProdutos(empresa, empresaService.getEmpresas());
    }

    //PEDIDOS

    public int criarPedido(int cliente, int empresa) throws Exception {
        return pedidoService.criarPedido(cliente, empresa, usuarioService.getUsuarios(), empresaService.getEmpresas());
    }

    public void adicionarProduto(int numero, int produto) throws Exception {
        pedidoService.adicionarProduto(numero, produto, produtoService.getProdutos());
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        pedidoService.removerProduto(pedido, produto);
    }

    public String getPedidos(int pedido, String atributo) throws Exception {
        return pedidoService.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        pedidoService.fecharPedido(numero);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return pedidoService.getNumeroPedido(cliente, empresa, indice);
    }
}