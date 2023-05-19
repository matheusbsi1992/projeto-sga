package br.com.projeto.clinica.model;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Servico")
public class Servico implements Serializable {

    @Id
    @Column(name = "idServico", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServico;

    @Column(name = "nomeServico", nullable = false, length = 20)
    private String nomeServico;

    @Column(name = "valorServico", scale = 2, nullable = true)
    private BigDecimal valorServico;

    @Column(name = "statusServico", nullable = false, length = 01)
    private String statusServico;

    @Column(name = "observacaoServico", nullable = true, length = 200)
    private String observacaoServico;

    @Column(name = "dataCadastroServico", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroServico;

    @ManyToOne
    @JoinColumn(name = "idClinica",
            foreignKey = @ForeignKey(name = "idClinica",
                    foreignKeyDefinition = "FOREIGN KEY(idClinica) REFERENCES clinica(idClinica) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Clinica clinica;

}
