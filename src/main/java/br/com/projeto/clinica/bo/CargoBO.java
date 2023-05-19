package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.CargoDAO;
import br.com.projeto.clinica.dao.ClinicaDAO;
import br.com.projeto.clinica.mb.LoginClinicaMB;
import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Clinica;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

public class CargoBO extends CargoDAO {

    public CargoBO() {
    }

    @Override
    public boolean ALTERARCARGO(Cargo cargo, List<String> listarpermissoes) {
        
        LoginClinicaMB loginClinicaMB = new LoginClinicaMB();
        
        return super.ALTERARCARGO(cargo, listarpermissoes); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Cargo BUSCARDECARGO(String nomeCargo, int idClinica) {
        try {
            Optional<Cargo> cargoOptional = super.BUSCADECARGO(nomeCargo, idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (!cargoOptional.isEmpty()) {
                return cargoOptional.get();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public List<Cargo> BUSCARCARGO(String nomeCargo, int idClinica) {
        return super.BUSCARCARGO(nomeCargo, idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Cargo> BUSCARTODOSCARGOS(String nomeCargo, int idCLinica) {
        return super.BUSCARTODOSCARGOS(nomeCargo, idCLinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean DELETARCARGO(int idCargo) {
        return super.DELETARCARGO(idCargo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean EXISTENOMECARGO(String nomeCargo, int idClinica) {
        return super.EXISTENOMECARGO(nomeCargo, idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean IDNOMEDOCARGOCONFERE(int idCargo, int idClinica, String nomeCargo) {
        return super.IDNOMEDOCARGOCONFERE(idCargo, idClinica, nomeCargo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean INSERIRCARGO(Cargo cargo) {
        return super.INSERIRCARGO(cargo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Cargo> LISTARCARGO(int idClinica) {
        return super.LISTARCARGO(idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Cargo> LISTARPERMISSOES() {
        return super.LISTARPERMISSOES(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Cargo> LISTARPERMISSOES(String nomeCargo, int idClinica) {
        return super.LISTARPERMISSOES(nomeCargo, idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Cargo> LISTARTODOSCARGOS(int idClinica) throws SQLException {
        return super.LISTARTODOSCARGOS(idClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean INSERIRCARGOPERMISSAO(Cargo cargo) {
        return super.INSERIRCARGOPERMISSAO(cargo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void INSERIRPERMISSAO() {
        super.INSERIRPERMISSAO(); //To change body of generated methods, choose Tools | Templates.
    }

}
