/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.tile.puzzle.tests;

import java.util.ArrayList;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pkg8.tile.puzzle.solver.PuzzleState;
import java.util.PriorityQueue;

/**
 *
 * @author brand_000
 */
public class PuzzleTest {
    static Scanner reader = new Scanner(System.in);
    
    public PuzzleTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    public void thisTest(){
        try {
            Integer[] t = {8,6,3,7,2,5,4,1,9};
            Integer[] w = {8,6,3,7,2,5,4,9,1};
            Integer[] g = {1,2,3,4,5,6,7,8,9};
            ArrayList<Integer> a1 = new ArrayList<>();
            
            PuzzleState one = new PuzzleState(a1);
            PuzzleState two = new PuzzleState(a1);
            PuzzleState t3 = new PuzzleState(a1);
            two.setParent(one);
            t3.setParent(two);

            PuzzleState cur = t3;
            while (cur.getParent() != null){
                System.out.println(cur.getState());
                cur = t3.getParent();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void swapValues(){
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> goal =new ArrayList<>();
       /* a.add("0");
        a.add("1");
        a.add("2");
        b.addAll(a);
        System.out.println(a == b);
        System.out.println(b);*/
        PriorityQueue<PuzzleState> open = new PriorityQueue();
        Integer[] t = {8,6,3,7,2,5,4,1,9};
        Integer[] w = {8,6,3,7,2,5,4,9,1};
        Integer[] g = {1,2,3,4,5,6,7,8,9};
        for (int in = 0;in<9;in++){
            a.add(w[in]);
            b.add(t[in]);
            goal.add(g[in]);
        }
        PuzzleState one = new PuzzleState(a);
        PuzzleState two = new PuzzleState(b);
        PuzzleState t3 = new PuzzleState(goal);
        
        two.setParent(one);
        t3.setParent(two);
        
        PuzzleState cur = t3;
       /* System.out.println(cur.getState());
        cur = cur.getParent();
        System.out.println(cur.getState());
        cur = cur.getParent();
        System.out.println(cur.getState());*/
        
        
        while (cur != null){
            System.out.println(cur.getState());
            cur = cur.getParent();
        }
        
    }
    
    public static ArrayList<Integer> initPuzzle() throws Exception{
        
        System.out.print("Enter: ");
        String settings = new String();
        if (reader.hasNextLine()) settings = reader.nextLine();
        String[] temp = settings.split("");
        ArrayList<Integer> t2 = new ArrayList<>();
        for (String t : temp){
            t2.add(Integer.parseInt(t));
        }
        return t2;
    }
}
