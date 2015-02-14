/* Name: 8 Puzzle Solver
 * Description: A puzzle where the goal is to take a 3 x 3 grid filled with 
    the numbers 1-8 and a blank space in a random assortment and, using the most
    optimal path through the A* algorithm, reorder them so that they match a 
    certain result. The path works by moving a neigboring digit into the empty 
    square.
 * Input: A set of 9 digits together with no spaces twice. First to represent
    the starting state and second to represent the goal state.
 * Output: A list of states that display the most optimal path from the starting
    state to the goal state. Each is in grid format.
 */
package pkg8.tile.puzzle.solver;

import java.util.*;


/**
 * Description: Solves any 8 puzzle given, if it's solvable.
 * @author bej0843
 */
public class TilePuzzleSolver {
    /**
    Description: The initial state of the grid.
    */
    static ArrayList<Integer> puzzle = new ArrayList<>();
    /**
    Description: Contains the options that were deemed the least costly to
    reach the end result.
    */
    static ArrayList<PuzzleState> closed = new ArrayList<>();
    /**
    Description: Contains all of the possible options of movement that haven't
    been chosen yet.
    */
    static PriorityQueue<PuzzleState> open = new PriorityQueue<>();
    /**
    Description: The current goal state.
    */
    static ArrayList<Integer> goalState = new ArrayList<>();
    /**
     * Description: Reads user's input.
     */
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
            System.out.println("Exception");
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    /**
     * Description: Asks the user for input to initialize the puzzle's state.
     * @return
     * @throws Exception 
     */
    public static ArrayList<Integer> initPuzzle() throws Exception {
        System.out.println("Insert the numbers 1 through 9 in any order with no spaces in between. "
                + "9 represents the space.");
        System.out.print("Enter: ");
        String settings = new String();
        if (reader.hasNextLine()) settings = reader.nextLine();
        String[] temp = settings.split("");
        ArrayList<Integer> t2 = new ArrayList<>();
        while (!checkLength(temp.length) || !checkNumbers(temp)){
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
    
    /**
     * Description: Checks the length of the array and returns true if it is 
     * equal to 9.
     * @param length
     * @return 
     */
    private static boolean checkLength(int length){
        return length == 9;
    }
    
    /**
     * Description: Checks the numbers within the input to ensure it is correct. 
     * Return true if the numbers are valid.
     * @param array - The numbers in the input
     * @return 
     */
    private static boolean checkNumbers(String[] array){
        ArrayList<String> array2 = new ArrayList<>();
        for (String t : array){
            if (array2.contains(t)) return false;
            switch (t){
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    array2.add(t);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Description: Displays the given state as a 3 x 3 grid.
     * @param curWorld - The given state.
     */
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
    
    /**
     * Description: Implements the A* algorithm modified for the 8 puzzle. 
     */
    public static void AStar(){
        PuzzleState curWorld = new PuzzleState(puzzle);
        curWorld.setFValue(goalState,0);
        open.add(curWorld);
        while (!open.isEmpty()){
            curWorld = open.poll();
            //System.out.println(curWorld.getState());
            if (curWorld.check(goalState)){
                System.out.println("Printing results.");
                //Work on displaying. May be other probs, but this is one.
                Stack<PuzzleState> display = new Stack<>();
                while (curWorld != null){
                    display.push(curWorld);
                    curWorld = curWorld.getParent();
                }
                while (!display.empty()){
                    curWorld = display.pop();
                    displayGrid(curWorld.getState());
                    System.out.println("-");
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
                    child.setFValue(goalState,curWorld.getDepth()+1);
                    child.setParent(curWorld);
                    if (!inOpen && !inClosed){
                        //System.out.println("Open and Closed don't contain child.");
                        open.add(child);
                    }
                    //Something must be wrong with this.
                    else if (inOpen){
                        //System.out.println("Open contains child.");
                        Iterator<PuzzleState> it = open.iterator();
                        while (it.hasNext()){
                            PuzzleState oldChild = it.next();
                            if (oldChild.equals(child) &&
                                    oldChild.getFValue() > child.getFValue()){
                                open.remove(oldChild);
                                open.add(child);
                                break;
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
                                break;
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
    
    /**
     * Description: Generates the children of a given state.
     * @param x
     * @return 
     */
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
            System.out.println("9 not found.");
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
            curWorld = new PuzzleState(temp);
            children.add(curWorld);
        }
        //Check if south move is possible.
        if (pos < 6){
            //System.out.println("South");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos+3);
            temp.set(pos+3, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp);
            children.add(curWorld);
        }
        //Check if west move is possible.
        if (pos%3 != 0){
            //System.out.println("West");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos-1);
            temp.set(pos-1, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp);
            children.add(curWorld);
        }
        //Check if east move is possible
        if ((pos+1)%3 != 0){
            //System.out.println("East");
            temp.clear();
            temp.addAll(x);
            cur = temp.get(pos+1);
            temp.set(pos+1, temp.get(pos));
            temp.set(pos,cur);
            curWorld = new PuzzleState(temp);
            children.add(curWorld);
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
            for (int j=(i+1);j<9;j++){
                if (current > puzzle.get(j)) inversions++;
            }
        }
        if (inversions%2==0) return true;
        
        return false;
    }
    
}
