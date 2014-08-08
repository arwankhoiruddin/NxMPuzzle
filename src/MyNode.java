
import java.util.Arrays;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arwanakhoiruddin
 */
public class MyNode {

    int[] state;
    int n;
    String info;
    int heuristicValue;
    int level;
    
    MyNode up;
    MyNode bottom;
    MyNode left;
    MyNode right;
    MyNode parent;

    public MyNode(int[] state, int n, String info) {
        this.state = state;
        this.n = n;
        this.info = info;
    }

    /**
     * expand the current node
     * @return 
     */
    public MyNode expand() {
        addNodeOnMoveUp(state, n);
        addNodeOnMoveBottom(state, n);
        addNodeOnMoveLeft(state, n);
        addNodeOnMoveRight(state, n);
        return this;
    }

    /**
     * Generates new state when the blank part is shifted up
     *
     * @param state
     * @param n
     * @return 
     */
    public MyNode addNodeOnMoveUp(int[] state, int n) {
        MyNode newNode = null;
        int[] tempState = new int[state.length];
        int i = 0;
        for (int o : state) {
            tempState[i++] = o;
        }

        // find the blank positon to understand how it can move
        int zeroCoord = findBlankPosition(state);

        if (zeroCoord < n) { // cannot move up        
            this.up = null;
        } else {
            // exchange values between blank and the upper value of the blank position
            int tmp = tempState[zeroCoord];
            tempState[zeroCoord] = tempState[zeroCoord - n];
            tempState[zeroCoord - n] = tmp;

            // check first if the same state has been generated before
            if (!stateHasBeenCreated(tempState)) {
                newNode = new MyNode(tempState, n, "up");
                this.up = newNode;
                newNode.parent = this;
                Search.nodes.addLast(this.up);
                return newNode;
            }
        }
        return newNode;
    }

    /**
     * Generates new state when the blank part is shifted down
     *
     * @param state
     * @param n
     * @return MyNode
     */
    public MyNode addNodeOnMoveBottom(int[] state, int n) {
        MyNode newNode = null;
        int[] tempState = new int[state.length];
        int i = 0;
        for (int o : state) {
            tempState[i++] = o;
        }

        // find the blank positon to understand how it can move
        int zeroCoord = findBlankPosition(state);
        if (zeroCoord >= (state.length - n)) { // cannot move down    
            this.bottom = null;
        } else {
            // exchange values between blank and the bottom value of the blank position
            int tmp = tempState[zeroCoord];
            tempState[zeroCoord] = tempState[zeroCoord + n];
            tempState[zeroCoord + n] = tmp;

            if (!stateHasBeenCreated(tempState)) {
                newNode = new MyNode(tempState, n, "bottom");
                this.bottom = newNode;
                newNode.parent = this;
                Search.nodes.addLast(this.bottom);
                return newNode;
            }
        }
        return newNode;
    }

    /**
     * Generates new state when the blank part is shifted left
     *
     * @param state
     * @param n
     */
    public MyNode addNodeOnMoveLeft(int[] state, int n) {
        MyNode newNode = null;
        int[] tempState = new int[state.length];
        int i = 0;
        for (int o : state) {
            tempState[i++] = o;
        }

        // find the blank positon to understand how it can move
        int zeroCoord = findBlankPosition(state);
        if ((zeroCoord % n) == 0) { // cannot move to the left        
            this.left = null;
        } else {
            // exchange values between blank and the upper part of the blank
            int tmp = tempState[zeroCoord];
            tempState[zeroCoord] = tempState[zeroCoord - 1];
            tempState[zeroCoord - 1] = tmp;

            if (!stateHasBeenCreated(tempState)) {
                newNode = new MyNode(tempState, n, "left");
                this.left = newNode;
                newNode.parent = this;
                Search.nodes.addLast(this.left);
            }
        }
        return newNode;
    }

    /**
     * Generates new state when the blank part is shifted right
     *
     * @param state
     * @param n
     */
    public MyNode addNodeOnMoveRight(int[] state, int n) {
        MyNode newNode = null;
        int[] tempState = new int[state.length];
        int i = 0;
        for (int o : state) {
            tempState[i++] = o;
        }

        // find the blank positon to understand how it can move
        int zeroCoord = findBlankPosition(state);
        if (((zeroCoord + 1) % n) == 0) { // cannot move to the right        
            this.right = null;
        } else {
            // exchange values between blank and the upper part of the blank
            int tmp = tempState[zeroCoord];
            tempState[zeroCoord] = tempState[zeroCoord + 1];
            tempState[zeroCoord + 1] = tmp;

            if (!stateHasBeenCreated(tempState)) {
                newNode = new MyNode(tempState, n, "right");
                this.right = newNode;
                newNode.parent = this;
                Search.nodes.addLast(this.right);
            }
        }
        return newNode;
    }

    /**
     * find the position of the blank in the current state
     * @param state
     * @return 
     */
    private int findBlankPosition(int[] state) {
        int i = 0;
        while (i < state.length) {
            if (state[i] == 0) {
                break;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * detect if the state has been created before
     * @param state
     * @return 
     */
    public boolean stateHasBeenCreated(int[] state) {
        boolean created = false;
        Iterator it = Search.nodes.iterator();
        while (it.hasNext()) {
            MyNode createdNode = (MyNode) it.next();
            if (arrayIsSame(createdNode.state, state)) {
                created = true;
                break;
            }
        }
        return created;
    }

    /**
     * check if the content of the array is same or not
     * @param arr1
     * @param arr2
     * @return 
     */
    public boolean arrayIsSame(int[] arr1, int[] arr2) {
        int i = 0;
        boolean same = true;
        for (int o : arr1) {
            if (arr2[i++] != o) {
                same = false;
                break;
            }
        }
        return same;
    }
}
