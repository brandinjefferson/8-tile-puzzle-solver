/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.tile.puzzle.solver;

import java.util.ArrayList;
/**
 *
 * @author brand_000
 */
public class PuzzleState {
    
    /*
    Description: The current representation of the puzzle.
    */
    private final ArrayList<String> state;
    /*
    Description: The number of tiles still out of place.
    */
    private Integer fvalue;
    
    /*
    Description: An ID used when searching for this state.
    */
    private Integer puzzleID;
    
    /*
    Description: Initializes the puzzle's state and assigns an fvalue based
        on the number of out of place tiles.
    */
    public PuzzleState(ArrayList<String> givenState, Integer id){
        state = new ArrayList<>();
        state.addAll(givenState);
        puzzleID = id;
    }
    
    public void setFValue(){
        fvalue = 0;
        for (int i=0;i<9;i++){
            if (i==8 && !state.get(i).equals("0")) fvalue++;
            else if (!state.get(i).equals(String.valueOf(i+1))){
                fvalue++;
            }
        }
    }
    
    public ArrayList<String> getState(){
        return state;
    }
    
    public Integer getFValue(){
        return fvalue;
    }
    
    public Integer getID(){
        return puzzleID;
    }
    
}
