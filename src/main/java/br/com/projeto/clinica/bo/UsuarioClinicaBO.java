package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.CargoDAO;
import br.com.projeto.clinica.dao.ClinicaDAO;
import br.com.projeto.clinica.dao.UsuarioClinicaDAO;
import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.model.Profissional;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

public class UsuarioClinicaBO extends UsuarioClinicaDAO {

    public UsuarioClinicaBO() {
    }

    @Override
    public boolean INSERIRUSUAROCLINICAPROFISSIONAL(Pessoa pessoa) {
        return super.INSERIRUSUAROCLINICAPROFISSIONAL(pessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean INSERIRUSUAROCLINICACOLABORADOR(Pessoa pessoa) {
        return super.INSERIRUSUAROCLINICACOLABORADOR(pessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean EXISTECLINICAPESSOA(String nomeClinica, String nomeEmail) {
        return super.EXISTECLINICAPESSOA(nomeClinica, nomeEmail); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
