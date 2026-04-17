package br.ufal.ic.myfood.exceptions;

public class EmailJaExisteException extends Exception {
    public EmailJaExisteException() {
        super("Conta com esse email ja existe");
    }
}
