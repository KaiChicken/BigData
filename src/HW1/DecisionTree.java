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
    public String[] attributesValueName = {"buyingValue", "maintValue", "doorsValue", "personValue", "lugBootValue", "safetyValue"};
    public String[] classValue = {"unacc", "acc"};
    public String[] buyingValue = {"vhigh", "high", "med", "low"};
    public String[] maintValue = {"vhigh", "high", "med", "low"};
    public String[] doorsValue = {"2", "3", "4", "5more"};
    public String[] personsValue = {"2", "4", "more"};
    public String[] lugBootValue = {"small", "med", "big"};
    public String[] safetyValue = {"low", "med", "high"};
    
    public static void main (String[] args){
        DecisionTree dt = new DecisionTree();
        dt.train();
        dt.createRoot();
    }
    public DecisionTree(){
        
    }
    
    public void createRoot(){
        Tree root = new Tree();
        root.setValue("root");
        String[] remainingAttribute = {"buying", "maint", "doors", "person", "lugBoot", "safety"};
        createTreeByGini(data, root, remainingAttribute, "decision");
    }
    
    public Tree createTreeByGini(String[][] data, Tree tree, String[] remainingAttribute, String result){
        int numberOfRemainingAttribute = 0; 
        for(int i = 0; i < remainingAttribute.length; i++){
            if(remainingAttribute[i].length() > 1){
                numberOfRemainingAttribute++;
            }
        }
        System.out.println("number of attribute: " + numberOfRemainingAttribute);
        if (numberOfRemainingAttribute == 0){
            
        }else{
            if (result.equals("decision")){
                Tree child = new Tree();
                //for(int z = 0; z < remainingAttribute.length; z++){

                //count the remaining attribute and calculate the gini index
                int[][] buyingMatrix = new int[2][4];
                int[][] maintMatrix = new int[2][4];
                int[][] doorsMatrix = new int[2][4];
                int[][] personsMatrix = new int[2][3];
                int[][] lugBootMatrix = new int[2][3];
                int[][] safetyMatrix = new int[2][3];
                double[] attributeGini = new double[6];
                for(int i = 0; i < data.length; i++){
                    //1 buying matrix
                    for(int j = 0; j < buyingValue.length; j++){
                        if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("unacc")){
                            buyingMatrix[0][j]++;
                        }
                        if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("acc")){
                            buyingMatrix[1][j]++;
                        }
                    }

                    //2 maint matrix
                    for(int j = 0; j < maintValue.length; j++){
                        if (data[i][1].equals(maintValue[j]) && data[i][6].equals("unacc")){
                            maintMatrix[0][j]++;
                        }
                        if (data[i][1].equals(maintValue[j]) && data[i][6].equals("acc")){
                            maintMatrix[1][j]++;
                        }
                    }

                    //3 doors matrix
                    for(int j = 0; j < doorsValue.length; j++){
                        if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("unacc")){
                            doorsMatrix[0][j]++;
                        }
                        if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("acc")){
                            doorsMatrix[1][j]++;
                        }
                    }

                    //4 persons matrix
                    for(int j = 0; j < personsValue.length; j++){
                        if (data[i][3].equals(personsValue[j]) && data[i][6].equals("unacc")){
                            personsMatrix[0][j]++;
                        }
                        if (data[i][3].equals(personsValue[j]) && data[i][6].equals("acc")){
                            personsMatrix[1][j]++;
                        }
                    }

                    //5 lug boot matrix
                    for(int j = 0; j < lugBootValue.length; j++){
                        if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("unacc")){
                            lugBootMatrix[0][j]++;
                        }
                        if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("acc")){
                            lugBootMatrix[1][j]++;
                        }
                    }

                    //6 safety Matrix
                    for(int j = 0; j < safetyValue.length; j++){
                        if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("unacc")){
                            safetyMatrix[0][j]++;
                        }
                        if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("acc")){
                            safetyMatrix[1][j]++;
                        }
                    }      
                }
                
                
                //calculate the gini index for different attribute
                for(int i = 0; i < attributeGini.length; i++){
                    attributeGini[i] = 1;
                }
                if (remainingAttribute[0].length() > 0){
                    attributeGini[0] = giniCalculator(buyingMatrix);
                }
                if (remainingAttribute[1].length() > 1){
                    attributeGini[1] = giniCalculator(maintMatrix);
                }
                if (remainingAttribute[2].length() > 1){
                    attributeGini[2] = giniCalculator(doorsMatrix);
                }
                if (remainingAttribute[3].length() > 1){
                    attributeGini[3] = giniCalculator(personsMatrix);
                }
                if (remainingAttribute[4].length() > 1){
                    attributeGini[4] = giniCalculator(lugBootMatrix);
                }
                if (remainingAttribute[5].length() > 1){
                    attributeGini[5] = giniCalculator(safetyMatrix);
                }
                
                double minimumGini = attributeGini[0];
                int chosenAttributeIndex = 0;
                String chosenAttribute = "";
                int grandChildrenNumber = 0;
                //find the minimum gini index
                for(int i = 0; i < attributeGini.length; i++){
                    if (minimumGini > attributeGini[i]){
                        minimumGini = attributeGini[i];
                        chosenAttributeIndex = i; 
                    }
                }
                chosenAttribute = attributes[chosenAttributeIndex];
                //chosen attribute matrix
                
                if(chosenAttributeIndex == 0){
                    double[][] chosenAttributeMatrix = new double[2][4];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        chosenAttributeMatrix[0][1] = 
                    }
                }
                
                
                
                //remove the used attribute
                for(int i = 0; i < remainingAttribute.length; i++){
                    if(remainingAttribute[i].equals(chosenAttribute)){
                        remainingAttribute[i] = "";
                    }
                }
                
                //change the data for the next generation/ trim the data
                for(int i = 0; i < data.length; i++){
                    if(!data[i][chosenAttributeIndex].equals(chosenAttribute)){
                        for(int j = 0; j < data[0].length; j++){
                            data[i][j] = "";
                        }
                    }
                }
                
                
                
                //create the decision tree and the attribute tree
                child.setValue(chosenAttribute);
                for(int i = 0; i < attributes.length; i++){
                    if (chosenAttributeIndex < 3){
                        grandChildrenNumber = 4;
                    }
                    if (chosenAttributeIndex >= 3){
                        grandChildrenNumber = 3;
                    }
                }
                String[] chosenAttributeValue = new String[grandChildrenNumber];
                for(int i = 0; i < attributes.length; i++){
                    if (chosenAttributeIndex == 0){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = buyingValue[j];
                        }
                    }
                    if (chosenAttributeIndex == 1){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = maintValue[j];
                        }
                    }
                    if (chosenAttributeIndex == 2){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = doorsValue[j];
                        }
                    }
                    if (chosenAttributeIndex == 3){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = personsValue[j];
                        }
                    }
                    if (chosenAttributeIndex == 4){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = lugBootValue[j];
                        }
                    }
                    if (chosenAttributeIndex == 5){
                        for (int j = 0; j < chosenAttributeValue.length;j++){
                            chosenAttributeValue[j] = safetyValue[j];
                        }
                    }
                }
                
                for(int i = 0; i < grandChildrenNumber; i++){
                    Tree grandChild = new Tree();
                    grandChild.setValue(chosenAttributeValue[i]);
                    child.addChild(grandChild);
                    if ()
                    createTreeByGini(data, grandChild, remainingAttribute, );
                    adfadsf
                }
                
                //}
                
            }else if(result.equals("unacc")){
                Tree child = new Tree();
                child.setValue("unacc");
                tree.addChild(child);

            }else if(result.equals("acc")){
                Tree child = new Tree();
                child.setValue("unacc");
                tree.addChild(child);
            }
        }
        
        
        return null; 
    }
    
    public void createTree(String[][] data, int numberOfAttributeRemaining){
        int[][] buyingMatrix = new int[2][4];
        int[][] maintMatrix = new int[2][4];
        int[][] doorsMatrix = new int[2][4];
        int[][] personsMatrix = new int[2][3];
        int[][] lugBootMatrix = new int[2][3];
        int[][] safetyMatrix = new int[2][3];
        double[] attributeGini = new double[6];
        
        double buyingGini = 0; 
        double maintGini = 0; 
        double doorsGini = 0; 
        double personsGini = 0; 
        double lugBootGini = 0;
        double safetyGini = 0; 
        for(int i = 0; i < data.length; i++){
            //1 buying matrix
            for(int j = 0; j < buyingValue.length; j++){
                if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("unacc")){
                    buyingMatrix[0][j]++;
                }
                if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("acc")){
                    buyingMatrix[1][j]++;
                }
            }
            
            //2 maint matrix
            for(int j = 0; j < maintValue.length; j++){
                if (data[i][1].equals(maintValue[j]) && data[i][6].equals("unacc")){
                    maintMatrix[0][j]++;
                }
                if (data[i][1].equals(maintValue[j]) && data[i][6].equals("acc")){
                    maintMatrix[1][j]++;
                }
            }
            
            //3 doors matrix
            for(int j = 0; j < doorsValue.length; j++){
                if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("unacc")){
                    doorsMatrix[0][j]++;
                }
                if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("acc")){
                    doorsMatrix[1][j]++;
                }
            }
            
            //4 persons matrix
            for(int j = 0; j < personsValue.length; j++){
                if (data[i][3].equals(personsValue[j]) && data[i][6].equals("unacc")){
                    personsMatrix[0][j]++;
                }
                if (data[i][3].equals(personsValue[j]) && data[i][6].equals("acc")){
                    personsMatrix[1][j]++;
                }
            }
            
            //5 lug boot matrix
            for(int j = 0; j < lugBootValue.length; j++){
                if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("unacc")){
                    lugBootMatrix[0][j]++;
                }
                if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("acc")){
                    lugBootMatrix[1][j]++;
                }
            }
            
            //6 safety Matrix
            for(int j = 0; j < safetyValue.length; j++){
                if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("unacc")){
                    safetyMatrix[0][j]++;
                }
                if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("acc")){
                    safetyMatrix[1][j]++;
                }
            }
        }
        
        attributeGini[0] = giniCalculator(buyingMatrix);
        attributeGini[1] = giniCalculator(maintMatrix);
        attributeGini[2] = giniCalculator(doorsMatrix);
        attributeGini[3] = giniCalculator(personsMatrix);
        attributeGini[4] = giniCalculator(lugBootMatrix);
        attributeGini[5] = giniCalculator(safetyMatrix);
        double minimumGini = attributeGini[0];
        double chosenAttribute = 0;
        for(int i = 0; i < attributeGini.length; i++){
            if (minimumGini > attributeGini[i]){
                minimumGini = attributeGini[i];
                chosenAttribute = i; 
            }
        }
        
        
        
        
        int counter = 0;
        for(int i = 0; i < buyingMatrix.length; i++){
            for(int j = 0; j < buyingMatrix[0].length; j++){
                counter += maintMatrix[i][j];
            }
        }
        for(int i = 0; i < attributeGini.length; i++){
            System.out.println("attribute " + i + " : " + attributeGini[i]);
        }
        for(int i = 0; i < buyingMatrix.length; i++){
            for(int j = 0; j < buyingMatrix[0].length; j++){
                System.out.print(buyingMatrix[i][j] + ", ");
            }
            System.out.println("");
        }
        
        
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
