package br.ufal.ic.myfood.exceptions;

public class ProdutoEmpresaInvalidaException extends Exception {
    public ProdutoEmpresaInvalidaException() {
        super("O produto nao pertence a essa empresa");
    }
}
