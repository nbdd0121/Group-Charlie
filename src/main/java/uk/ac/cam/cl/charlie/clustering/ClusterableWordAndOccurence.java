package uk.ac.cam.cl.charlie.clustering;

import uk.ac.cam.cl.charlie.vec.TextVector;
import uk.ac.cam.cl.charlie.vec.tfidf.TfidfVectoriser;

import java.util.Vector;

/**
 * Created by M Boyce on 11/02/2017.
 */
public class ClusterableWordAndOccurence implements ClusterableObject {
    private String word;
    private int occurences;

    public ClusterableWordAndOccurence(String word,int occurences){
        this.word = word;
        this.occurences = occurences;
    }

    public String getWord(){
        return word;
    }

    public int getOccurences(){return occurences;}

    @Override
    public TextVector getVec() {
        //TODO: Deal with possibility that word does not exist, NoSuchElementException thrown.
        return GenericClusterer.getVectoriser().word2vec(word).get();
    }
}
