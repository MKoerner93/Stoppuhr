/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popups;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import controller.MainViewController;
import javafx.collections.ObservableList;
import utility.Leaderboard;
import utility.SortTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author koern
 */
public class AlertBox implements EventHandler<ActionEvent>, Initializable{
    
    private static Button buttAccept = new Button("Ja");
    private static Button buttDecline = new Button("Nein");
    private static Button buttOk = new Button("Ok");
    
    private static Label labTitleMessage = new Label();
    private static Label labNameTime = new Label();
    
    private static String strName;
    private static String strTime;
    
    private static ObservableList<Leaderboard> olLeaderboard;
    public static void standardDisplay(String title, String message){    
       Stage window = new Stage();
       VBox layVert = new VBox();
       HBox layHor = new HBox();
       Scene scene = new Scene(layVert);
       
       String strNameTime;       
       
       labTitleMessage.setText(message);
       
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle(title);
       window.setMinWidth(250);
       
       layHor.getChildren().addAll(buttOk);
       layHor.setAlignment(Pos.CENTER);
       layHor.setMargin(buttOk, new Insets(5,5,5,5));
       
       layVert.getChildren().addAll(labTitleMessage, layHor);
       layVert.setAlignment(Pos.CENTER);
       layVert.setMargin(layVert, new Insets(5,5,5,5));
       
       window.setScene(scene);
       buttOk.setOnAction(e -> window.close());
       window.showAndWait();
    }
    
    public static ObservableList<Leaderboard> addTimeDisplay(String title, String message, String name, String time){
       Stage window = new Stage();
       strName = name;
       strTime = time;
       VBox layVert = new VBox();
       HBox layHor = new HBox();
       Scene scene = new Scene(layVert);
       
       String strNameTime;
       
       
       
       strNameTime = name + ", " + time;
       labTitleMessage.setText(message);
       labNameTime.setText(strNameTime);
       
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle(title);
       window.setMinWidth(250);
       
       
       
       
       layHor.getChildren().addAll(buttAccept, buttDecline);
       layHor.setAlignment(Pos.CENTER);
       layHor.setMargin(buttOk, new Insets(5,5,5,5));
       layHor.setMargin(buttDecline, new Insets(5,5,5,5));
       
       layVert.getChildren().addAll(labTitleMessage, labNameTime, layHor);
       layVert.setAlignment(Pos.CENTER);
       layVert.setMargin(layVert, new Insets(5,5,5,5));
       
       window.setScene(scene);
       buttAccept.setOnAction((e) -> {
           MainViewController mvc = new MainViewController();
           olLeaderboard = dataToTable(mvc);
           mvc.setOlLeaderboard(olLeaderboard);
           window.close();
       });
       buttDecline.setOnAction(e-> window.close());
       window.showAndWait();
       return olLeaderboard;
    }
    
    private static Comparator<Leaderboard> timeComparator = (entry1, entry2) -> {
        String time1 = entry1.getStrTime();
        String time2 = entry2.getStrTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss,SSS");
        
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            
            return date1.compareTo(date2);
        } catch(ParseException e){
            e.printStackTrace();
            return 0;
        }
    };
    
    private static ObservableList<Leaderboard> dataToTable(MainViewController mvc) {
        SortTable st = new SortTable(); 
        ObservableList<Leaderboard> olLeaderboard = mvc.getOlLeaderboard();
        olLeaderboard = st.insertNewEntryAndSort(olLeaderboard, strName, strTime);
        FXCollections.sort(olLeaderboard, timeComparator);
        return olLeaderboard;
        
       
    }
    
    
    @Override
    public void handle(ActionEvent t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        //buttAccept.setOnAction(e-> mvc.setOlLeaderboard(olLeaderboard));
    }
}
