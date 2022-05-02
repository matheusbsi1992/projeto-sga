/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

/**
 *
 * @author root
 */
import static java.util.Arrays.asList;
import java.util.List;

public class Category {

    private String name;
    private List<TriagemChamadaClienteTransfer> products;

    public Category() {
        //
    }

    public Category(String name, TriagemChamadaClienteTransfer... products) {
        this.name = name;
        this.products = asList(products);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TriagemChamadaClienteTransfer> getProducts() {
        return products;
    }

    public void setProducts(List<TriagemChamadaClienteTransfer> products) {
        this.products = products;
    }

}
