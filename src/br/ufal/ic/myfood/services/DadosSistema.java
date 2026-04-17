package br.ufal.ic.myfood.services;

import java.io.Serializable;

public class DadosSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;
    private EmpresaService empresaService;

    public DadosSistema(UsuarioService usuarioService, EmpresaService empresaService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    public UsuarioService getUsuarioService() { return usuarioService; }
    public EmpresaService getEmpresaService() { return empresaService; }
}
