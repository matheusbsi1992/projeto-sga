/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.email;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Properties;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Pessoa;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.mail.Address;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.NoSuchProviderException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Email {

    private static HtmlEmail mail = new HtmlEmail();
    private static final StringBuffer strB = new StringBuffer();
    private static final String HOSTNAME = "smtp.office365.com";
    private static final String USERNAME = "nao-responda-minha-clinica@outlook.com";
    private static final String PASSWORD = "+-0&N4r1)M1nh4Cl1N1ca";
    private static String RESPOSTA = "INVALIDO";
    private static String titulo;
    private static String mensagem;

    public void cadastrarConectadoEmail(Pessoa pessoa) {

        //criacao de escrita pré-visualização dos dados
        mensagem = ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                + "<meta name=\"x-apple-disable-message-reformatting\">\n"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "<meta content=\"telephone=no\" name=\"format-detection\">\n"
                + "<title>Novo modelo de e-mail 2022-05-31</title><!--[if (mso 16)]>\n"
                + "<style type=\"text/css\">\n"
                + "a {text-decoration: none;}\n"
                + "</style>\n"
                + "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n"
                + "<xml>\n"
                + "<o:OfficeDocumentSettings>\n"
                + "<o:AllowPNG></o:AllowPNG>\n"
                + "<o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "</o:OfficeDocumentSettings>\n"
                + "</xml>\n"
                + "<![endif]--><!--[if !mso]><!-- -->\n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Lato:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]-->\n"
                + "<style type=\"text/css\">\n"
                + ".rollover div {\n"
                + "font-size:0;\n"
                + "}\n"
                + ".rollover:hover .rollover-first {\n"
                + "max-height:0px!important;\n"
                + "display:none!important;\n"
                + "}\n"
                + ".rollover:hover .rollover-second {\n"
                + "max-height:none!important;\n"
                + "display:block!important;\n"
                + "}\n"
                + "#outlook a {\n"
                + "padding:0;\n"
                + "}\n"
                + ".ExternalClass {\n"
                + "width:100%;\n"
                + "}\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "line-height:100%;\n"
                + "}\n"
                + ".es-button {\n"
                + "mso-style-priority:100!important;\n"
                + "text-decoration:none!important;\n"
                + "transition:all 100ms ease-in;\n"
                + "}\n"
                + "a[x-apple-data-detectors] {\n"
                + "color:inherit!important;\n"
                + "text-decoration:none!important;\n"
                + "font-size:inherit!important;\n"
                + "font-family:inherit!important;\n"
                + "font-weight:inherit!important;\n"
                + "line-height:inherit!important;\n"
                + "}\n"
                + ".es-button:hover {\n"
                + "background:#555555!important;\n"
                + "border-color:#555555!important;\n"
                + "}\n"
                + ".es-desk-hidden {\n"
                + "display:none;\n"
                + "float:left;\n"
                + "overflow:hidden;\n"
                + "width:0;\n"
                + "max-height:0;\n"
                + "line-height:0;\n"
                + "mso-hide:all;\n"
                + "}\n"
                + "[data-ogsb] .es-button {\n"
                + "border-width:0!important;\n"
                + "padding:15px 30px 15px 30px!important;\n"
                + "}\n"
                + "[data-ogsb] .es-button.es-button-1 {\n"
                + "padding:15px 30px!important;\n"
                + "}\n"
                + "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:center } h2 { font-size:26px!important; text-align:left } h3 { font-size:20px!important; text-align:left } h1 a { text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:20px!important } h3 a { text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:17px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:17px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:14px!important; display:inline-block!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .h-auto { height:auto!important } }\n"
                + "</style>\n"
                + "</head>\n"
                + "<body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + "<div class=\"es-wrapper-color\" style=\"background-color:#F1F1F1\"><!--[if gte mso 9]>\n"
                + "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "<v:fill type=\"tile\" color=\"#f1f1f1\"></v:fill>\n"
                + "</v:background>\n"
                + "<![endif]-->\n"
                + "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\"></tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:15px;padding-bottom:15px\"><!--[if mso]><table style=\"width:580px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:282px\" valign=\"top\"><![endif]-->\n"
                + "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;width:282px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td class=\"es-infoblock es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica\\ neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><br></p></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:278px\" valign=\"top\"><![endif]-->\n"
                + "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;width:278px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td class=\"es-infoblock es-m-txt-c\" align=\"right\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><br></p></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table><!--[if mso]></td></tr></table><![endif]--></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;background-color:#ffffff;border-radius:20px 20px 0px 0px\" bgcolor=\"#ffffff\" align=\"left\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td style=\"padding:0;Margin:0;font-size:0px\" align=\"center\"><a target=\"_blank\" class=\"rollover\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img class=\"adapt-img rollover-first\" src=\"https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/favicon.jpg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"35\">\n"
                + "<div style=\"font-size:0;mso-hide:all\">\n"
                + "<img class=\"adapt-img rollover-second\" style=\"display:none;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;max-height:0px\" src=\"https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/favicon.jpg\" alt width=\"35\">\n"
                + "</div></a></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td style=\"padding:0;Margin:0;background-image:url(https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/expandirsuaclinicamedica1024x6831024x675.jpg);background-repeat:no-repeat;background-position:center 0%;border-radius:50px\" background=\"https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/expandirsuaclinicamedica1024x6831024x675.jpg\" align=\"left\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n"
                + "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-image:url(https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/expandirsuaclinicamedica1024x6831024x675.jpg);background-repeat:no-repeat;background-position:center 55%\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" background=\"https://jharoz.stripocdn.email/content/guids/CABINET_6a37073544246132475862326a3a34c0/images/expandirsuaclinicamedica1024x6831024x675.jpg\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:40px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:30px;font-style:normal;font-weight:bold;color:#0b5394\"><b>Seja Bem Vindo(a) !</b></h1><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:30px;font-style:normal;font-weight:bold;color:#0b5394\"><b>Seu cadastro foi efetuado com sucesso.</b></h1></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td esdev-links-color=\"#757575\" class=\"es-m-txt-c\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#0b5394;font-size:15px\"><br><strong>Lhe desejamos uma ótima degustação da Minha Clínica.</strong><br></p></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#26a4d3;background:#26a4d3;border-width:0px;display:inline-block;border-radius:50px;width:auto\"><a href=\"\" class=\"es-button es-button-1\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;transition:all 100ms ease-in;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#FFFFFF;font-size:14px;border-style:solid;border-color:#26a4d3;border-width:15px 30px;display:inline-block;background:#26a4d3;border-radius:50px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center\">Minha Clínica</a></span></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><br><br><span style=\"font-size:26px\"><strong>Parabéns</strong></span><br><br>Você agora faz parte deste time que usam o <strong>Sistema Minha Clínica</strong> para melhorar sua gestão.</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\">A partir de hoje você pode acessar ao sistema com todas funcionalidades disponíveis. Comece a economizar horas de trabalho e dinheiro<br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\">AGORA MESMO!<br><br></p><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#333333\"><br></h3><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#333333;text-align:center\">Feito para aumentar a Produtividade, Rentabilidade e Segurança!</h3><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><br></p></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\">Com este plano laboral, <strong><em> Srº/Srª " + pessoa.getNomePessoa() + " </em></strong> segue os seus dados logo abaixo.</p></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#f7fafc;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:30px;padding-left:40px;padding-right:40px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n"
                + "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;border-left:3px solid #26a4d3;border-right:1px solid #dddddd;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;background-color:#ffffff\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\"><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#555555;text-align:center\">Minha Clínica (Seus Dados)</h3></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:15px;color:#555555;font-size:10px\"><br></p>\n"
                + "<ul>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Empresa/Clínica</strong>: " + pessoa.getClinica().getNomeClinica() + "</p></li>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Seu Nome</strong>: " + pessoa.getNomePessoa() + "</p></li>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Celular</strong>: " + pessoa.getClinica().getCelularClinica() + "</p></li>\n"
                + "</ul></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:40px;padding-left:40px;padding-right:40px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n"
                + "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;border-left:3px solid #26a4d3;border-right:1px solid #dddddd;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;background-color:#ffffff\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\"><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#555555;text-align:center\">Minha Clínica (Dados de Acesso)</h3></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:12px;color:#555555;font-size:8px\"><br></p>\n"
                + "<ul>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Usuário da Clínica</strong>: " + pessoa.getEmailPessoa() + "</p></li>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Senha de Acesso</strong>: <strong><span style=\"color:#FF0000\">" + pessoa.getSenhaPessoa() + "</span></strong></p></li>\n"
                + "<li style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;Margin-bottom:15px;margin-left:0;color:#555555;font-size:14px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#555555;font-size:15px\"><strong>Código de Acesso da Clínica</strong>: <strong><span style=\"color:#FF0000\">" + pessoa.getClinica().getCodigoClinica() + "</span></strong></p></li>\n"
                + "</ul></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#292828;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"Margin:0;padding-top:30px;padding-bottom:30px;padding-left:40px;padding-right:40px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td style=\"padding:0;Margin:0;font-size:0\" align=\"center\">\n"
                + "<table class=\"es-table-not-adapt es-social\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Facebook\" src=\"https://jharoz.stripocdn.email/content/assets/img/social-icons/circle-white/facebook-circle-white.png\" alt=\"Fb\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Twitter\" src=\"https://jharoz.stripocdn.email/content/assets/img/social-icons/circle-white/twitter-circle-white.png\" alt=\"Tw\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><img title=\"Instagram\" src=\"https://jharoz.stripocdn.email/content/assets/img/social-icons/circle-white/instagram-circle-white.png\" alt=\"Inst\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0\"><img title=\"Linkedin\" src=\"https://jharoz.stripocdn.email/content/assets/img/social-icons/circle-white/linkedin-circle-white.png\" alt=\"In\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                + "<tr style=\"border-collapse:collapse\"></tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "<table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"left\" style=\"padding:5px;Margin:0\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:590px\">\n"
                + "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#666666;font-size:12px\"><br>Minha Clínica</p></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#666666;font-size:12px\">Este aviso foi enviado para você do endereço de e-mail da empresa. <br>Por favor não responda este e-mail.<br><span style=\"font-size:10px\"></span><br><span style=\"font-size:8px\"></span><b>Fale Conosco:</b><br>(79) 99889-7439 ou (79) 99916-5475<br><a href=\"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><b>link de site</b></a> | <a href=\"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\">minhaclinicaradikal@hotmail.com</a><br></p></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#666666;font-size:12px\"><br></p></td>\n"
                + "</tr>\n"
                + "<tr style=\"border-collapse:collapse\">\n"
                + "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#666666;font-size:12px\">Copyright © 2022 <strong>Radikal TI</strong>, Todos os Direitos Reservados.</p></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>");

        //strB.append("<center><img src=\"http://ahs.org.br/ahs/wp-content/uploads/2019/10/logo.png\"></center>");
        //strB.append("<br></br>");
        //escreve dados para o envio do email;
        titulo = "Minha Clínica - Seja Bem Vindo(a)";

        try {
            enviarEmail(pessoa.getEmailPessoa());
        } catch (Exception ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println(titulo);
//        System.out.println(mensagem);
//        System.out.println(RESPOSTA);
//        System.out.println(mail);
        mail = new HtmlEmail();
        mensagem = null;

    }

    private String enviarEmail(String email) throws EmailException, Exception {

        //abri a conexao de EmailPrincipal
        mail = conectarEmail();
        //escrever informacoes a serem passadas
        mail.setSubject(titulo);
        mail.setHtmlMsg(mensagem);

        mail.addTo(email);
        //mail.setDebug(true);
        RESPOSTA = mail.send();

        return RESPOSTA;
    }

    private HtmlEmail conectarEmail() throws EmailException, Exception {

        ///email
        mail.setHostName(HOSTNAME);
        //porta
        mail.setSmtpPort(587);
        //usuario e senha 
        mail.setAuthentication(USERNAME, PASSWORD);
        //protocolo
        mail.setTLS(true);

        //mail.setDebug(true);
        //remetente do email
        mail.setFrom(USERNAME);

        return mail;

    }

}
