/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW1;

import java.util.*;

/**
 *
 * @author tom_c
 */
public class Tree {
    public String value = "";
    public List<Tree> children = new ArrayList<Tree>();
    public double giniIndex = 0;
    
    public Tree(String value){
        this.value = value;
    }
    
    //set the index 
    public void setGiniIndex(double giniIndex){
        this.giniIndex = giniIndex;
    }
    
    //set the value of the node
    public void setValue(String value){
        //this.children = new ArrayList<Tree>();
        this.value = value;
    }
    
    //add child node 
    public void addChild(Tree child){
        children.add(child);
    }
    
    //get value of the node
    public String getValue(){
        return value;
    }
    
    //get child 
    public Tree getChild(int i){
        return children.get(i);
    }
    
    //get number of the children of a node
    public int numberOfChildren(){
        return children.size();
    }
    
}
