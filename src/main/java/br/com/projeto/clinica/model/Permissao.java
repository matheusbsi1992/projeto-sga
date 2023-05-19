/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Permissao")
public class Permissao implements Serializable {

    @Id
    @Column(name = "idPermissao", nullable = false, unique = true, table = "Permissao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermissao;

    @Column(name = "nomePermissao", nullable = false, length = 30)
    private String nomePermissao;
    
    @Column(name = "descricaoPermissao", nullable = false, length = 100)
    private String descricaoPermissao;

    @ManyToMany
    @JoinTable(
            name = "cargo_permissao",
            joinColumns = @JoinColumn(name = "idcargo",
                    foreignKey = @ForeignKey(name = "idcargo",
                            foreignKeyDefinition = "FOREIGN KEY(idcargo) REFERENCES cargo(idcargo) ON UPDATE CASCADE ON DELETE CASCADE"),
                    nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idpermissao",
                    foreignKey = @ForeignKey(name = "idpermissao",
                            foreignKeyDefinition = "FOREIGN KEY(idpermissao) REFERENCES permissao(idpermissao) ON UPDATE CASCADE ON DELETE CASCADE"),
                    nullable = false))
    private List<Permissao> descricaoPermissoes = new ArrayList<Permissao>();

    //set de permissoes a serem tratadas pelo cargo do usuario
    //@Transient
    //private List<String> descricaoPermissoes = new ArrayList<String>();
}
