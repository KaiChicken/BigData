/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW1;

import java.io.*;
import java.util.*; 
/**
 *
 * @author tom_c
 */
public class DecisionTree {
    public final String trainFilename = "car.training.csv";
    public final String testFilename = "car.test.csv";
    public int numberOfClass = 1;
    public int numberOfAttribute = 6;
    //public String[][] trainData;
    //public String[][] testData;
    public String[][] data;
    public String[] attributes = {"buying", "maint", "doors", "person", "lugBoot", "safety"};
    public String[] classValue = {"unacc", "acc"};
    public String[] buyingAttribute = {"vhigh", "high", "med", "low"};
    public String[] maintAttribute = {"vhigh", "high", "med", "low"};
    public String[] doorsAttribute = {"2", "3", "4", "5more"};
    public String[] personsAttribute = {"2", "4", "more"};
    public String[] lugBootAttribute = {"small", "med", "big"};
    public String[] safetyAttribute = {"low", "med", "high"};
    
    public static void main (String[] args){
        DecisionTree dt = new DecisionTree();
        dt.train();
    }
    public DecisionTree(){
        
    }
    
    public void createTree(String[][] data, int numberOfAttributeRemaining){
        int[][] buyingMatrix = new int[2][4];
        int[][] maintMatrix = new int[2][4];
        int[][] doorsMatrix = new int[2][4];
        int[][] personsMatrix = new int[2][3];
        int[][] lugBootMatrix = new int[2][3];
        int[][] safetyMatrix = new int[2][3];
        double buyingGini = 0; 
        double maintGini = 0; 
        double doorsGini = 0; 
        double personsGini = 0; 
        double lugBootGini = 0;
        double safetyGini = 0; 
        for(int i = 0; i < data.length; i++){
            //1 matrix
            for(int j = 0; j < buyingAttribute.length; j++){
                if (data[i][0].equals(buyingAttribute[j]) && data[i][6].equals("unacc")){
                    buyingMatrix[0][j]++;
                }
                if (data[i][0].equals(buyingAttribute[j]) && data[i][6].equals("acc")){
                    buyingMatrix[1][j]++;
                }
            }
            
            for(int j = 0; j < maintAttribute.length; j++){
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            for(int j = 0; j < maintAttribute.length; j++){
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            for(int j = 0; j < maintAttribute.length; j++){
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            for(int j = 0; j < maintAttribute.length; j++){
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            for(int j = 0; j < maintAttribute.length; j++){
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][0].equals(maintAttribute[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            
            
        }
        
        int counter = 0;
        for(int i = 0; i < buyingMatrix.length; i++){
            for(int j = 0; j < buyingMatrix[0].length; j++){
                counter += buyingMatrix[i][j];
            }
        }
        System.out.println("buying matrix total: " + counter);
        System.out.println("buying matrix gini: " + giniCalculator(buyingMatrix));
        Tree root = new Tree();
        //root.setValue();
    }
    
    public void addNode(String[][] data, int numberOfAttributeRemaining){
    }
    
    
    
    public void train(){
        int[][] buyingMatrix = new int[2][4];
        int[][] maint = new int[2][4];
        int[][] doors = new int[2][4];
        int[][] persons = new int[2][3];
        int[][] lugBoot = new int[2][3];
        int[][] safety = new int[2][3];
        
        readFile(trainFilename);
        String[][] trainData = new String[data.length][data[0].length];
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                trainData[i][j] = data[i][j];
                System.out.print(trainData[i][j]+",,");
            }
            System.out.println("");
        }
        System.out.println("length: " + trainData.length);
        
        int[][] giniTest = new int[2][3];
        giniTest[0][0] = 1;
        giniTest[0][1] = 8;
        giniTest[0][2] = 1;
        giniTest[1][0] = 3;
        giniTest[1][1] = 0;
        giniTest[1][2] = 7;
        System.out.println("gini test: " + giniCalculator(giniTest));
        createTree(trainData,0);
    }
    
    //calculate gini index
    public double giniCalculator(int[][] matrix){
        double giniIndex = 0;
        double totalCount = 0;
        for (int i = 0; i < matrix[0].length; i++){
            for (int j = 0; j < matrix.length; j++){
                totalCount += matrix[j][i];
            }
        }
        for (int i = 0; i < matrix[0].length; i++){
            double columnCount = 0;
            double partialGini = 0; 
            for (int j = 0; j < matrix.length; j++){
                columnCount += matrix[j][i];
            }
            for (int j = 0; j < matrix.length; j++){
                partialGini += Math.pow(matrix[j][i]/columnCount, 2);
            }
            partialGini = 1 - partialGini; 
            giniIndex += partialGini*(columnCount/totalCount); 
        }
        return giniIndex;
    }
    
    //read csv file
    public void readFile(String filename){
        int counter = 0;
        String line = "";
        String splitBy = ",";
        data = new String[countLine(filename)][numberOfAttribute+numberOfClass];
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine())!= null){
                    data[counter] = line.split(",");
                    //System.out.println("first data and third data: "+data[counter][0] +" +++ " + data[counter][6]);    
                    //System.out.println(line);
                    counter++;
            }
            
            fr.close();
            br.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    //count the number of line of a file
    public int countLine(String filename){
        String line = "";
        int numberOfLine = 0; 
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine())!= null){
                numberOfLine++;
            }
            
            fr.close();
            br.close();
            System.out.println("number of line: " + numberOfLine);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return numberOfLine;
    }
    
}
