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
    public String[] testData = {"vhigh", "vhigh", "3", "more", "big", "med", "acc"};
    public String resultPerEntry = "";
    
    public static void main (String[] args){
        DecisionTree dt = new DecisionTree();
        //dt.train();
        dt.run();
    }
    
    public DecisionTree(){
    }
    //choose 3 types of methond amoong gini index, entropy, error
    public DecisionTree(String typesOfFormula){
    }
    
    //run
    public void run(){
        System.out.println("*******************************************");
        System.out.println("***Decision Tree for [data/car.training]***");
        System.out.println("*******************************************");
        System.out.println("");
        //retrieve the data
        readFile(trainFilename);
        
        //create the tree with gini index
        Tree root = new Tree("root");
        String[] remainingAttribute = {"buying", "maint", "doors", "person", "lugBoot", "safety"};
        createTreeByGini(data, root, remainingAttribute, "decision");
        
        //print the decision tree
        print(root, 0);
        
        //test the tree
        readFile(testFilename);
        int totalNumber = data.length;
        int correctNumber = 0;
        for(int i = 0; i < data.length; i++){
            testTree(root, data[i]);
            if(data[i][6].equals(resultPerEntry)){
                correctNumber++;
            }
        }
        
        //print the result
        System.out.println("");
        System.out.println("******************************************");
        System.out.println("***Test Results for [data/car.training]***");
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("training_mode gini_index ");
        System.out.println("matches " + correctNumber);
        System.out.println("test_rows " + data.length);
        System.out.println("overall " + ((double)correctNumber/data.length));
    }
    
    //create the tree by gini index
    public void createTreeByGini(String[][] data, Tree tree, String[] remainingAttribute, String result){
        int numberOfRemainingAttribute = 0; 
        //find the remaining attributes after every level
        for(int i = 0; i < remainingAttribute.length; i++){
            if(remainingAttribute[i].length() > 1){
                numberOfRemainingAttribute++;
            }
        }
        //shows the class when it reaches the lower level
        if (numberOfRemainingAttribute == 0){
            if(result.equals("unacc")){
                Tree child = new Tree("unacc");
                tree.addChild(child);
            }else if(result.equals("acc")){
                Tree child = new Tree("acc");
                tree.addChild(child);
            }else if(result.equals("decision")){
                Tree child = new Tree("unknown");
                tree.addChild(child);
            }
        }
        
        //if the the attribute 
        if (numberOfRemainingAttribute > 0){ 
            //is the next level is an attribute, not class 
            if (result.equals("decision")){
                //count the remaining attribute and calculate the gini index
                int[][] buyingMatrix = new int[classValue.length][buyingValue.length];
                int[][] maintMatrix = new int[classValue.length][maintValue.length];
                int[][] doorsMatrix = new int[classValue.length][doorsValue.length];
                int[][] personsMatrix = new int[classValue.length][personsValue.length];
                int[][] lugBootMatrix = new int[classValue.length][lugBootValue.length];
                int[][] safetyMatrix = new int[classValue.length][safetyValue.length];
                double[] attributeGini = new double[attributes.length];
                
                //find the minimum gini index
                for(int i = 0; i < attributeGini.length; i++){
                    attributeGini[i] = 1;
                }
                for(int i = 0; i < data.length; i++){
                    //1 count for buying matrix
                    for(int j = 0; j < buyingValue.length; j++){
                        if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("unacc")){
                            buyingMatrix[0][j]++;
                        }
                        if (data[i][0].equals(buyingValue[j]) && data[i][6].equals("acc")){
                            buyingMatrix[1][j]++;
                        }
                    }
                    //2 count for maint matrix
                    for(int j = 0; j < maintValue.length; j++){
                        if (data[i][1].equals(maintValue[j]) && data[i][6].equals("unacc")){
                            maintMatrix[0][j]++;
                        }
                        if (data[i][1].equals(maintValue[j]) && data[i][6].equals("acc")){
                            maintMatrix[1][j]++;
                        }
                    }
                    //3 count for doors matrix
                    for(int j = 0; j < doorsValue.length; j++){
                        if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("unacc")){
                            doorsMatrix[0][j]++;
                        }
                        if (data[i][2].equals(doorsValue[j]) && data[i][6].equals("acc")){
                            doorsMatrix[1][j]++;
                        }
                    }
                    //4 count for persons matrix
                    for(int j = 0; j < personsValue.length; j++){
                        if (data[i][3].equals(personsValue[j]) && data[i][6].equals("unacc")){
                            personsMatrix[0][j]++;
                        }
                        if (data[i][3].equals(personsValue[j]) && data[i][6].equals("acc")){
                            personsMatrix[1][j]++;
                        }
                    }
                    //5 count for lug boot matrix
                    for(int j = 0; j < lugBootValue.length; j++){
                        if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("unacc")){
                            lugBootMatrix[0][j]++;
                        }
                        if (data[i][4].equals(lugBootValue[j]) && data[i][6].equals("acc")){
                            lugBootMatrix[1][j]++;
                        }
                    }
                    //6 count for safety Matrix
                    for(int j = 0; j < safetyValue.length; j++){
                        if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("unacc")){
                            safetyMatrix[0][j]++;
                        }
                        if (data[i][5].equals(safetyValue[j]) && data[i][6].equals("acc")){
                            safetyMatrix[1][j]++;
                        }
                    }      
                }
                
                //only calculate the gini index for the attribute that's not used
                if (remainingAttribute[0].length() > 1){
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
                
                //find the minimum gini index and the chosen attribute index
                double minimumGini = attributeGini[0];
                int chosenAttributeIndex = 0;
                for(int i = 0; i < attributeGini.length; i++){
                    if (minimumGini > attributeGini[i]){
                        minimumGini = attributeGini[i];
                        chosenAttributeIndex = i; 
                    }
                }
                
                //find chosen attribute 
                String chosenAttribute = "";
                chosenAttribute = attributes[chosenAttributeIndex];
                
                //find chosen attribute matrix
                double[][] chosenAttributeMatrix = new double[2][3];
                if(chosenAttributeIndex == 0){
                    chosenAttributeMatrix = new double[2][4];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = buyingMatrix[i][j];
                    }
                }
                if(chosenAttributeIndex == 1){
                    chosenAttributeMatrix = new double[2][4];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = maintMatrix[i][j];
                    }
                }
                if(chosenAttributeIndex == 2){
                    chosenAttributeMatrix = new double[2][4];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = doorsMatrix[i][j];
                    }
                }
                if(chosenAttributeIndex == 3){
                    chosenAttributeMatrix = new double[2][3];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = personsMatrix[i][j];
                    }
                }
                if(chosenAttributeIndex == 4){
                    chosenAttributeMatrix = new double[2][3];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = lugBootMatrix[i][j];
                    }
                }
                if(chosenAttributeIndex == 5){
                    chosenAttributeMatrix = new double[2][3];
                    for(int i = 0; i < chosenAttributeMatrix.length; i++){
                        for(int j = 0; j < chosenAttributeMatrix[0].length; j++)
                        chosenAttributeMatrix[i][j] = safetyMatrix[i][j];
                    }
                }
                
                //add the decision node
                Tree child = new Tree(chosenAttribute);
                tree.addChild(child);
                child.setGiniIndex(minimumGini);
                
                //find the number of values of the decision
                int grandChildrenNumber = 0;
                for(int i = 0; i < attributes.length; i++){
                    if (chosenAttributeIndex < 3){
                        grandChildrenNumber = 4;
                    }
                    if (chosenAttributeIndex >= 3){
                        grandChildrenNumber = 3;
                    }
                }
                
                //find the values of the decision
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
                
                //add the values to the decision
                for(int i = 0; i < chosenAttributeValue.length; i++){
                    Tree grandChild = new Tree(chosenAttributeValue[i]);
                    child.addChild(grandChild);
                    
                    int dataNumberLine = 0; 
                    String[][] newData = new String[800][7];
                    //change the data for the next generation/ trim the data
                    for(int j = 0; j < data.length; j++){
                        if(!data[j][chosenAttributeIndex].equals(chosenAttributeValue[i])){
                            for(int k = 0; k < data[0].length; k++){
                                newData[j][k] = "";
                            }
                        }
                        if(data[j][chosenAttributeIndex].equals(chosenAttributeValue[i])){
                            for(int k = 0; k < data[0].length; k++){
                                newData[j][k] = data[j][k];
                            }
                        }
                        if(data[j][0].length() > 1){
                            dataNumberLine++;
                        }
                    }
                    
                    String[] newRemainingAttribute = new String[6];
                    for(int j = 0; j < newRemainingAttribute.length; j++){
                        newRemainingAttribute[j] = remainingAttribute[j];
                    }
                    for(int j = 0; j < newRemainingAttribute.length; j++){
                        if(remainingAttribute[j].equals(chosenAttribute)){
                            //System.out.println("used attribute: " + remainingAttribute[i]);
                            newRemainingAttribute[j] = "";
                        }
                        //System.out.println("new remaining attribute"+newRemainingAttribute[j]);
                    }
                    //System.out.println("number of data line: " + dataNumberLine);
                    if(chosenAttributeMatrix[0][i] != 0 && chosenAttributeMatrix[1][i] != 0){
                        createTreeByGini(newData, grandChild, newRemainingAttribute, "decision");
                    }
                    if(chosenAttributeMatrix[0][i] == 0){
                        createTreeByGini(newData, grandChild, newRemainingAttribute, "acc");
                    }
                    if(chosenAttributeMatrix[1][i] == 0){
                        createTreeByGini(newData, grandChild, newRemainingAttribute, "unacc");
                    }
                }
            }else if(result.equals("unacc")){
                Tree child = new Tree("unacc");
                tree.addChild(child);
            }else if(result.equals("acc")){
                Tree child = new Tree("acc");
                tree.addChild(child);
            }
        }
    }
    
    //test the tree
    public String testTree(Tree tree, String[] data){
        if (tree.getChild(0).getValue().equals("acc")){
            //System.out.println("acc");
            resultPerEntry = "acc";
            return "acc";
        }else if (tree.getChild(0).getValue().equals("unacc")){
            //System.out.println("unacc");
            resultPerEntry = "unacc";
            return "unacc";
        }else{
            String dataType = "";
            String dataValue = "";
            int dataValueIndex = 0; 

            for(int i = 0; i < attributes.length; i++){
                if (tree.getChild(0).getValue().equals(attributes[i])){
                    dataValueIndex = i;
                    dataType = attributes[i];
                }
            }
            dataValue = data[dataValueIndex];
            for(int i = 0; i < tree.getChild(0).numberOfChildren(); i++){
                if (tree.getChild(0).getChild(i).getValue().equals(dataValue)){
                    testTree(tree.getChild(0).getChild(i), data);
                }
            }
            return null;
        }
    }
    
    public void findResult(Tree tree, String[] data, int dataIndex){
        String result = "";
        for(int i = 0; i < 4; i++){
            result = tree.getChild(i).getChild(0).getValue();
            if(data[dataIndex].equals(tree.getChild(i).getValue()) && result.equals("acc")){
                
            }
        }
    }
    
    //print the tree
    public void print(Tree tree, int space){
        //System.out.print(space);
        for(int i = space; i > 0; i--){
            System.out.print("--");
        }
        if(tree.numberOfChildren() > 0){
            System.out.print("node: " + tree.getValue());
            if(tree.giniIndex > 0){
                System.out.print(",    Gini Index = " + tree.giniIndex);
            }
            System.out.println("");
        }else{
            System.out.println("class: " + tree.getValue());
        }
        for(int i = 0; i < tree.numberOfChildren(); i++){
            print(tree.getChild(i), space + 1);
        }
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
            //System.out.println("number of line: " + numberOfLine);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return numberOfLine;
    }
}
