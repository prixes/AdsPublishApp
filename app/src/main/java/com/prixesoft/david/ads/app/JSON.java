package com.prixesoft.david.ads.app;

import android.content.ContextWrapper;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by david on 11-Mar-17.
 */

public class JSON {


    static JSONArray jsonPacket = new JSONArray();
    //Building creating and writing JSON file
    static void writeJSON(Boolean[] imageType, String imagePath[], String title, String category,
                                String subCategory, String description, String price, String currency,
                                String phone, Boolean shareFB, Boolean shareLoc,
                                File getFilesDir, MainActivity activity) throws JSONException {

        // Building whole JSON structure
        JSONObject jsonImage = new JSONObject();
        JSONArray jsonImages = new JSONArray();
        for(int i=0; i <= 5 ; i ++) {
            if( imagePath[i] !=null)
                jsonImage.put("type",imageType[i] == true ? "gallery" : "camera");
            jsonImage.put("path",imagePath[i]);
            jsonImages.put(jsonImage);
            jsonImage = new JSONObject();
        }


        JSONObject jsonGeneral = new JSONObject();
        jsonGeneral.put("images",jsonImages);
        jsonGeneral.put("title", title);
        jsonGeneral.put("category", category);
        jsonGeneral.put("subcategory", subCategory);
        jsonGeneral.put("description", description);
        jsonGeneral.put("price",price);
        jsonGeneral.put("currency",currency);
        jsonGeneral.put("phone",phone);
        jsonGeneral.put("should_fb_share",shareFB);
        jsonGeneral.put("should_show_location",shareLoc);


        jsonPacket.put(jsonGeneral);

        JSONObject json=new JSONObject();
        json.put("ads",jsonPacket);


        String path = "AdPublish.json";
        File file = new File(getFilesDir +  "/" + path);

        if(!file.exists()) {
            try {
                file.createNewFile();
                activity.displayMsgBox("nope!","error while creating the file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            // Writting json file to /data/data/com.digimark.david.app/files/AdPublish.json
            FileWriter fileWriter = new FileWriter(getFilesDir +  "/" + path);
            fileWriter.write(json.toString(2));
            fileWriter.flush();
            fileWriter.close();
            activity.displayMsgBox( (" Location: " + getFilesDir +  "/" + path)  , "Successful JSON file write");

        }catch (Exception e){
            System.err.println("Error: " + e);
            activity.displayMsgBox("nope!","error while writing the file");
        }

    }
}
