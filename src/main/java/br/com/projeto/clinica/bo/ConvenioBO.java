/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.ConvenioDAO;
import br.com.projeto.clinica.model.Convenio;
import java.util.List;
import java.util.Optional;


public class ConvenioBO extends ConvenioDAO {

    public ConvenioBO() {
    }

    @Override
    public boolean ALTERARCONVENIO(Convenio convenio) {
        return super.ALTERARCONVENIO(convenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Convenio> BUSCARLISTANOMECONVENIO(int idClinica, String nomeConvenio) {
        return super.BUSCARLISTANOMECONVENIO(idClinica, nomeConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Convenio BUSCANOMECONVENIOCONFERE(int idClinica, String nomeConvenio) {
        try {
            Optional<Convenio> optionalConvenio = super.BUSCARNOMECONVENIOCONFERE(idClinica, nomeConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (!optionalConvenio.isEmpty()) {
                return optionalConvenio.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean DELETARCONVENIO(int idConvenio) {
        return super.DELETARCONVENIO(idConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean EXISTINOMECONVENIO(int idClinica, String nomeConvenio) {
        return super.EXISTINOMECONVENIO(idClinica, nomeConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Convenio IDCONVENIO(int idClinica, int idConvenio) {
        try {
            Optional<Convenio> optionalConvenio = super.IDCONVENIOCONFERE(idClinica, idConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (!optionalConvenio.isEmpty()) {
                return optionalConvenio.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean IDNOMECONVENIOCONFERE(int idClinica, int idConvenio, String nomeConvenio) {
        return super.IDNOMECONVENIOCONFERE(idClinica, idConvenio, nomeConvenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean INSERIRCONVENIO(Convenio convenio) {
        return super.INSERIRCONVENIO(convenio); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Convenio> LISTARTODOSCONVENIO(int idCLinica) {
        return super.LISTARTODOSCONVENIO(idCLinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
