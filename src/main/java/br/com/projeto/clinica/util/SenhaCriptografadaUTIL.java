/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.util;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 *
 * @author matheus
 */
public class SenhaCriptografadaUTIL {

    /**
     * Metodo hashCode
     *
     *
     * @param valorHash
     * @return SimpleHash
     */
    public SimpleHash HASHCODE(Object valorHash) {
        SimpleHash simplehash = new Sha512Hash(valorHash);
        
        return simplehash;
    }

}
