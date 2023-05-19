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
@Data
@Table(name = "Clinica")
public class Clinica implements Serializable {

    @Id
    @Column(name = "idClinica", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClinica;

    @Column(name = "codigoClinica", nullable = false, length = 6, unique = true)
    private String codigoClinica;

    @Column(name = "nomeClinica", nullable = false, length = 20)
    private String nomeClinica;

    @Column(name = "nomeDoResponsavelClinica", nullable = true)
    private String nomeDoResponsavelClinica;

    @Column(name = "celularClinica", nullable = true, length = 15)
    private String celularClinica;
    
    @Column(name = "comercialClinica", nullable = true)
    private String comercialClinica;

    @Column(name = "emailClinica", nullable = true)
    private String emailClinica;

    @Column(name = "cnpjClinica", nullable = true)
    private String cnpjClinica;

    @Column(name = "cpfClinica", nullable = true)
    private String cpfClinica;

    @Column(name = "imagemClinica", nullable = true)
    private byte[] imagemClinica;

    @Column(name = "dataCadastroClinica", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroClinica;

    @Column(name = "statusClinica", nullable = true, length = 01)
    private String statusClinica;

    @OneToOne   
    @JoinColumn(name = "idEndereco",
            foreignKey = @ForeignKey(name = "idEndereco",
                    foreignKeyDefinition = "FOREIGN KEY(idendereco) REFERENCES endereco(idEndereco) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = true)
    private Endereco endereco;

}
