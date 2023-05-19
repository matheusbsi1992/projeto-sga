/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Convenio")
@Data public class Convenio implements Serializable {

    @Id
    @Column(name = "idConvenio", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConvenio;

    @Column(name = "registroAnsConvenio", nullable = true)
    private int registroAnsConvenio;

    @Column(name = "numeroConvenio", nullable = true)
    private String numeroConvenio;

    @Column(name = "nomeConvenio", nullable = true)
    private String nomeConvenio;

    @Column(name = "tipoConvenio", nullable = true)
    private String tipoConvenio;

    @Column(name = "cnpjConvenio", nullable = true, unique = true)
    private String cnpjConvenio;

    @Column(name = "cpfConvenio", nullable = true)
    private String cpfConvenio;

    @Column(name = "celularConvenio", nullable = true)
    private String celularConvenio;

    @Column(name = "telefoneConvenio", nullable = true)
    private String telefoneConvenio;

    @Column(name = "emailConvenio", nullable = true)
    private String emailConvenio;
    
    @Column(name = "statusConvenio", nullable = true, length = 1)
    private String statusConvenio;

    @Column(name = "periodoDeCarenciaDoConvenio", nullable = true)
    private int periodoDeCarenciaDoConvenio;

    @Column(name = "dataCadastroConvenio", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroConvenio;

    @OneToOne
    @JoinColumn(name = "idEndereco",
            foreignKey = @ForeignKey(name = "idEndereco",
                    foreignKeyDefinition = "FOREIGN KEY(idendereco) REFERENCES endereco(idEndereco) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "idClinica",
            foreignKey = @ForeignKey(name = "idClinica",
                    foreignKeyDefinition = "FOREIGN KEY(idClinica) REFERENCES clinica(idClinica) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Clinica clinica;
}
