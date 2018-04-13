package Model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class GsonHandler {
    private Gson gson;

    public GsonHandler(){
        this.gson = new Gson();
    }

    public void writeJson(ArrayList<Joint> joints){
        String json = gson.toJson(joints);
        try{
            FileWriter fw = new FileWriter("db.json");
            fw.write(json);
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList readJson(String filename) throws FileNotFoundException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        return gson.fromJson(br, ArrayList.class);
    }
}
