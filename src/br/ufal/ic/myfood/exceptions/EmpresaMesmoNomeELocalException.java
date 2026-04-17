package br.ufal.ic.myfood.exceptions;

public class EmpresaMesmoNomeELocalException extends Exception {
    public EmpresaMesmoNomeELocalException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}
