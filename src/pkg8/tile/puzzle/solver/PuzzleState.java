package pkg8.tile.puzzle.solver;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Description: Represents an instance of a puzzle case.
 * @author bej0843
 */
public class PuzzleState implements Comparator, Comparable {
    
    /*
    Description: The current representation of the puzzle.
    */
    private final ArrayList<Integer> state;
    /*
    Description: The sum of distances between the tiles and their goal positions.
    */
    private Integer fvalue;
    
    /**
     * Description: The current level of this child; represents g(n).
     */
    private Integer depth;
    /**
     * Description: The child's parent state.
     */
    private PuzzleState parent = null;
    
    /**
     * Description: Initializes the puzzle's state.
     * @param givenState The current state of the puzzle in array form.
     */
    public PuzzleState(ArrayList<Integer> givenState){
        state = new ArrayList<>();
        state.addAll(givenState);
        depth = 0;
        fvalue = 0;
    }

    /**
     * Description: Returns this state's parent.
     * @return 
     */
    public PuzzleState getParent() {
        return parent;
    }

    /**
     * Description: Sets this state's parent.
     * @param parent 
     */
    public void setParent(PuzzleState parent) {
        this.parent = parent;
    }
    
    
    /**
     * Description: Sets the FValue according to the sum of distances heuristic.
     * @param goal
     * @param d The depth [g(n)]
     */
    public void setFValue(ArrayList<Integer> goal, Integer d){
        depth = d;
        Integer[] coord;
        int sum = 0;
        for (int i=0;i<9;i++){
            Integer cur = this.state.get(i); //The current value
            Integer pos = goal.indexOf(cur); //Its location in the goal state
            coord = findCoordinates(i,pos);
            sum = Math.abs((coord[2] - coord[0]) + (coord[3] -coord[1])) + sum;
        }
        fvalue = depth + sum;
    }
    
    /**
     * Description: Returns an integer array containing the coordinates of the
     * given variables if you were to look at the 8-puzzle array as a grid. Ex:
     * Index 0 would be coordinates (1,1).
     * @param start The array position the number is current at.
     * @param goal The array position it needs to reach.
     * @return 
     */
    private Integer[] findCoordinates(Integer start, Integer goal){
        Integer [] coord = new Integer[4];
        
        switch (start){
            case 0:
                coord[0] = 1; coord[1] = 1;
                break;
            case 1: 
                coord[0] = 2; coord[1] = 1;
                break;
            case 2:
                coord[0] = 3; coord[1] = 1;
                break;
            case 3:
                coord[0] = 1; coord[1] = 2;
                break; 
            case 4:
                coord[0] = 2; coord[1] = 2;
                break;
            case 5:
                coord[0] = 3; coord[1] = 2;
                break;
            case 6:
                coord[0] = 1; coord[1] = 3;
                break;
            case 7:
                coord[0] = 2; coord[1] = 3;
                break;
            case 8:
                coord[0] = 3; coord[1] = 3;
                break;
            default:
                coord[0] = 0; coord[1] = 0;
        }
        switch (goal){
            case 0:
                coord[2] = 1; coord[3] = 1;
                break;
            case 1: 
                coord[2] = 2; coord[3] = 1;
                break;
            case 2:
                coord[2] = 3; coord[3] = 1;
                break;
            case 3:
                coord[2] = 1; coord[3] = 2;
                break; 
            case 4:
                coord[2] = 2; coord[3] = 2;
                break;
            case 5:
                coord[2] = 3; coord[3] = 2;
                break;
            case 6:
                coord[2] = 1; coord[3] = 3;
                break;
            case 7:
                coord[2] = 2; coord[3] = 3;
                break;
            case 8:
                coord[2] = 3; coord[3] = 3;
                break;
            default:
                coord[2] = 0; coord[3] = 0;
        }
        
        return coord;
    }
    
    /**
     * Description: Returns the state.
     * @return 
     */
    public ArrayList<Integer> getState(){
        return state;
    }
    
    /**
     * Description: Returns the case's f-value.
     * @return 
     */
    public Integer getFValue(){
        return fvalue;
    }
    
    /**
     * Description: Returns the case's depth.
     * @return 
     */
    public Integer getDepth(){
        return depth;
    }
    
    /**
     * Description: Compares the current state to a given one. Returns true
     * they are equal.
     * @param given - A state of the puzzle to checked against. 
     * @return 
     */
    public boolean check(ArrayList<Integer> given){
        return (this.state.equals(given));
    }
    
    /**
     * Description: Overrides Java's equal method so that, when searching for 
     * an object that should be equal to the calling case, only their state
     * arrays are compared.
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        PuzzleState o1 = (PuzzleState)o;
        return (this.state.equals(o1.state));
    }
    
    /**
     * Description: Overrides Java's compare method, so that if two PuzzleState
     * cases are compared, it's only their state arrays that get compared.
     * @param one
     * @param two
     * @return 
     */
    @Override
    public int compare(Object one, Object two){
        PuzzleState o1 = (PuzzleState)one;
        PuzzleState o2 = (PuzzleState)two;
        
        if (o1.state.equals(o2.state)) return 1;
        else return 0;
    }
    
    /**
     * Description: For comparisons between the fvalues of states in the heap; 
        Returns -1 if the current value is less than the given one, 1 if it is
        greater, and a 0 if they are equal.
     * @param one
     * @return 
     */
    @Override
    public int compareTo(Object one){
        if (this == one) return 0;
        
        PuzzleState o1 = (PuzzleState)one;
        if (this.fvalue > o1.fvalue) return 1;
        else if (this.fvalue < o1.fvalue) return -1;
        else return 0; //Equal
    }
    
}
