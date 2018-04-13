package Controller;

import Model.Joint;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class GsonHandler {
    private Gson gson = new Gson();


    public void write(ArrayList<Joint> joints){
        String json = gson.toJson(joints);
        try{
            FileWriter fw = new FileWriter("db.json");
            fw.write(json);
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList read(String filename){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(br, ArrayList.class);
    }
}
