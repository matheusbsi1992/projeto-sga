/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.transfer.Series;
import com.google.gson.Gson;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 * Chart Controller
 *
 * @author Babji Prashanth, Chetty
 */
@ManagedBean
@ViewScoped
public class ChartController implements Serializable {

    private List<Series> valorgraficos = new ArrayList<Series>();
    private List<String> listajson = new ArrayList<String>();
    private boolean valorexiste = false;
    private boolean valor = true;
    List listMapPie = new ArrayList<>();
    Map<String, Object> mapPie;

    private void valores() {
        listMapPie = new ArrayList();
        mapPie = new HashMap<>();
        mapPie.put("name", "Column 1");
        mapPie.put("y", 3.0);
        listMapPie.add(mapPie);

        mapPie = new HashMap<>();
        mapPie.put("name", "Column 2");
        mapPie.put("y", 8.5);
        listMapPie.add(mapPie);

        mapPie = new HashMap<>();
        mapPie.put("name", "Column 3");
        mapPie.put("y", 6.0);
        listMapPie.add(mapPie);

    }

    public void listarValores() {
        listMapPie = new ArrayList();
        mapPie = new HashMap<>();
        mapPie.put("name", "Column 1");
        mapPie.put("y", 3.0);
        listMapPie.add(mapPie);

        mapPie = new HashMap<>();
        mapPie.put("name", "Column 2");
        mapPie.put("y", 8.5);
        listMapPie.add(mapPie);

        mapPie = new HashMap<>();
        mapPie.put("name", "Column 3");
        mapPie.put("y", 6.0);
        listMapPie.add(mapPie);
        setChartData(new Gson().toJson(listMapPie));
        valorexiste = true;

    }

    public void setValorexiste(boolean valorexiste) {
        this.valorexiste = valorexiste;
    }

    public boolean isValorexiste() {
        return valorexiste;
    }

    public List<Series> getValorgraficos() {
        return valorgraficos;
    }

    public void setValorgraficos(List<Series> valorgraficos) {
        this.valorgraficos = valorgraficos;
    }

    public List<String> getListajson() {
        return listajson;
    }

    public void setListajson(List<String> listajson) {
        this.listajson = listajson;
    }


    private String valorexistente = "NOME AQUI";
    private String chartData = "1";
    private String categories = "1";
    private List<String> categoryList = new ArrayList<String>();
    private List<Integer> heapSizeList = new ArrayList<Integer>();
    private List<Integer> usedHeapSizeList = new ArrayList<Integer>();
    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
    private static final int MB = 1024 * 1024;
    int index = 0;
    private Long[] longs;

    /**
     * Load Chart Data
     */
    public void loadChartData() {
        if (heapSizeList.size() > 10) {
            heapSizeList.remove(0);
            usedHeapSizeList.remove(0);
            categoryList.remove(0);
        }
        List<Series> series = new ArrayList<Series>();
        System.out.println("entrou aquientrou aquientrou aquientrou aquientrou aqui");
        malloc();
        Integer heapSize = Integer.parseInt(String.valueOf(Runtime.getRuntime().maxMemory()));
        int v = Integer.parseInt(String.valueOf(Runtime.getRuntime().freeMemory()));
        heapSizeList.add(heapSize / MB);
        usedHeapSizeList.add((heapSize - v) / MB);
        List<Integer> d = new ArrayList<>(45);
        d.add(85);
        d.add(95);
        d.add(500);
        //series.add(new Series("Heap Size", heapSizeList));
        series.add(new Series("Used Heap", d));

        setChartData(new Gson().toJson(series));

        categoryList.add(sdfDate.format(new Date()));
        System.out.println(categoryList);

        setCategories(new Gson().toJson(categoryList));
    }

    /**
     * @return the chartData
     */
    public String getChartData() {
        return chartData;
    }

    /**
     * @param chartData the chartData to set
     */
    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    /**
     * @return the categories
     */
    public String getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    private void malloc() {
        if (index % 2 == 0) {
            longs = new Long[100000];
            for (int i = 0; i < 1000; i++) {
                longs[i] = Long.valueOf(i);
            }
        } else {
            longs = null;
        }
        index++;
    }


    public String getValorexistente() {
        return valorexistente;
    }

    public void setValorexistente(String valorexistente) {
        this.valorexistente = valorexistente;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }
    
    

}
