package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoCadastradoException extends Exception {
    public UsuarioNaoCadastradoException() {
        super("Usuario nao cadastrado.");
    }
}
