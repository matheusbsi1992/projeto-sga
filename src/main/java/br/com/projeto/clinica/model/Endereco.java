package br.com.projeto.clinica.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Endereco")
@Data
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEndereco", nullable = false, unique = true)
    private int idEndereco;

    @Column(name = "ruaEndereco", nullable = true, length = 50)
    private String ruaEndereco;

    @Column(name = "cepEndereco", nullable = true, length = 8)
    private String cepEndereco;

    @Column(name = "nomeEndereco", nullable = true, length = 50)
    private String nomeEndereco;

    @Column(name = "numeroEndereco", nullable = true, length = 6)
    private String numeroEndereco;

    @Column(name = "complementoEndereco", nullable = true, length = 10)
    private String complementoEndereco;

    @Column(name = "bairroEndereco", nullable = true, length = 10)
    private String bairroEndereco;

    @Column(name = "cidadeEndereco", nullable = true, length = 29)
    private String cidadeEndereco;

    @Column(name = "estadoEndereco", nullable = true, length = 18)
    private String estadoEndereco;

}
