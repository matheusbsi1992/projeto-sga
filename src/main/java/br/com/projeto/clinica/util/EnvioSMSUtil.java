/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.util;

import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Pessoa;

import br.com.projeto.clinica.validacao.ValidarUsuarioClinica;
import kong.unirest.Unirest;

/**
 *
 * @author matheus
 */
public class EnvioSMSUtil {

//    public String ENVIODESMSCRIACAODEUSUARIOCLINICA(Clinica clinica, Profissional pessoa, int senhaGeradora) {
//        //---INICIA O ENVIO DE SMS
//        String response = Unirest.get(
//                "https://api.smsdev.com.br/v1/send?"
//                + "key=3U5WM0M2XM8OVUYZYDPQZ6GD2NPH1TUCXFSSBPAH9NKRH3235TVB2BM5IUDNTHEU10H6J8G819HGAJG7GLS44HHF96G46PCBO1Y0CJB62DRDU3DYF6SXZWAA6XBQXSJP"
//                + "& type=9"
//                + "& number= " + clinica.getCelularClinica()
    //  + "& msg=" + pessoa.getNomePessoa() + ", Seja Bem Vindo a Minha Clínica !  Sua conta foi criada com sucesso. Para acesso ao sistema Minha Clínica acesse o seu e-mail ou através deste link: com o seguinte dados, E-mail: " + pessoa.getEmailPessoa() + " Senha de Acesso: " + senhaGeradora + " e Código da Clínica: " + clinica.getCodigoClinica() + "Tenha uma ótima degustação!"
//        ).asString().getBody();
//        System.out.println(response);
//        System.out.println("");
//        //---SORITRCBJQW8CW14Y8RYCFC5CUPQ8AO139IGS79T3LB0QI7VQR7MF8H5IH9121DM0EO54BX1U3Y2XK13MWCOJ66LRRHV9NG3005VT5XKV6PDSI84ELPM7K8S551DYD5U
//        Unirest.shutDown();
//        //---FINALIZA O ENVIO DE SMS
//
//        return response;
//    }
    public String ENVIODESMSCRIACAODEUSUARIOCLINICA(Clinica clinica, Pessoa pessoa, int senhaGeradora) {
        //---INICIA O ENVIO DE SMS
        String response = Unirest.get(
                "https://api.mobizon.com.br/service/message/sendsmsmessage?"
                + "recipient= " + "55" + (clinica.getCelularClinica())
                + "&"
                + "text=Minha Clínica! " + pessoa.getEmailPessoa() + " - " + senhaGeradora + " - " + clinica.getCodigoClinica()
                + "&"
                + "apiKey=brc2146b44b67c11ef452953923f31a0bef8f0f63ed82a23ed6f2ac558c3f11c975eee"
        ).asString().getBody();
        //System.out.println(response);
       // System.out.println("");
        //---SORITRCBJQW8CW14Y8RYCFC5CUPQ8AO139IGS79T3LB0QI7VQR7MF8H5IH9121DM0EO54BX1U3Y2XK13MWCOJ66LRRHV9NG3005VT5XKV6PDSI84ELPM7K8S551DYD5U
        Unirest.shutDown();

        //System.out.println("");
        //---FINALIZA O ENVIO DE SMS

        ///https://api.mobizon.com.br/service/message/sendsmsmessage?recipient=5579999165475&text=Minha%20Clinica&apiKey=brc2146b44b67c11ef452953923f31a0bef8f0f63ed82a23ed6f2ac558c3f11c975eee
        return response;
    }

}
