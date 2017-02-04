package uk.ac.cam.cl.charlie.clustering;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Ben on 01/02/2017.
 */
public abstract class Clusterer {
    //Possible implentations:
    //KMeans, XMeans, EM, etc.
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    //Mailbox is private because only this class should update it, no inheriting class.
    //private Mailbox mailbox;

    //the vector array is only temporary until the Vectoriser is implemented.
    ArrayList<Vector<Double>> vecsForTesting = new ArrayList<Vector<Double>>();

    public ArrayList<Cluster> getClusters() {return clusters;}

    //for inserting a list of messages into their appropriate clusters, and updating the server.
    void classifyNewEmails(ArrayList<Message> messages) throws VectorElementMismatchException {
        //gets temp test vectors. Update once Vectoriser is implemented to getVecs(messages) or something.
        ArrayList<Vector<Double>> vecs = vecsForTesting;

        //For classification, find best clustering for each email using matchStrength().
        //TODO: Update mailbox accordingly.
        double bestMatch = Integer.MAX_VALUE;
        int bestCluster = 0;
        for (int i = 0; i < messages.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                double currMatch = clusters.get(j).matchStrength(vecs.get(i));
                if (currMatch < bestMatch) {
                    bestMatch = currMatch;
                    bestCluster = j;
                }
            }
            clusters.get(bestCluster).addMessage(messages.get(i));
        }
    }

    //Only identifies clustering metadata. uk.ac.cam.cl.charlie.clustering.Clusterer will actually assign emails based on the metadata.
    protected abstract ArrayList<Cluster> run(ArrayList<Message> vecs) throws Exception;


    void evalClusters(ArrayList<Message> messages) {
        //main method for evaluating clusters.
        //precondition: all Messages in 'message' are clear for clustering i.e. are not in protected folders.
        //call training methods in Vectoriser. If Vectorising model doesn't require training, these will be blank anyway.
        //postcondition: 'clusters' contains the new clustering, and all emails are in their new clusters on the server.


        //gets centroids of clusters.
        try {
            clusters = run(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //TODO: generate and assign clustering names.

        //TODO: update server with new clusters.
        return;
    }


/*
    //-------------------------------------------------------------------------------
    //Temp/test code:

    //Temp code for generating test vector file.
    public static void main(String args[]) throws Exception{

        FileWriter writer = new FileWriter("testVecs.arff");
        BufferedReader in = new BufferedReader(new FileReader("iris.arff"));
        writer.write("@RELATION vectors \n");
        for (int i = 0; i < 4; i++) {
            writer.write("@ATTRIBUTE e" + i + " \n");
        }
        writer.write("\n@DATA\n");

        String currLine;
        try {
            int i = 0;
            while (i < 299) {
                currLine = in.readLine();
                if (currLine == null)
                    break;
                if (!currLine.equals("")) {
                    String[] tokens = currLine.split(",");
                    currLine = tokens[0]+","+tokens[1]+","+tokens[2]+","+tokens[3];
                    writer.write(currLine+"\n");
                    writer.flush();
                }
            }
        } catch (Exception e) {}
        writer.close();
        in.close();
    }



    //temp test function, for use until Vectoriser is implemented.
    private ArrayList<Vector<Double>> getTestVecs(ArrayList<Message> messages) {
        ArrayList<Vector<Double>> vecs = new ArrayList<Vector<Double>>();

        int dimensionality = 5;
        for (int i = 0; i < messages.size(); i++) {
            Vector<Double> v = new Vector<Double>();
            for (int j = 0; j < dimensionality; j++) {
                v.add(java.lang.Math.random() * 3);
            }
            vecs.add(v);
        }

        return vecs;
    }
*/
}
