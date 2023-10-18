/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.util.Collections;
import javafx.collections.ObservableList;

/**
 *
 * @author koern
 */
public class SortTable {
    
    public ObservableList<Leaderboard> insertNewEntryAndSort(
            ObservableList<Leaderboard> olLeaderboard, 
            String strName, 
            String strTime){
        try{
            Leaderboard newEntry = new Leaderboard(strName, strTime);
            olLeaderboard.add(newEntry);
            olLeaderboard.sorted();
            return olLeaderboard;
        }catch(Exception e){
            e.printStackTrace();
            return olLeaderboard;
            
        }
    }
}
