package br.ufal.ic.myfood.exceptions;

public class EmpresaInexistenteException extends Exception{
    public EmpresaInexistenteException() {
        super("Empresa nao encontrada");
    }
}
