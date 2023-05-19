/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@AllArgsConstructor
@NoArgsConstructor
@Entity()
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "Colaborador")
@Data
@PrimaryKeyJoinColumn(name = "idColaborador", foreignKey = @ForeignKey(name = "idColaborador",
        foreignKeyDefinition = "FOREIGN KEY(idColaborador) REFERENCES pessoa(idColaborador) ON UPDATE CASCADE ON DELETE CASCADE"),
        referencedColumnName = "idPessoa")
//@Customizer(mypackage.MyCustomizer.class)
public class Colaborador extends Pessoa implements Serializable {

    
    @Column(name = "cpfColaborador", nullable = true, length = 14)    
    private String cpfColaborador;
    
    @Column(name = "rgColaborador", nullable = true, length = 11)
    private String rgColaborador;
    
    @Column(name="salarioColaborador", scale=2, nullable=true)
    private BigDecimal salarioColaborador;
 
}