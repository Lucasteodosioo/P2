package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoDeLoja;
import br.ufal.ic.myfood.exceptions.*;

import java.io.Serializable;
import java.util.*;

public class EmpresaService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Empresa> empresas;
    // nome -> lista de ids (para controle de duplicatas)
    private Map<String, List<Integer>> nomeParaIds;
    // idDono -> lista de ids das empresas dele
    private Map<Integer, List<Integer>> donoParaEmpresas;
    private int proximoId;

    public EmpresaService() {
        empresas = new HashMap<>();
        nomeParaIds = new HashMap<>();
        donoParaEmpresas = new HashMap<>();
        proximoId = 1;
    }

    // CRIAÇÃO

    public int criarEmpresa(String tipoEmpresa, int idDono, String nome, String endereco,
                            String tipoCozinha, Map<Integer, Usuario> usuarios) throws Exception {

        Usuario dono = usuarios.get(idDono);
        if (!(dono instanceof DonoDeLoja)) throw new UsuarioNaoPodeCriarEmpresaException();

        // Verifica se outro dono já tem empresa com esse nome
        if (nomeParaIds.containsKey(nome)) {
            for (int eid : nomeParaIds.get(nome)) {
                Empresa e = empresas.get(eid);
                if (e.getIdDono() != idDono) throw new EmpresaJaExisteException();
                // Mesmo dono, mesmo nome, mesmo endereço
                if (e.getEndereco().equals(endereco)) throw new EmpresaMesmoNomeELocalException();
            }
        }

        String nomeDono = dono.getNome();
        int id = proximoId++;
        Empresa emp = new Empresa(id, nome, endereco, tipoCozinha, idDono, nomeDono);
        empresas.put(id, emp);

        nomeParaIds.computeIfAbsent(nome, k -> new ArrayList<>()).add(id);
        donoParaEmpresas.computeIfAbsent(idDono, k -> new ArrayList<>()).add(id);

        return id;
    }

    // CONSULTAS

    public String getEmpresasDoUsuario(int idDono, Map<Integer, Usuario> usuarios) throws Exception {
        Usuario u = usuarios.get(idDono);
        if (!(u instanceof DonoDeLoja)) throw new UsuarioNaoPodeCriarEmpresaException();

        List<Integer> ids = donoParaEmpresas.getOrDefault(idDono, new ArrayList<>());
        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < ids.size(); i++) {
            Empresa e = empresas.get(ids.get(i));
            sb.append("[").append(e.getNome()).append(", ").append(e.getEndereco()).append("]");
            if (i < ids.size() - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
        if (indice < 0) throw new IndiceInvalidoException();

        List<Integer> ids = donoParaEmpresas.getOrDefault(idDono, new ArrayList<>());
        List<Integer> correspondentes = new ArrayList<>();
        for (int eid : ids) {
            if (empresas.get(eid).getNome().equals(nome)) correspondentes.add(eid);
        }

        if (correspondentes.isEmpty()) throw new EmpresaNaoEncontradaException();
        if (indice >= correspondentes.size()) throw new IndiceMaiorQueEsperadoException();

        return correspondentes.get(indice);
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            // Verifica se empresa existe para dar o erro correto
            if (!empresas.containsKey(idEmpresa)) throw new EmpresaNaoCadastradaException();
            throw new AtributoInvalidoException();
        }
        Empresa e = empresas.get(idEmpresa);
        if (e == null) throw new EmpresaNaoCadastradaException();
        return e.getAtributo(atributo);
    }

    // RESET

    public void zerar() {
        empresas = new HashMap<>();
        nomeParaIds = new HashMap<>();
        donoParaEmpresas = new HashMap<>();
        proximoId = 1;
    }
}
