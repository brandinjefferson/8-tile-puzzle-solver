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
    static ArrayList<String> puzzle = new ArrayList<>();

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
    
}
