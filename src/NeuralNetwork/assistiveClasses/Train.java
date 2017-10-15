package NeuralNetwork.assistiveClasses;

import NeuralNetwork.assistiveClasses.Evaluate;
import NeuralNetwork.assistiveClasses.HyperParameters;
import NeuralNetwork.assistiveClasses.Network;
import NeuralNetwork.assistiveClasses.SGD;
import NeuralNetwork.costFunctions.CostFunction;
import NeuralNetwork.costFunctions.CostPrime;
import deeplearning2.pkg1.RunNetworkController;

public class Train {
        public static Network trainNetwork(Network net, RunNetworkController controller,
                HyperParameters parameters, Integer sampleSize){
            double lambda = 0.0;
            Double learnRate = parameters.getLearnRate();
            Integer batchSize = parameters.getMiniBatch();
            Integer epoch = parameters.getEpochs();
            CostFunction cost = parameters.getCost();
            CostPrime costPrime = parameters.getCostPrime();
                
            net.setLearningRate(learnRate);
            SGD.sGD(net, controller, net.images, net.labels, batchSize, epoch, cost, costPrime, sampleSize, lambda);
            Evaluate.testNetwork(net, -1);	
            
            return net;
        }			
}