package br.ufal.ic.myfood;

import br.ufal.ic.myfood.services.*;

public class Facade {

    private UsuarioService usuarioService;
    private EmpresaService empresaService;

    public Facade() {
        DadosSistema dados = PersistenciaService.carregar();
        usuarioService = dados.getUsuarioService();
        empresaService = dados.getEmpresaService();
    }

    // SISTEMA

    public void zerarSistema() throws Exception {
        usuarioService.zerar();
        empresaService.zerar();
        PersistenciaService.deletarArquivo();
    }

    public void encerrarSistema() throws Exception {
        PersistenciaService.salvar(usuarioService, empresaService);
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
}