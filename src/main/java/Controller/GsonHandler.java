package Controller;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class GsonHandler {
    private Gson gson = new Gson();


    public void write(HashMap<String, String> out){
        String json = gson.toJson(out);
        try{
            FileWriter fw = new FileWriter("db.json");
            fw.write(json);
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public HashMap read(String filename){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(br, HashMap.class);
    }
}
