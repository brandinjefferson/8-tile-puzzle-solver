/*
 * Description: A puzzle where the goal is to take a 3 x 3 grid filled with 
    the numbers 1-8 and a blank space in a random assortment and, using the most
    optimal path, reorder them so that they match a certain result. The path 
    works by moving a neigboring digit into the empty square.
 */
package pkg8.tile.puzzle.solver;

import java.util.*;


/**
 *
 * @author brand_000
 */
public class TilePuzzleSolver {
    /*
    Description: The current state of the grid.
    */
    static ArrayList<String> puzzle = new ArrayList<>();
    /*
    Description: Contains the options that were deemed the least costly to
    reach the end result.
    */
    static ArrayList<ArrayList<String>> closed = new ArrayList<>();
    /*
    Description: Contains all of the possible options of movement that haven't
    been chosen yet.
    */
    static PriorityQueue<PuzzleState> open = new PriorityQueue<>();
    /*
    Description: The current ID value to use for a new puzzle state.
    */
    static int curID = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initPuzzle();
        displayGrid();
    }
    
    public static void initPuzzle(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Insert the numbers 0 through 8 in any order with no spaces in between. "
                + "0 represents the space.");
        System.out.print("Enter: ");
        String settings = reader.nextLine();
        String[] temp = settings.split("");
        while (!checkLength(temp.length) && !checkNumbers(temp)){
            System.out.println("Follow the directions.");
            System.out.print("Enter: ");
            settings = reader.nextLine();
            temp = settings.split("");
        }
        for (String t : temp){
            puzzle.add(t);
        }
    }
    
    private static boolean checkLength(int length){
        return length == 9;
    }
    
    private static boolean checkNumbers(String[] array){
        ArrayList<String> array2 = new ArrayList<>();
        for (String t : array){
            if (array2.contains(t)) return false;
            switch (t){
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                    array2.add(t);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
    
    public static void displayGrid(){
        for (int i=0;i<9;i++){
            if ((i+1)%3 == 0)
                if (puzzle.get(i).equals("0")) System.out.println(" ");
                else System.out.println(puzzle.get(i));
            else 
                if (puzzle.get(i).equals("0")) System.out.print(" ");
                else System.out.print(puzzle.get(i));
        }
    }
    
    public static void AStar(){
        open = new PriorityQueue<>();
        closed = new ArrayList<>();
        ArrayList<String> curState;
        PuzzleState curWorld = new PuzzleState(puzzle,curID);
        open.add(curWorld);
        curID++;
        
        while (!open.isEmpty()){
            curState = open.poll().getState();
            if (checkGoal(curState)){
                //Display closed path
                //Exit function
            }
            else {
                ArrayList<PuzzleState> children = generateChildren();
                for (PuzzleState child : children){
                    
                }
            }
        }
    }
    
    /*
    Description: Compares the given puzzle's state to the goal state. If they
        are identical, then return true.
    */
    private static boolean checkGoal(ArrayList<String> curState){
        for (int i=0;i<8;i++){
            if (!curState.get(i).equals(String.valueOf(i+1))) return false;
        }
        return (curState.get(8).equals("0"));
    }
    
    private static ArrayList<PuzzleState> generateChildren(){
        //Search for the blank space
        int pos = -1;
        for (int i=0;i<9;i++){
            if (puzzle.get(i).equals("0")){
                pos = i;
            }
            if (pos > -1) break;
        }
        if (pos == -1){
            System.out.println("0 not found.");
            //return;
        }
        
        ArrayList<String> temp;
        PuzzleState curWorld;
        ArrayList<PuzzleState> children = new ArrayList<>();
        String cur;
        //Check if north move is possible.
        if (pos > 2) {
            temp = copyCurrentPuzzle();
            cur = temp.remove(pos-3);
            temp.add(pos-3, temp.remove(pos));
            temp.add(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            curID++;
        }
        //Check if south move is possible.
        if (pos < 6){
            temp = copyCurrentPuzzle();
            cur = temp.remove(pos+3);
            temp.add(pos+3, temp.remove(pos));
            temp.add(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            curID++;
        }
        //Check if west move is possible.
        if (pos%3 != 0){
            temp = copyCurrentPuzzle();
            cur = temp.remove(pos-1);
            temp.add(pos-1, temp.remove(pos));
            temp.add(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            curID++;
        }
        //Check if east move is possible
        if ((pos+1)%3 != 0){
            temp = copyCurrentPuzzle();
            cur = temp.remove(pos+1);
            temp.add(pos+1, temp.remove(pos));
            temp.add(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            curID++;
        }
        return children;
    }
    
    private static ArrayList<String> copyCurrentPuzzle(){
        ArrayList<String> t = new ArrayList<>();
        for (String i : puzzle){
            t.add(i);
        }
        return t;
    }
    
}
