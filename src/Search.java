
import java.io.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arwanakhoiruddin
 */
public class Search {

    public static LinkedList nodes = new LinkedList();
    public static LinkedList steps = new LinkedList();

    public static void main(String[] args) {

        String path = System.getProperty("user.dir")+ "/" + args[0];
        String method = args[1];
        
        String[] input = readFile(path);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        // define initial and goal state
        int arrayLength = n * m;
        int[] initState = new int[arrayLength];
        int[] goalState = new int[arrayLength];
        String[] iState = input[2].split(";");
        String[] gState = input[3].split(";");

        int idx = 0;
        while (idx < arrayLength) {
            initState[idx] = Integer.parseInt(iState[idx]);
            goalState[idx] = Integer.parseInt(gState[idx]);
            idx++;
        }

        int nodesCreated = 0;

        MyNode root = new MyNode(initState, n, "root");

        MyTree t = new MyTree(root);

        if (method.equals("BFS")) {
            t.bfs(n, m, initState, goalState);
            nodesCreated = nodes.indexOf(nodes.getLast());
        } else if (method.equals("DFS")) {
            nodes.clear();
            t.dfs(n, root, goalState);
        } else if (method.equals("GFS")) {
            nodes.clear();
            t.greedybest(n, initState, goalState);
            nodesCreated = nodes.indexOf(nodes.getLast());
        } else if (method.equals("AS")) {
            nodes.clear();
            t.astar(n, initState, goalState);
            nodesCreated = nodes.indexOf(nodes.getLast());
        } else {
            System.out.println("Unknown parameter for keyword. Available parameters: BFS, DFS, GFS, and AS");
        }
        
        File f = new File(path);
        String fileName = f.getName();
        System.out.println(fileName + " " + method + " " + nodesCreated);
        Iterator it = steps.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + ";");
        }
        System.out.println();
    }

    public static String[] readFile(String path) {
        String[] sCurrentLine = new String[4];
        String temp = "";
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((temp = br.readLine()) != null) {
                sCurrentLine[i++] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sCurrentLine;
    }

    public static void showNodesCreated() {
        Iterator it = nodes.iterator();
        while (it.hasNext()) {
            MyNode nodeCreated = (MyNode) it.next();
            System.out.println("State created: " + Arrays.toString(nodeCreated.state));
        }
    }
}
