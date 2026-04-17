package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoDeLoja;
import br.ufal.ic.myfood.exceptions.*;

import java.io.*;
import java.util.*;

public class UsuarioService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Usuario> usuarios;
    private Map<String, Integer> emailParaId;
    private int proximoId;

    public UsuarioService() {
        usuarios = new HashMap<>();
        emailParaId = new HashMap<>();
        proximoId = 1;
    }

    // CRIAÇÃO

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);

        if (emailParaId.containsKey(email)) throw new EmailJaExisteException();

        int id = proximoId++;
        Usuario u = new Usuario(id, nome, email, senha, endereco);
        usuarios.put(id, u);
        emailParaId.put(email, id);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarCpf(cpf);

        if (emailParaId.containsKey(email)) throw new EmailJaExisteException();

        int id = proximoId++;
        DonoDeLoja d = new DonoDeLoja(id, nome, email, senha, endereco, cpf);
        usuarios.put(id, d);
        emailParaId.put(email, id);
    }

    // LOGIN

    public int login(String email, String senha) throws Exception {
        if (email == null || email.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new LoginInvalidoException();

        Integer id = emailParaId.get(email);
        if (id == null) throw new LoginInvalidoException();

        Usuario u = usuarios.get(id);
        if (!u.getSenha().equals(senha)) throw new LoginInvalidoException();

        return id;
    }

    // ATRIBUTOS

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        Usuario u = usuarios.get(id);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.getAtributo(atributo);
    }

    // Expõe o mapa para uso em outros services
    public Map<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    // RESET

    public void zerar() {
        usuarios = new HashMap<>();
        emailParaId = new HashMap<>();
        proximoId = 1;
    }

    // VALIDAÇÕES

    private void validarNome(String nome) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
    }

    private void validarEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) throw new EmailInvalidoException();
        if (!email.matches("^[^@]+@[^@]+\\.[^@]+$")) throw new EmailInvalidoException();
    }

    private void validarSenha(String senha) throws Exception {
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
    }

    private void validarEndereco(String endereco) throws Exception {
        if (endereco == null || endereco.trim().isEmpty()) throw new EnderecoInvalidoException();
    }

    private void validarCpf(String cpf) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) throw new CpfInvalidoException();
        if (cpf.length() != 14) throw new CpfInvalidoException();
    }
}