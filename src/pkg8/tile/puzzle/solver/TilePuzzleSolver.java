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
    static ArrayList<Integer> puzzle = new ArrayList<>();
    /*
    Description: Contains the options that were deemed the least costly to
    reach the end result.
    */
    static ArrayList<PuzzleState> closed = new ArrayList<>();
    /*
    Description: Contains all of the possible options of movement that haven't
    been chosen yet.
    */
    static PriorityQueue<PuzzleState> open = new PriorityQueue<>();
    /*
    Description: The current ID value to use for a new puzzle state.
    */
    static int curID = 0;
    /*
    Description: The current goal state.
    */
    static ArrayList<Integer> goalState = new ArrayList<>();
    static Scanner reader = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting State");
            puzzle = initPuzzle();
            System.out.println("Goal State");
            goalState = initPuzzle();
            if (isSolvable()){
                AStar();
            }
            else {
                System.out.println("No solvable route available.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static ArrayList<Integer> initPuzzle() throws Exception {
        System.out.println("Insert the numbers 1 through 9 in any order with no spaces in between. "
                + "9 represents the space.");
        System.out.print("Enter: ");
        String settings = new String();
        if (reader.hasNextLine()) settings = reader.nextLine();
        String[] temp = settings.split("");
        ArrayList<Integer> t2 = new ArrayList<>();
        while (!checkLength(temp.length) && !checkNumbers(temp)){
            System.out.println("Follow the directions.");
            System.out.print("Enter: ");
            settings = reader.nextLine();
            temp = settings.split("");
        }
        for (String t : temp){
            t2.add(Integer.parseInt(t));
        }
        return t2;
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
    
    public static void displayGrid(ArrayList<Integer> curWorld){
        for (int i=0;i<9;i++){
            if ((i+1)%3 == 0)
                if (curWorld.get(i) == 9) System.out.println(" ");
                else System.out.println(curWorld.get(i));
            else 
                if (curWorld.get(i) == 9) System.out.print(" ");
                else System.out.print(curWorld.get(i));
        }
    }
    
    public static void AStar(){
        PuzzleState curWorld = new PuzzleState(puzzle,curID);
        curWorld.setFValue(goalState, 0);
        open.add(curWorld);
        curID++;
        Integer level = 0;
        while (!open.isEmpty()){
            curWorld = open.poll();
            //System.out.println(curWorld.getState());
            level++;
            if (curWorld.check(goalState)){
                System.out.println("Printing results.");
                /*closed.add(curWorld);
                for (PuzzleState fin : closed){
                    displayGrid(fin.getState());
                    System.out.println();
                }*/
                //Work on displaying. May be other probs, but this is one.
                Stack<PuzzleState> display = new Stack<>();
                while (curWorld != null){
                    display.push(curWorld);
                    curWorld = curWorld.getParent();
                }
                while (!display.empty()){
                    curWorld = display.pop();
                    displayGrid(curWorld.getState());
                }
                return;
            }
            else {
                ArrayList<PuzzleState> children = generateChildren(curWorld.getState());
                //System.out.println("Children made.");
                for (PuzzleState child : children){
                    Boolean inOpen = false,inClosed = false;
                    if (open.contains(child)) inOpen = true;
                    if (closed.contains(child)) inClosed = true;
                    child.setFValue(goalState,level);
                    //System.out.println("F Value: " + child.getFValue());
                    if (!inOpen && !inClosed){
                        //System.out.println("Open and Closed don't contain child.");
                        child.setParent(curWorld);
                        open.add(child);
                    }
                    else if (inOpen){
                        //System.out.println("Open contains child.");
                        Iterator<PuzzleState> it = open.iterator();
                        while (it.hasNext()){
                            PuzzleState oldChild = it.next();
                            if (oldChild.equals(child) &&
                                    oldChild.getFValue() > child.getFValue()){
                                open.remove(oldChild);
                                open.add(child);
                            }
                        }
                    }
                    else if (inClosed){
                        //System.out.println("Closed contains child.");
                        for (int i=0;i<closed.size();i++){
                            PuzzleState it = closed.get(i);
                            if (it.equals(child) &&
                                    it.getFValue() > child.getFValue()){
                                closed.remove(i);
                                //System.out.println(it.getFValue());
                                open.add(child);
                                //System.out.println(child.getFValue());
                            }
                        }
                    }
                }
            }
            closed.add(curWorld);
            //System.out.println("End of iteration.");
        }
        System.out.println("There was no solution.");
    }
    
    
    
    private static ArrayList<PuzzleState> generateChildren(ArrayList<Integer> x){
        //System.out.println("Generating children.");
        //Search for the blank space
        int pos = -1;
        for (int i=0;i<9;i++){
            if (x.get(i) == 9){
                pos = i;
            }
            if (pos > -1) break;
        }
        if (pos == -1){
            System.out.println("0 not found.");
            //return;
        }
        
        ArrayList<Integer> temp = new ArrayList<>();
        PuzzleState curWorld;
        ArrayList<PuzzleState> children = new ArrayList<>();
        Integer cur;
        //Check if north move is possible.
        if (pos > 2) {
            //System.out.println("North");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos-3);
            temp.set(pos-3, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            //System.out.println(curWorld.getState());
            curID++;
        }
        //Check if south move is possible.
        if (pos < 6){
            //System.out.println("South");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos+3);
            temp.set(pos+3, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            //System.out.println(curWorld.getState());
            curID++;
        }
        //Check if west move is possible.
        if (pos%3 != 0){
            //System.out.println("West");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos-1);
            temp.set(pos-1, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            //System.out.println(curWorld.getState());
            curID++;
        }
        //Check if east move is possible
        if ((pos+1)%3 != 0){
            //System.out.println("East");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos+1);
            temp.set(pos+1, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp,curID);
            children.add(curWorld);
            //System.out.println(curWorld.getState());
            curID++;
        }
        return children;
    }
    
    /**
     * Description: Checks the number of inversions; if it is positive, then 
     * true is returned.
     * @return 
     */
    private static boolean isSolvable(){
        Integer current, inversions=0;
        for (int i=0;i<9;i++){
            current = puzzle.get(i);
            if (current != 9){
                for (int j=(i+1);j<9;j++){
                    if (current > puzzle.get(j)) inversions++;
                }
            }
        }
        if (inversions%2==0) return true;
        
        return false;
    }
    
}
