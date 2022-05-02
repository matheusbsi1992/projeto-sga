///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.sga.mb;
//
//import br.com.sga.transfer.UsuarioTransfer;
//import java.io.Serializable;
//import java.sql.SQLException;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Named;
//
///**
// *
// * @author Jandisson
// */
//@ManagedBean
//@SessionScoped
//public class LoginMB implements Serializable {
//
//    private UsuarioTransfer usuarioTransfer;
//
//    private static LoginMB me;
//
//    public static LoginMB getInstance() throws SQLException {
//        if (me == null) {
//            me = new LoginMB();
//        }
//        return me;
//    }
//
//    public UsuarioTransfer getUsuarioTransfer() throws SQLException {
//        if (usuarioTransfer == null) {
//            usuarioTransfer = new UsuarioTransfer();
//        }
//        return usuarioTransfer;
//    }
//
//    public void setUsuarioTransfer(UsuarioTransfer usuarioTransfer) throws SQLException {
//        
//        this.usuarioTransfer = usuarioTransfer;
//    }
//
//    public UsuarioTransfer usuario() throws SQLException {
//        return LoginMB.getInstance().getUsuarioTransfer();
//    }
//
//    public boolean isLogado() {
//        return this.usuarioTransfer != null;
//    }
//
//}
