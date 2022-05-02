package br.com.sga.mb;

import br.com.sga.bo.CargoBO;
import br.com.sga.transfer.CargoTransfer;

import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public final class CargoListarMB implements Serializable {

    public CargoListarMB() throws SQLException {
        listarTodosCargo();
    }

    private CargoTransfer cargotransfer = new CargoTransfer();
    private List<CargoTransfer> listartransfercargo = new ArrayList<CargoTransfer>();
    private String nomecargo;
   

    private CargoBO cargobo = new CargoBO();

    //metodo responsavel por consultar pelo nome do cargo
    public void consultarAction() throws SQLException {
        if (nomecargo.trim().length() == 0 || nomecargo.trim().equalsIgnoreCase("")) {
            listarTodosCargo();
        } else {
            listartransfercargo = cargobo.buscarCargo(nomecargo);
            if (listartransfercargo.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomecargo(null);
            }
        }
    }

    public void listarTodosCargo() throws SQLException {
        listartransfercargo = cargobo.listarCargo();
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public CargoTransfer getCargotransfer() {
        return cargotransfer;
    }

    public void setCargotransfer(CargoTransfer cargotransfer) {
        this.cargotransfer = cargotransfer;
    }

    public List<CargoTransfer> getListartransfercargo() throws SQLException {
        return listartransfercargo;
    }

    public void setListartransfercargo(List<CargoTransfer> listartransfercargo) {
        this.listartransfercargo = listartransfercargo;
    }

    public String getNomecargo() {
        return nomecargo;
    }

    public void setNomecargo(String nomecargo) {
        this.nomecargo = nomecargo;
    }

    
}
