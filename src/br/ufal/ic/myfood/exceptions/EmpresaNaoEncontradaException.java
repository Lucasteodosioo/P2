package br.ufal.ic.myfood.exceptions;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super("Nao existe empresa com esse nome");
    }
}
