/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metricas;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.*;
import java.util.*;

/**
 *
 * @author user
 */
public class M2 {
    
        public static double[][] elementos_pareto;
        
        //public static ArrayList<String> lineas=null;
        
        public static String FileName;
        
        public void M2(String FileName, double[][] matriz){
            this.elementos_pareto = matriz;
            this.FileName = FileName;
            
        }
    
        public static void readProblem(String fileName) throws FileNotFoundException,
            IOException {

        BufferedReader inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        //FileReader f = new FileReader(fileName);
        //BufferedReader b = new BufferedReader(f);
            long lNumeroLineas = 0;
            String sCadena;
            
            while ((sCadena = inputFile.readLine())!= null) {
                lNumeroLineas++;
                //System.out.println(sCadena);
             }
        /*String cadena;
        long lNumeroLineas = 0;
        while((cadena = b.readLine())!=null) {
          //System.out.println(cadena);
          lNumeroLineas++;
        }
        b.close();*/
        
        System.out.printf("Numero de lineas archivo: %d", lNumeroLineas);
        StreamTokenizer token = new StreamTokenizer(inputFile);
        try {
            //token.nextToken();

            //numberOfCities_ = (int) token.nval;
            //token.nextToken();
           

            elementos_pareto = new double[(int)lNumeroLineas][2];
            //timeMatrix_ = new double[numberOfCities_][numberOfCities_];

            // Cargar objetivo 1
            System.out.println("\nCargar Elementos del frente pareto");
            for (int k = 0; k < lNumeroLineas; k++) {
                for (int j = 0; j < 2; j++) {
                    token.nextToken();
                    elementos_pareto[k][j] = token.nval;
                    System.out.println(elementos_pareto[k][j]);
                    

                    //System.out.println("X: " + k + ", Y: " + j + ", Valor: " + distanceMatrix_[k][j]);
                } // for
            } // for

            
        } // try
        catch (Exception e) {
            System.err.println("TSP.readProblem(): error when reading data file " + e);
            System.exit(1);
        } // catch

    } // readProblem
    
        
    public static double calcular_M2(double[][] elementos){
    
        double sigma=1;
        double distancia=0.0;
        double a,b;
        int m2=0, suma=0;
        
        for(int i=0;i<elementos.length;i++)
        {
            for(int j=i+1;j<elementos.length;j++)
            {
                a = Math.pow(elementos[i][0] - elementos[j][0],2);
                b = Math.pow(elementos[i][1] - elementos[j][1],2);
                distancia = Math.sqrt(a+b);
                if (distancia>sigma)
                {
                    suma=suma+1;
                }
            }
        }
        
        m2 = 1/(elementos.length-1)*suma;
        
        return m2;
    }
                
        public static void main(String[] args) throws IOException{
        
        String FileName;
        FileName = "c:\\salidatsp\\kroac100.tsp.txt-SPEA1.txt";
        
        readProblem(FileName);
        
        double metrica_2 = 0.0;
        
        metrica_2 = calcular_M2(elementos_pareto);
        
        System.out.println("La metrica 2 es" + metrica_2);
        
        
        }
        
                
    
    }

