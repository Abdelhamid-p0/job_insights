package rmi_api;



import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.Serializable;
import org.apache.commons.lang3.tuple.Pair;



public class BodyResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes




    public String chatbootResp;

    public List<Annonce> annonceList;

    public ArrayList<Pair<String, Integer>> Statistics = new ArrayList<>();

}
