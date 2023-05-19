package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.ServicoDAO;
import br.com.projeto.clinica.model.Servico;

import java.util.List;
import java.util.Optional;

public class ServicoBO extends ServicoDAO {

    public ServicoBO() {
    }

    @Override
    public boolean ALTERARSERVICO(Servico servico) {
        return super.ALTERARSERVICO(servico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Servico> BUSCARLISTANOMESERVICO(int idClinica, String nomeServico) {
        return super.BUSCARLISTANOMESERVICO(idClinica, nomeServico); //To change body of generated methods, choose Tools | Templates.
    }

    public Servico BUSCARNOMESERVICO(int idClinica, String nomeServico) {
        try {
            Optional<Servico> servicoOptinal
                    = super.BUSCARNOMESERVICOCONFERE(idClinica, nomeServico); //To change body of generated methods, choose Tools | Templates.
            if (!servicoOptinal.isEmpty()) {
                return servicoOptinal.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean DELETARSERVICO(int idServico) {
        return super.DELETARSERVICO(idServico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean EXISTINOMESERVICO(int idClinica, String nomeServico) {
        return super.EXISTINOMESERVICO(idClinica, nomeServico); //To change body of generated methods, choose Tools | Templates.
    }

    public Servico IDSERVICO(int idClinica, int idServico) {
        try {
            Optional<Servico> servicoOptinal
                    = super.IDSERVICOCONFERE(idClinica, idServico); //To change body of generated methods, choose Tools | Templates.
            if (!servicoOptinal.isEmpty()) {
                return servicoOptinal.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean IDNOMESERVICOCONFERE(int idClinica, int idServico, String nomeServico) {
        return super.IDNOMESERVICOCONFERE(idClinica, idServico, nomeServico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean INSERIRSERVICO(Servico servico) {
        return super.INSERIRSERVICO(servico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Servico> LISTARTODOSSERVICO(int idCLinica) {
        return super.LISTARTODOSSERVICO(idCLinica); //To change body of generated methods, choose Tools | Templates.
    }

}
