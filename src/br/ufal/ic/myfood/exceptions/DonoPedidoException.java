package br.ufal.ic.myfood.exceptions;

public class DonoPedidoException extends Exception {
    public DonoPedidoException() {
        super("Dono de empresa nao pode fazer um pedido");
    }
}
