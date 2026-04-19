package br.ufal.ic.myfood.exceptions;

public class AtributoNaoPedidoException extends Exception{
    public AtributoNaoPedidoException() {
        super("Atributo nao existe");
    }
}
