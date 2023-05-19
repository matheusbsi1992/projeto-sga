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
import javax.persistence.ManyToOne;
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
@Table(name = "Local")
@Data
public class Local implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocal", nullable = false, unique = true)
    private int idLocal;

    @Column(name = "nomeLocal", length = 30, nullable = false)
    private String nomeLocal;

    @Column(name = "dataCadastroLocal", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroLocal;

    @OneToOne
    @JoinColumn(name = "idClinica",
            foreignKey = @ForeignKey(name = "idClinica",
                    foreignKeyDefinition = "FOREIGN KEY(idClinica) REFERENCES clinica(idClinica) ON UPDATE CASCADE ON DELETE CASCADE"),
            nullable = false)
    private Clinica clinica;
}
