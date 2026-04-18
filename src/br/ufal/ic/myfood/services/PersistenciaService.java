package br.ufal.ic.myfood.services;

import java.io.*;

public class PersistenciaService {

    private static final String ARQUIVO_DADOS = "myfood_dados.ser";

    public static void salvar(UsuarioService us, EmpresaService es, ProdutoService ps) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(new DadosSistema(us, es, ps));
        }
    }

    public static DadosSistema carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                return (DadosSistema) ois.readObject();
            } catch (Exception e) {
                return novosDados();
            }
        }
        return novosDados();
    }

    private static DadosSistema novosDados() {
        return new DadosSistema(new UsuarioService(), new EmpresaService(), new ProdutoService());
    }

    public static void deletarArquivo() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) arquivo.delete();
    }
}