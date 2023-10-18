/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author koern
 */
public class Leaderboard {
    private String strName;
    private String strTime;
    
    public Leaderboard(){}
    
    public Leaderboard(String strName, String strTime){
        this.strName = strName;
        this.strTime = strTime;
    }
    
    public ObservableList<Leaderboard> readLeaderboardFromJSON() {
    try (InputStream inputStream = getClass().getResourceAsStream("/resources/leaderboard.json");
         InputStreamReader reader = new InputStreamReader(inputStream)) {
        Gson gson = new Gson();
        Type leaderboardListType = new TypeToken<List<Leaderboard>>() {}.getType();
        List<Leaderboard> entries = gson.fromJson(reader, leaderboardListType);
        System.out.println(entries);
        return FXCollections.observableArrayList(entries);
    } catch (Exception e) {
        e.printStackTrace();
        return FXCollections.observableArrayList(); // Return an empty list on error
    }
    }
    public void writeLeaderboardToJSON(ObservableList<Leaderboard> olLeaderboard) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    List<Leaderboard> entries = olLeaderboard;
    String json = gson.toJson(entries);

    try {
        // Copy the JSON file from the resources to a temporary location
        InputStream resourceStream = getClass().getResourceAsStream("/resources/leaderboard.json");
        Path tempFile = Files.createTempFile("leaderboard", ".json");
        Files.copy(resourceStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        // Write the JSON data to the temporary file
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write(json);
        }

        // Optionally, you can replace the original file with the updated one
        Files.copy(tempFile, Path.of("src/resources/leaderboard.json"), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
        // Handle the error as needed
    }
}
    
    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }
    
    
}
