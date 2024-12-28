package rmi_api;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;

import java.io.Serializable;


public class BodyResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes




    public String chatbootResp;

    public List<Annonce> annonceList;

    public HashMap<String, String> Statistics = new HashMap<>();

}
