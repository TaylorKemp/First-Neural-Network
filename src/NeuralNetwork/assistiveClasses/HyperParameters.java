/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetwork.assistiveClasses;

import NeuralNetwork.costFunctions.CostFunction;
import NeuralNetwork.costFunctions.CostPrime;

/**
 *
 * @author tkemp
 */
public class HyperParameters {
    private Integer epochs;
    private Integer miniBatchSize;
    private Double learnRate;
    private CostFunction cost;
    private CostPrime costPrime;
    
    public void setEpochs(Integer epochs){
        this.epochs = epochs;
    }
    
    public Integer getEpochs(){
        return this.epochs;
    }
    
    public void setLearnRate(Double learnRate){
        this.learnRate = learnRate;
    }
    
    public Double getLearnRate(){
        return this.learnRate;
    }
    
    public void setminiBatchSize(Integer miniBatchSize){
        this.miniBatchSize = miniBatchSize;
    }
    
    public Integer getMiniBatch(){
        return this.miniBatchSize;
    }
    
    public void setCost(CostFunction cost){
        this.cost = cost;
    }
    
    public CostFunction getCost(){
        return this.cost;
    }
    
    public void setCostPrime(CostPrime costPrime){
        this.costPrime = costPrime;
    }
    
    public CostPrime getCostPrime(){
        return this.costPrime;
    }
    
    public HyperParameters(Integer epochs, Integer miniBatchSize, Double learnRate, 
            CostFunction cost, CostPrime costPrime){
        this.epochs = epochs;
        this.miniBatchSize = miniBatchSize;
        this.learnRate = learnRate;
        this.cost = cost;
        this.costPrime = costPrime;
        
    }
}
