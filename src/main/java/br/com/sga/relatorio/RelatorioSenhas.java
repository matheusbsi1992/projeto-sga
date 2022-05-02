/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.relatorio;

import br.com.sga.conexaofactory.Conexao;
import br.com.sga.dao.DAO;
import br.com.sga.mb.MonitorChamadaClienteMB;
import java.applet.Applet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Jandisson
 */
public class RelatorioSenhas extends DAO {

    public void imprimirSenha(String nometriagemchamadacliente, String tipo) throws IOException, JRException, SQLException {

        Conexao conexao = new Conexao();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

        Map<String, Object> params = new HashMap<>();
        params.put("nometriagemchamadacliente", nometriagemchamadacliente);
        //String caminhosenhareport = new File("../src/main/webapp/resources/reports/senha.jrxml").getCanonicalPath();
        String localarquivo = "/resources/reports/senha.jrxml";
        String caminhosenhareport = FacesContext.getCurrentInstance().getExternalContext().getRealPath(localarquivo);
        JasperReport report = JasperCompileManager.compileReport(caminhosenhareport);
        JasperPrint print = JasperFillManager.fillReport(report, params, conexao.getConexao());
        JasperExportManager.exportReportToPdfStream(print, bytearrayoutputstream);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.reset();
        if (tipo.equalsIgnoreCase("monitortriagemchamadacliente")) {
            response.reset();
            response.setContentLength(bytearrayoutputstream.size());
            response.setHeader("Content-Disposition", "inline; filename=" + nometriagemchamadacliente + ".pdf");
            response.setContentType("application/pdf");
            response.getOutputStream().write(bytearrayoutputstream.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            //response.getOutputStream().print(false);
        }
//        else {
//            response.reset();
//            response.getOutputStream().print(false);
//            response.setContentLength(bytearrayoutputstream.size());
//            response.setHeader("Content-Disposition", "inline; filename=" + nometriagemchamadacliente + ".pdf");
//            response.setContentType("application/pdf");
//            response.getOutputStream().write(bytearrayoutputstream.toByteArray());
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//            // response.getOutputStream().print(false);
//        }
        FacesContext.getCurrentInstance().responseComplete();
        conexao.fecharConexao();

    }

//    /**
//     * Métodos com a característica de imprimir a senha do paciente
//     *
//     * @param nometriagemchamadacliente
//     */
//    public void imprimirSenhaDireta(String nometriagemchamadacliente) throws IOException, JRException, SQLException {
//
//        Conexao conexao = new Conexao();
//        DAO dao = new DAO();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("nometriagemchamadacliente", nometriagemchamadacliente);
//
//        String caminhosenhareport = new File("C:\\Program Files\\glassfish-4.1\\glassfish\\domains\\domain1\\applications\\sga\\reports\\senha.jrxml").getCanonicalPath();
//
//        JasperReport jasperReport = JasperCompileManager.compileReport(caminhosenhareport);
//
//        JasperPrint printarrelatorio = JasperFillManager.fillReport(jasperReport, params, conexao.getConexao());
//
//        try {
//            PrintReportToPrinter(printarrelatorio);
//
//            //Vizualiza
//            //JasperViewer.viewReport("../src/main/webapp/reports/senha.jrxml", false, false);
//            //imprimir direto para impressora padrao
//            //JasperPrintManager.printReport(printarrelatorio, false);
//            //JasperPrintManager.printPage(printarrelatorio, 0, false);
//        } catch (JRException ex) {
//            dao.logPrincipal(RelatorioSenhas.class).error(">>>>ERROR AO IMPRIMIR SENHA DIRETAMENTE(imprimirSenhaDireta) ", ex);
//        }
//    }
//
//    //https://stackoverflow.com/questions/31028896/how-to-print-jasper-reports-in-a-specified-printer
//    private void PrintReportToPrinter(JasperPrint jasperPrint) throws JRException {
//
////Get the printers names
//        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//
////Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
////        String selectedPrinter = "Microsoft XPS Document Writer";
//        String selectedPrinter = "Foxit Reader PDF Printer";
//        // String selectedPrinter = "\\\\S-BPPRINT\\HP Color LaserJet 4700"; // examlpe to network shared printer
//        PrintService impressora = null;
//        System.out.println("Number of print services: " + services.length);
//        PrintService selectedService = null;
//
//        //Set the printing settings
//        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
//        printRequestAttributeSet.add(new Copies(1));
//        if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
//            printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
//        } else {
//            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
//        }
//        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
//        if (selectedPrinter.equalsIgnoreCase("Foxit Reader PDF Printer")) {
//            printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));
//        } else {
//            printServiceAttributeSet
//                    = (PrintServiceAttributeSet) PrintServiceLookup.lookupDefaultPrintService();
//            System.out.println("Impressora Não Localizada Enviada para Impressora Padrão: " + printServiceAttributeSet.toString());
//        }
//
//        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
//        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
//        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
//        configuration.setDisplayPageDialog(false);
//        configuration.setDisplayPrintDialog(false);
//
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setConfiguration(configuration);
//
//        //Iterate through available printer, and once matched with our <selectedPrinter>, go ahead and print!
//        if (services != null && services.length != 0) {
//            for (PrintService service : services) {
//                String existingPrinter = service.getName();
//                if (existingPrinter.equals(selectedPrinter)) {
//                    selectedService = service;
//                    break;
//                }
//            }
//        }
//        if (selectedService != null) {
//            try {
//                //Lets the printer do its magic!
//                exporter.exportReport();
//            } catch (Exception e) {
//                System.out.println("JasperReport Error: " + e.getMessage());
//            }
//        } else {
//            System.out.println("JasperReport Error: Printer not found!");
//        }
//    }

}
