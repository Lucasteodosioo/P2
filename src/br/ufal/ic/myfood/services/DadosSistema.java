package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Produto;

import java.io.Serializable;

public class DadosSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;

    public DadosSistema(UsuarioService usuarioService, EmpresaService empresaService, ProdutoService produtoService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.produtoService = produtoService;
    }

    public UsuarioService getUsuarioService() { return usuarioService != null ? usuarioService : new UsuarioService(); }
    public EmpresaService getEmpresaService() { return empresaService != null ? empresaService : new EmpresaService(); }
    public ProdutoService getProdutoService() { return produtoService != null ? produtoService : new ProdutoService(); }
}
