/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.CargoDAO;
import br.com.sga.transfer.CargoTransfer;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jandisson
 */
public class CargoBO extends CargoDAO {

    @Override
    public boolean inserirCargo(CargoTransfer cargotransfer) throws SQLException {
        return super.inserirCargo(cargotransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarCargo(CargoTransfer cargotransfer,List<String> listarpermissoes) throws SQLException {
        return super.alterarCargo(cargotransfer,listarpermissoes); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarCargo(String nomecargo) throws SQLException {
        return super.deletarCargo(nomecargo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeNomeCargo(String nomecargo) throws SQLException {
        return super.existeNomeCargo(nomecargo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idNomedoCargoConfere(Short id, String nomecargo) throws SQLException {
        return super.idNomedoCargoConfere(id, nomecargo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CargoTransfer> buscarCargo(String nomecargo) throws SQLException {
        return super.buscarCargo(nomecargo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CargoTransfer> listarPermissoes() throws SQLException {
        return super.listarPermissoes(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CargoTransfer> listarCargo() throws SQLException {
        return super.listarCargo(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CargoTransfer> listareBuscarPermissoes(String nomecargo) throws SQLException {
        return super.listareBuscarPermissoes(nomecargo); //To change body of generated methods, choose Tools | Templates.
    }

}
