package br.ufal.ic.myfood.exceptions;

public class PedidoNaoAbertoException extends Exception {
    public PedidoNaoAbertoException() {
        super("Nao existe pedido em aberto");
    }
}
