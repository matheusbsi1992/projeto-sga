/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

/**
 *
 * @author Jandisson
 */
import java.util.List;

/**
 *
 * @author Babji Prashanth, Chetty
 */
public class Series {

    private String name;
    private double valor;
    private List<Integer> data;

    public Series() {
    }

    public Series(String name, List<Integer> data) {
        this.name = name;
        this.data = data;
    }

    public Series(String name, double valor) {
        this.name = name;
        this.valor = valor;
    }

}
