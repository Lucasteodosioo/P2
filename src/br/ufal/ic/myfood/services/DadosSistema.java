package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Produto;

import java.io.Serializable;

public class DadosSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public DadosSistema(UsuarioService usuarioService, EmpresaService empresaService, ProdutoService produtoService, PedidoService peds) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.produtoService = produtoService;
        this.pedidoService = peds;
    }

    public UsuarioService getUsuarioService() { return usuarioService != null ? usuarioService : new UsuarioService(); }
    public EmpresaService getEmpresaService() { return empresaService != null ? empresaService : new EmpresaService(); }
    public ProdutoService getProdutoService() { return produtoService != null ? produtoService : new ProdutoService(); }
    public PedidoService getPedidoService()   { return pedidoService  != null ? pedidoService  : new PedidoService(); }
}
