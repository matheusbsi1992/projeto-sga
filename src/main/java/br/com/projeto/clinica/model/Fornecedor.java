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
@Table(name = "Fornecedor")
@Data
public class Fornecedor implements Serializable {

    @Id
    @Column(name = "idFornecedor", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFornecedor;

    @Column(name = "razaoSocialFornecedor", nullable = false, unique = true)
    private String razaoSocialFornecedor;

    @Column(name = "nomeFantasiaFornecedor", nullable = true)
    private String nomeFantasiaFornecedor;

    @Column(name = "cpfFornecedor", nullable = false, unique = true)
    private String cpfFornecedor;

    @Column(name = "cargoFornecedor", nullable = false)
    private String cargoFornecedor;

    @Column(name = "emailFornecedor", nullable = false, unique = true)
    private String emailFornecedor;

    @Column(name = "telefoneFornecedor", nullable = true)
    private String telefoneFornecedor;

    @Column(name = "celularFornecedor", nullable = true)
    private String celularFornecedor;

    @Column(name = "cnpjFornecedor", nullable = false)
    private String cnpjFornecedor;

    @Column(name = "inscricaoEstadualFornecedor", nullable = false, unique = true)
    private String inscricaoEstadualFornecedor;

    @Column(name = "siteFornecedor", nullable = true)
    private String siteFornecedor;

    @Column(name = "observacaoFornecedor", nullable = true)
    private String observacaoFornecedor;

    @Column(name = "dataCadastroFornecedor", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroFornecedor;

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
