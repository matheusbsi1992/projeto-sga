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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Especialidade")
public class Especialidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidade", nullable = false, unique = true)
    private int idEspecialidade;

    @Column(name = "nomeEspecialidade", nullable = true)
    private String nomeEspecialidade;

    @Column(name = "dataCadastroEspecialidade", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCadastroEspecialidade;

}
