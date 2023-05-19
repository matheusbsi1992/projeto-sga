/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.model;

import java.io.Serializable;

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
@Table(name = "Profissional")
@Data
@PrimaryKeyJoinColumn(name = "idProfissional", foreignKey = @ForeignKey(name = "idProfissional",
        foreignKeyDefinition = "FOREIGN KEY(idProfissional) REFERENCES pessoa(idPessoa) ON UPDATE CASCADE ON DELETE CASCADE"),
        referencedColumnName = "idPessoa")
//@Customizer(mypackage.MyCustomizer.class)
public class Profissional extends Pessoa implements Serializable {

    @Column(name = "conselhoProfissional", nullable = true, length = 6)
    private int conselhoProfissional;

    @OneToOne
    @JoinColumn(name = "idEspecialidade", foreignKey = @ForeignKey(name = "idEspecialidade",
            foreignKeyDefinition = "FOREIGN KEY(idEspecialidade) REFERENCES especialidade(idEspecialidade) ON UPDATE CASCADE ON DELETE NO ACTION"), nullable = true)
    private Especialidade especialidade;
 
}
