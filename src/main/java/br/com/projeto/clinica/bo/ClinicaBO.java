package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.ClinicaDAO;
import br.com.projeto.clinica.model.Clinica;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ClinicaBO extends ClinicaDAO {

    public ClinicaBO() {
    }

    @Override
    public boolean ALTERARCLINICA(Clinica clinica) {
        return super.ALTERARCLINICA(clinica); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clinica> BUSCARLISTANOMECLINICA(String nomeClinica) {
        return super.BUSCARLISTANOMECLINICA(nomeClinica); //To change body of generated methods, choose Tools | Templates.
    }

    public Clinica BUSCARNOMECLINICA(String nomeClinica) {
        try {
            Optional<Clinica> cliOptional = BUSCARNOMECLINICACONFERE(nomeClinica); //To change body of generated methods, choose Tools | Templates.
            if (!cliOptional.isEmpty()) {
                return cliOptional.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public Clinica EXISTICODIGOCLINICACORRESPONDENTE(String codigoClinica) {

        try {
            Optional<Clinica> cliOptional = EXISTICODIGOCLINICACONFERE(codigoClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody; //To change body of generated methods, choose Tools | Templates.
            if (!cliOptional.isEmpty()) {
                return cliOptional.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean DELETARCLINICA(int idClinica) {
        return super.DELETARCLINICA(idClinica); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean EXISTICODIGOCLINICA(String codigoClinica) {
        return super.EXISTICODIGOCLINICA(codigoClinica); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean EXISTIIDECODIGOCLINICA(int idClinica, String codigoClinica) {
        return super.EXISTIIDECODIGOCLINICA(idClinica, codigoClinica); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean EXISTINOMECLINICA(String nomeClinica) {
        return super.EXISTINOMECLINICA(nomeClinica); //To change body of generated methods, choose Tools | Templates.
    }

    public Clinica IDCLINICA(int idClinica) {

        try {
            Optional<Clinica> cliOptional = super.IDCLINICACONFERE(idClinica); //To change body of generated methods, choose Tools | Templates.
            if (!cliOptional.isEmpty()) {
                return cliOptional.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public boolean EXISTEIDNOMECLINICA(int id, String nomeClinica) {
        return super.EXISTEIDNOMECLINICA(id, nomeClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean INSERIRCLINICA(Clinica clinica) {
        return super.INSERIRCLINICA(clinica); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clinica> LISTARTODOSCLINICA() {
        return super.LISTARTODOSCLINICA(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Clinica ULTIMOIDCLINICA() {
        return super.ULTIMOIDCLINICA(); //To change body of generated methods, choose Tools | Templates.
    }

}
