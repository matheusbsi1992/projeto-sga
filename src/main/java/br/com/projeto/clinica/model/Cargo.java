/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cargo")
@Data
public class Cargo implements Serializable {

    @Id
    @Column(name = "idCargo", nullable = false, unique = true, table = "Cargo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCargo;

    @Column(name = "nomeCargo", nullable = true, length = 20)
    private String nomeCargo;

    @Column(name = "descricaoCargo", nullable = true, length = 200)
    private String descricaoCargo;

    @Column(name = "statusCargo", nullable = true, length = 1)
    private String statusCargo;

    @Column(name = "dataCadastroCargo", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroCargo;

    @Transient
    private Permissao permissao;

    @OneToOne
    @JoinColumn(name = "idClinica",
            foreignKey = @ForeignKey(name = "idClinica",
                    foreignKeyDefinition = "FOREIGN KEY(idClinica) REFERENCES clinica(idClinica) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Clinica clinica;
}
