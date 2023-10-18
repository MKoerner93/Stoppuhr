/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;


import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import popups.AlertBox;
import utility.*;

/**
 * FXML Controller class
 *
 * @author koern
 */
public class MainViewController implements Initializable, EventHandler<ActionEvent> {
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label labName = new Label();
    @FXML
    private Label labTime = new Label();
    @FXML
    private Label labTimeName = new Label();
    
    @FXML
    private TextField tfName = new TextField();
    
    @FXML
    private Button buttStart = new Button();
    @FXML
    private Button buttStopp = new Button();
    
    @FXML
    private TableView<Leaderboard> tvMainTable = new TableView<>();
    
    @FXML
    private TableColumn<Leaderboard, String> tcName = new TableColumn<>("Name");
    @FXML
    private TableColumn<Leaderboard, String> tcTime = new TableColumn<>("Zeit");
    
    private Leaderboard leaderboard = new Leaderboard();
    
    private ObservableList<Leaderboard> olLeaderboard = leaderboard.readLeaderboardFromJSON();
    
    private long startTime;
    
    private Timeline timeline;
    
    private boolean running = false;
    
    
    public MainViewController(){}
    
    public MainViewController(ObservableList<Leaderboard> olLeaderboard){
        this.olLeaderboard = olLeaderboard;
    }

 
    private void startTimer() {
        if (!running && !tfName.getText().trim().isEmpty()) {
            startTime = System.currentTimeMillis();
            timeline.play();
            running = true;
        } else if(tfName.getText().trim().isEmpty()){
            AlertBox.standardDisplay("Error", "Bitte einen Namen eingeben");
        }
    }
    
    private void stopTimer() {
        if (running) {
            timeline.stop();
            running = false;
            olLeaderboard = AlertBox.addTimeDisplay("Speichern", "Soll die folgende Zeit gespeichert werden?", tfName.getText(), labTime.getText());
        } 
        refreshTable();
        Leaderboard lb = new Leaderboard();
        lb.writeLeaderboardToJSON(olLeaderboard);
    }
    
    private void updateTimer() {
    if (running) {
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - startTime) / 1000.0;
        
        int minutes = (int) (elapsedTime / 60);
        double seconds = elapsedTime % 60;
        
        DecimalFormat df = new DecimalFormat("00.000");
        String formattedTime = String.format("%02d:%s", minutes, df.format(seconds));
        
        labTime.setText(formattedTime);
    }
}
    
    public void refreshTable(){
        tcName.setCellValueFactory(new PropertyValueFactory<Leaderboard, String>("strName"));
        tcTime.setCellValueFactory(new PropertyValueFactory<Leaderboard, String>("strTime"));
        tvMainTable.setItems(olLeaderboard);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        buttStart.setOnAction(e -> startTimer());
        buttStopp.setOnAction(e -> stopTimer());
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        tvMainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
    }    

    public ObservableList<Leaderboard> getOlLeaderboard() {
        return olLeaderboard;
    }

    public void setOlLeaderboard(ObservableList<Leaderboard> olLeaderboard) {
        this.olLeaderboard = olLeaderboard;
        tvMainTable.refresh();
    }
    
    
    
    
    @Override
    public void handle(ActionEvent t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
    
}
