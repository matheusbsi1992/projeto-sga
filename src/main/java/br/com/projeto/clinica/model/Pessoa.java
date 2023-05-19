/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import br.com.projeto.clinica.enumciado.IdentificacaoPessoa;
import java.beans.Customizer;
import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "Pessoa")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa implements Serializable {

    @Id
    @Column(name = "idPessoa", updatable = true, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPessoa;

    @Column(name = "nomePessoa", nullable = true)
    private String nomePessoa;

    @Column(name = "imagemPessoa", nullable = true)
    private byte[] imagemPessoa;

    @Column(name = "emailPessoa", nullable = true)
    private String emailPessoa;

    @Column(name = "senhaPessoa", nullable = true)
    private String senhaPessoa;

    @Transient
    private String senhaConfirmacaoPessoa;

    @Column(name = "fonePessoa", nullable = true)
    private String fonePessoa;

    @Column(name = "celularPessoa", nullable = true)
    private String celularPessoa;

    @Column(name = "dataCadastroPessoa", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroPessoa;

    @Column(name = "tipoPessoa", nullable = true, length = 12)
    private String tipoPessoa;

    @Column(name = "statusPessoa", nullable = true, length = 1)
    private String statusPessoa;

    @Transient
    IdentificacaoPessoa identificacaoPessoa;

    @OneToOne
    @JoinColumn(name = "idClinica",
            foreignKey = @ForeignKey(name = "idClinica",
                    foreignKeyDefinition = "FOREIGN KEY(idClinica) REFERENCES clinica(idClinica) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Clinica clinica;

    @OneToOne
    @JoinColumn(name = "idCargo",
            foreignKey = @ForeignKey(name = "idCargo",
                    foreignKeyDefinition = "FOREIGN KEY(idCargo) REFERENCES cargo(idCargo) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Cargo cargo;

   
}
