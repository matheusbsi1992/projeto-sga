package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.LocalDAO;
import br.com.projeto.clinica.model.Local;

import java.util.List;
import java.util.Optional;

public class LocalBO extends LocalDAO {

    public LocalBO() {
    }

    @Override
    public boolean ALTERARLOCAL(Local local) throws IndexOutOfBoundsException{
        return super.ALTERARLOCAL(local); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Local> BUSCARLISTANOMELOCAL(int idClinica, String nomeLocal) {
        return super.BUSCARLISTANOMELOCAL(idClinica, nomeLocal); //To change body of generated methods, choose Tools | Templates.
    }

    public Local BUSCARNOMELOCAL(int idClinica, String nomeLocal) {
        try {
            Optional<Local> localOptinal
                    = super.BUSCARNOMELOCALCONFERE(idClinica, nomeLocal); //To change body of generated methods, choose Tools | Templates.
            if (!localOptinal.isEmpty()) {
                return localOptinal.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean DELETARLOCAL(int idLocal) {
        return super.DELETARLOCAL(idLocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean EXISTINOMELOCAL(int idClinica, String nomeLocal) {
        return super.EXISTINOMELOCAL(idClinica, nomeLocal); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Local IDLOCALEDITAR(int idClinica, int idLocal) {
        try {
            Optional<Local> localOptinal
                    = super.IDLOCAL(idClinica, idLocal); //To change body of generated methods, choose Tools | Templates.
            if (!localOptinal.isEmpty()) {
                return localOptinal.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean IDLOCALCONFERE(int idClinica, int idLocal) {
        return super.IDLOCALCONFERE(idClinica, idLocal); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    

    @Override
    public boolean IDNOMELOCALCONFERE(int idClinica, int idLocal, String nomeLocal) {
        return super.IDNOMELOCALCONFERE(idClinica, idLocal, nomeLocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean INSERIRLOCAL(Local local) {
        return super.INSERIRLOCAL(local); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Local> LISTARTODOSLOCAL(int idCLinica) throws IndexOutOfBoundsException{
        return super.LISTARTODOSLOCAL(idCLinica); //To change body of generated methods, choose Tools | Templates.
    }

}
