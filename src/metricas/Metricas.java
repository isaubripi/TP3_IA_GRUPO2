/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metricas;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author choffis
 */
public class Metricas {
    private static final double SIGMA = 5000;
    public static List<Double> Ytrue1;
    public static List<Double> Ytrue2;
    public static List<Double> funObj1;
    public static List<Double> funObj2;
    private Properties props;
    private String path;
    int problema;
    int corrida;
    int instancia;
  
    public Metricas() throws IOException {
        props = new Properties();
        path = System.getProperty("user.dir");
        props.load(new FileInputStream(path + "/props/bundle.properties"));
    }
    
    public Metricas(int problema, int corrida, int instancia) throws IOException {
        props = new Properties();
        path = System.getProperty("user.dir");
        props.load(new FileInputStream(path + "/props/bundle.properties"));
        this.problema = problema;
        this.corrida = corrida;
        this.instancia = instancia;
    }

    public static Double metricaM1(List<Double> f1, List<Double> f2, List<Double> y1, List<Double> y2) {
        Double[] YtrueX = y1.toArray(new Double[0]);
        Double[] YtrueY = y2.toArray(new Double[0]);
        Double[] funObjX = f1.toArray(new Double[0]);
        Double[] funObjY = f2.toArray(new Double[0]);

        Double sumaDistanciasMin = 0.0; //almacena la sumatoria de distancias
        Double distMinFinal = Double.MAX_VALUE; //distanciaEntreDosPuntos(funObjX[1] / YtrueXMax, YtrueX[1] / YtrueXMax, funObjY[1] / YtrueYMax, YtrueY[1] / YtrueYMax);
        Double distMin;

        for (int i = 0; i < funObjX.length; i++) {
            distMinFinal = Double.MAX_VALUE;
            for (int j = 0; j < YtrueX.length; j++) {
                distMin = distanciaEntreDosPuntos(funObjX[i], YtrueX[j], funObjY[i], YtrueY[j]);
                if (distMinFinal > distMin) {
                    distMinFinal = distMin;
                }
            }
            sumaDistanciasMin += distMinFinal;

        }

        return sumaDistanciasMin / funObjX.length;

    }
        

   

    public static Double metricaM2(List<Double> f1, List<Double> f2) {

        Double[] funObjX = f1.toArray(new Double[0]);
        Double[] funObjY = f2.toArray(new Double[0]);
        
        System.out.println(funObjX.length);
        
        double total = 0.0;
        for (int i = 0; i < funObjX.length; i++) {
            double cont = 0.0;
            for (int j = 0; j < funObjX.length; j++) {
                if(distanciaEntreDosPuntos(funObjX[i], funObjX[j], funObjY[i], funObjY[j]) > SIGMA){
                    cont = cont + 1;
                }
            }
            total += cont;
            //System.out.println(total);
        }

        return total / (funObjX.length - 1);
    }

    public static Double metricaM3(List<Double> f1, List<Double> f2) {

        Double[] funObjX = f1.toArray(new Double[0]);
        Double[] funObjY = f2.toArray(new Double[0]);
        Double distMinFinal = Double.MIN_VALUE;
        Double distMin = 0.0;


        for (int i = 0; i < funObjX.length; i++) {
            for (int j = 0; j < funObjX.length; j++) {
                distMin = distanciaEntreDosPuntos(funObjX[i], funObjX[j], funObjY[i], funObjY[j]);
                if (distMinFinal < distMin) {
                    distMinFinal = distMin;
                }
            }
        }

        return distMinFinal;
    }

   public static Double metricaM4(List<Double> y1, List<Double> f1) throws IOException {
        Double[] YtrueX = y1.toArray(new Double[0]);
        Double[] funObjX = f1.toArray(new Double[0]);

        /*if (instancia == ManejadorArchivo.INSTANCIA_DOS) {
                YtrueX = this.Ytrue2.toArray(new Double[0]);
                funObjX = this.funObj2.toArray(new Double[0]);
        }*/
        int FinterseccionF;
        FinterseccionF=interseccion(YtrueX,funObjX);
        double div = 0.0;
        if(FinterseccionF != 0){
            div = FinterseccionF/funObjX.length;
        }
        
        return 1- div;
        

    }

    public static int interseccion(Double [] Y, Double[] F) throws IOException {
        int pos=0;
        for (Double Y1 : Y) {
            for (Double F1 : F) {
                if (Y1 == F1) {
                    pos++;
                }
            }
        }
     return pos;
   
    }
    
    public static void cargarYtrue(String frenteParetoOptimo, List<Double> y1, List<Double> y2) throws IOException {
        try {
            FileInputStream archivo = new FileInputStream(frenteParetoOptimo);
            DataInputStream entrada = new DataInputStream(archivo);
            StringTokenizer st;
            String f1;
            String f2;
            String linea;
            //Ytrue1 = new ArrayList<Double>();
            //Ytrue2 = new ArrayList<Double>();
            while (true) {
                linea = entrada.readLine();
                if (linea == null) {
                    break;
                }
                st = new StringTokenizer(linea);

                f1 = st.nextToken();
                f2 = st.nextToken();

                y1.add(Double.parseDouble(f1));
                y2.add(Double.parseDouble(f2));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metricas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void cargarFrente(String fileName, List<Double> fo1, List<Double> fo2) throws IOException {
        try {
            FileInputStream archivo = null;
            /*if (instancia == ManejadorArchivo.INSTANCIA_UNO) {
                archivo = new FileInputStream(path + props.getProperty
                ("QAP_OUTPUT_FILE_UNO") + corrida);
            } else if (instancia == ManejadorArchivo.INSTANCIA_DOS) {
                archivo = new FileInputStream(path + props.getProperty
                ("QAP_OUTPUT_FILE_DOS") + corrida);
            }*/
            archivo = new FileInputStream(fileName);
            
            DataInputStream entrada = new DataInputStream(archivo);
            StringTokenizer st;
            String f1;
            String f2;
            String linea;
            //fo1 = new ArrayList<Double>();
            //fo2 = new ArrayList<Double>();
            while (true) {
                linea = entrada.readLine();
                if (linea == null) {
                    break;
                }
                st = new StringTokenizer(linea);

                f1 = st.nextToken(); 
                f2 = st.nextToken(); 

                fo1.add(Double.parseDouble(f1));
                fo2.add(Double.parseDouble(f2));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metricas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double distanciaEntreDosPuntos(double x, double x1, double y, double y1) {
        double distancia = 0;
        double aux = Math.pow(x - x1, 2);
        aux += Math.pow(y - y1, 2);
        distancia = Math.sqrt(aux);
        return distancia;
    }
    
    
    public static void main(String[] args) throws IOException{
        
        String FileName, FileOptimo;
        //FileName = "c:\\salidatsp\\kroac100.tsp.txt-SPEA4.txt";
        //FileOptimo = "c:\\salidatsp\\kroac100-YTRUE.txt";
        
        FileName = "c:\\salidatspkroab\\KROAB100.TSP.TXT-SPEA4.txt";
        FileOptimo = "c:\\salidatspkroab\\KROAB100-YTRUE.txt";
        
        funObj1 = new ArrayList<Double>();
        funObj2 = new ArrayList<Double>();
        
        Ytrue1 = new ArrayList<Double>();
        Ytrue2 = new ArrayList<Double>();

        
        cargarFrente(FileName, funObj1, funObj2);
        cargarYtrue(FileOptimo, Ytrue1, Ytrue2);
        
        //System.out.println(funObj1);
        //System.out.println(funObj2);
        
        double metrica_2 = 0.0;
        double metrica_3 = 0.0;
        double metrica_1 = 0.0;
        double metrica_4 = 0.0;
        
        metrica_1 = metricaM1(funObj1, funObj2, Ytrue1, Ytrue2);
        metrica_2 = metricaM2(funObj1, funObj2);
        metrica_3 = metricaM3(funObj1, funObj2);
        metrica_4 = metricaM4(Ytrue1, funObj1);
        
        System.out.println("La metrica 1 es " + metrica_1);
        System.out.println("La metrica 2 es " + metrica_2);
        System.out.println("La metrica 3 es " + metrica_3);
        System.out.println("La metrica 4 es " + metrica_4);
        
        }
    
}