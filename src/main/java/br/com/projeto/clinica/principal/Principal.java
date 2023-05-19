/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.principal;

import br.com.projeto.clinica.bo.ClinicaBO;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Endereco;
import br.com.projeto.clinica.model.Local;
import br.com.projeto.clinica.dao.ClinicaDAO;
import br.com.projeto.clinica.dao.LocalDAO;
import br.com.projeto.clinica.dao.UsuarioClinicaDAO;
import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.dao.CargoDAO;
import br.com.projeto.clinica.email.Email;
import br.com.projeto.clinica.model.Especialidade;
import br.com.projeto.clinica.model.Permissao;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.model.Profissional;
import br.com.projeto.clinica.util.DataUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author DHNSTI02
 */
public class Principal {

    private Random codigoAleatorio = new Random(100000);
    private static int cv = new Random().nextInt(1000000);

    public static void main(String[] args) {
        Principal principal = new Principal();

//        Pessoa pes
//        System.out.println(principal.cv);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Comunicacao");
        EntityManager createEntityManager = emf.createEntityManager();

//        Email email = new Email();
//        email.cadastrarConectadoEmail(pessoa);
////        Random random = new Random();
////        System.out.println();
//        ClinicaDAO clinicaDAO = new ClinicaDAO();
//        UsuarioClinicaDAO usuarioClinicaDAO = new UsuarioClinicaDAO();
//
//        Clinica clinica = new Clinica();
//        Endereco endereco = new Endereco();
//        Profissional profissional = new Profissional();
//        Especialidade especialidade = new Especialidade();
//
//        clinica.setNomeClinica("Teste HJ");
//        clinica.setCelularClinica("79999165475");
//        profissional.setNomePessoa("Maria Helena");
//        clinica.setNomeDoResponsavelClinica(profissional.getNomePessoa());
//        clinica.setEndereco(endereco);
//        //---INSERI CLINICA
//        clinicaDAO.INSERIRCLINICA(clinica);
//        ///---encerra CLinica
//
//        Cargo cargo = new Cargo();
//
//        int idClinica = clinicaDAO.ULTIMOIDCLINICA().getIdClinica();
//        cargo.setNomeCargo("Administrador");
//        cargo.setDescricaoCargo("Administrador do Sistema");
//        cargo.setStatusCargo("A");
//        cargo.setClinica(clinica);
//        cargo.getClinica().setIdClinica(idClinica);
//        CargoDAO cargoDAO = new CargoDAO();
//        //---INSERIR CARGO
//        if (cargoDAO.LISTARPERMISSOES().isEmpty()) {
//            cargoDAO.INSERIRPERMISSAO();
//        }
//        cargoDAO.INSERIRCARGOPERMISSAO(cargo);
//        //--ENCERRA INSERIR CARGO
//
//        profissional.setClinica(clinica);
//        profissional.setCargo(cargo);
//        profissional.setEmailPessoa("maria@gmail.com");
//        profissional.setConselhoProfissional(050545);
//       // profissional.setEspecialidade(especialidade);
//
//        usuarioClinicaDAO.INSERIRUSUAROCLINICA(profissional);
//        Endereco endereco = new Endereco();
//        LocalDAO localDAO = new LocalDAO();
//        ClinicaBO clinicaBO = new ClinicaBO();
//        Local local = new Local();
//        local.setNomeLocal("Sala");
//        local.setDataCadastroLocal(new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
//        boolean l = false;
//        try {
//            l = localDAO.EXISTINOMELOCAL(clinicaDAO.IDCLINICACONFERE(15).getIdClinica(), local.getNomeLocal());
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//        }
//        
//        try {
//            clinica = clinicaDAO.IDCLINICACONFERE(15);
//            if (clinica.getIdClinica() != 0) {
//                local.setClinica(clinica);
//            }
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//        }
//        ///passa se os dados forem incorretos, entretanto e necessario analisar as outras partes do projeto
//        if (l == false) {
//            localDAO.INSERIRLOCAL(local);
//        } else {
//            System.out.println("NAO inserido!!!");
//        }
//        clinica.setNomeClinica("MarietaD UNIDOM");
//        clinica.setCodigoClinica(random.nextInt(1000000));
//        clinica.setDataCadastroClinica(new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
//        endereco.setNomeEndereco("Rua Filadelfio Doria");
//
//        clinica.setEndereco(endereco);
//
//        clinicaDAO.INSERIRCLINICA(clinica);
//        System.out.println("");
//        clinicaDAO.LISTARTODOSCLINICA().stream().forEach(System.out::println);
//        System.out.println("");
//        clinicaDAO.IDCLINICACONFERE(1).stream().forEach(System.out::println);
//        System.out.println("");
//        System.out.println(clinicaDAO.IDCLINICACONFERE(1).get().getNomeClinica());
//        System.out.println(clinicaDAO.EXISTINOMECLINICA("Mariesta UNIDOM"));
//        System.out.println(clinicaDAO.IDNOMECLINICACONFERE(1,"Marieta UNIDOM"));
//        System.out.println("---");
//        clinicaDAO.BUSCARLISTANOMECLINICA("Mar").forEach(System.out::println);
//        System.out.println("---");
//        System.out.println(clinicaDAO.BUSCARNOMECLINICACONFERE("Marieta UNIDOM").get().getNomeClinica());
//        try {
//            clinica = clinicaBO.BUSCARNOMECLINICA("MarietaD UNIDOM");
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//        }
//
////int idCodigo = Integer.valueOf(id);
//        System.out.println(clinicaDAO.DELETARCLINICA(clinica.getIdClinica()));
////        Local local = new Local();
//        local.setNomeLocal("Tesfdgfdte");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setLenient(false);
//        TimeZone timeZone = TimeZone.getTimeZone("America/Recife");
//        calendar.setTimeZone(timeZone);
//        local.setDataCadastroLocal(calendar);
//      
//        
//        System.out.println(local.toString());
////
////        ///--inserindo local
//        createEntityManager.getTransaction().begin();
//        createEntityManager.merge(local);
//        createEntityManager.getTransaction().commit();
//
//        //emf.close();
//
//        ///--listando Local
//        createEntityManager.getTransaction().begin();
//
//        Query consulta = createEntityManager.createQuery("select local from Local local");
//        List<Local> clientes = consulta.getResultList();
//        System.out.println(clientes.listIterator().next().toString());
//        createEntityManager.getTransaction().commit();
//        emf.close();
    }

}
