package Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Array;
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
        TypeToken<ArrayList<Joint>> token = new TypeToken<ArrayList<Joint>>() {};
        return gson.fromJson(br, token.getType());
    }
}
