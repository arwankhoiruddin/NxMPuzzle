
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
public class MyTree {

    private MyNode root;

    /**
     * create tree with one node as root
     *
     * @param a
     */
    public MyTree(MyNode a) {
        this.root = a;
        Search.nodes.add(a);
    }

    public MyNode getRoot() {
        return this.root;
    }

    /**
     * breadth first search
     *
     * @param n
     * @param m
     * @param initState
     * @param goalState
     */
    public void bfs(int n, int m, int[] initState, int[] goalState) {

        // initialize list of closed and opened
        LinkedList<MyNode> open = new LinkedList();
        LinkedList<MyNode> closed = new LinkedList();
        LinkedList<MyNode> allNodes = new LinkedList();

        MyNode tr = getRoot();
        open.add(tr);

        // performs loop while the open list is not empty
        while (!open.isEmpty()) {
            // remove the leftmost part of the list
            tr = (MyNode) open.getFirst();
            open.removeFirst();

            // check if the tr is the goal
            if (arrayIsSame(tr.state, goalState)) {
//                System.out.println("The goal is found");

                // find the path from goal to the root
                Iterator findGoal = allNodes.iterator();
                while (findGoal.hasNext()) {
                    MyNode nd = (MyNode) findGoal.next();
                    if (arrayIsSame(nd.state, tr.state)) {
                        while (!nd.info.equals("root")) {
                            Search.steps.addFirst(nd.info);
                            nd = nd.parent;
                        }
                    }
                }
                break;
            } else {
                // the only difference DFS and BFS is that in BFS uses queue while in DFS uses stack
                tr = tr.expand();
                closed.addLast(tr);
                if (tr.up != null) {
                    open.addLast(tr.up);
                    allNodes.addLast(tr.up);
                }
                if (tr.bottom != null) {
                    open.addLast(tr.bottom);
                    allNodes.addLast(tr.bottom);
                }
                if (tr.left != null) {
                    open.addLast(tr.left);
                    allNodes.addLast(tr.left);
                }
                if (tr.right != null) {
                    open.addLast(tr.right);
                    allNodes.addLast(tr.right);
                }
//                System.out.println(Search.nodes.indexOf(Search.nodes.getLast()) + "nodes created");
            }
        }
    }

    public void dfs(int n, MyNode node, int[] goalState) {

        // initialize list of closed and opened
        LinkedList<MyNode> open = new LinkedList();
        LinkedList<MyNode> closed = new LinkedList();
        LinkedList<MyNode> allNodes = new LinkedList();

        MyNode tr = getRoot();
        open.add(tr);

        // performs loop while the open list is not empty
        while (!open.isEmpty()) {
            // remove the leftmost part of the list
            tr = (MyNode) open.getFirst();
            open.removeFirst();

            // check if the tr is the goal
            if (arrayIsSame(tr.state, goalState)) {
//                System.out.println("The goal is found");

                // find the path from goal to the root
                Iterator findGoal = allNodes.iterator();
                while (findGoal.hasNext()) {
                    MyNode nd = (MyNode) findGoal.next();
                    if (arrayIsSame(nd.state, tr.state)) {
                        while (!nd.info.equals("root")) {
                            Search.steps.addFirst(nd.info);
                            nd = nd.parent;
                        }
                    }
                }
                break;
            } else {
                // the only difference DFS and BFS is that in BFS uses queue while in DFS uses stack
                tr = tr.expand();
                closed.addLast(tr);
                if (tr.up != null) {
                    open.addFirst(tr.up);
                    allNodes.addLast(tr.up);
                }
                if (tr.bottom != null) {
                    open.addFirst(tr.bottom);
                    allNodes.addLast(tr.bottom);
                }
                if (tr.left != null) {
                    open.addFirst(tr.left);
                    allNodes.addLast(tr.left);
                }
                if (tr.right != null) {
                    open.addFirst(tr.right);
                    allNodes.addLast(tr.right);
                }
//                System.out.println(Search.nodes.indexOf(Search.nodes.getLast()) + "nodes created");
            }
        }
    }

    public void greedybest(int n, int[] state, int[] goalState) {

        // initialize list of closed and opened
        LinkedList<MyNode> open = new LinkedList();
        LinkedList<MyNode> closed = new LinkedList();
        LinkedList<MyNode> allNodes = new LinkedList();

        MyNode tr = getRoot();
        tr.level = 0;
        open.add(tr);

        // performs loop while the open list is not empty
        while (!open.isEmpty()) {
            // remove the leftmost part of the list
            tr = (MyNode) open.getFirst();
            open.removeFirst();

            // check if the tr is the goal
            if (arrayIsSame(tr.state, goalState)) {
//                System.out.println("The goal is found");

                // find the path from goal to the root
                Iterator findGoal = allNodes.iterator();
                while (findGoal.hasNext()) {
                    MyNode nd = (MyNode) findGoal.next();
                    if (arrayIsSame(nd.state, tr.state)) {
                        while (!nd.info.equals("root")) {
                            Search.steps.addFirst(nd.info);
                            nd = nd.parent;
                        }
                    }
                }
                break;
            } else {
                // visit tr
                // generate children of tr
                tr = tr.expand();
                // for each child of tr
                // 3cases:

                if (tr.up != null) {
                    tr.up.level = tr.level + 1;

                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.up.heuristicValue = evaluateHeuristic(n, tr.up.state, goalState);

                    // add the child to open
                    open.addLast(tr.up);
                    allNodes.addLast(tr.up);

                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.up)) {
                        MyNode temp = open.get(open.indexOf(tr.up));
                        if (temp.level > tr.up.level) {
                            open.remove(temp);
                            open.addFirst(tr.up);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.up)) {
                        MyNode temp = closed.get(open.indexOf(tr.up));
                        if (temp.level > tr.up.level) {
                            closed.remove(temp);
                            open.addFirst(tr.up);
                        }
                    }
                }
                if (tr.bottom != null) {
                    tr.bottom.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.bottom.heuristicValue = evaluateHeuristic(n, tr.bottom.state, goalState);

                    // add the child to open
                    open.addLast(tr.bottom);
                    allNodes.addLast(tr.bottom);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.bottom)) {
                        MyNode temp = open.get(open.indexOf(tr.bottom));
                        if (temp.level > tr.bottom.level) {
                            open.remove(temp);
                            open.addFirst(tr.bottom);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.bottom)) {
                        MyNode temp = closed.get(open.indexOf(tr.bottom));
                        if (temp.level > tr.bottom.level) {
                            closed.remove(temp);
                            open.addFirst(tr.bottom);
                        }
                    }
                }
                if (tr.left != null) {
                    tr.left.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.left.heuristicValue = evaluateHeuristic(n, tr.left.state, goalState);

                    // add the child to open
                    open.addLast(tr.left);
                    allNodes.addLast(tr.left);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.left)) {
                        MyNode temp = open.get(open.indexOf(tr.left));
                        if (temp.level > tr.left.level) {
                            open.remove(temp);
                            open.addFirst(tr.left);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.left)) {
                        MyNode temp = closed.get(open.indexOf(tr.left));
                        if (temp.level > tr.left.level) {
                            closed.remove(temp);
                            open.addFirst(tr.left);
                        }
                    }
                }
                if (tr.right != null) {
                    tr.right.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.right.heuristicValue = evaluateHeuristic(n, tr.right.state, goalState);

                    // add the child to open
                    open.addLast(tr.right);
                    allNodes.addLast(tr.right);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.right)) {
                        MyNode temp = open.get(open.indexOf(tr.right));
                        if (temp.level > tr.right.level) {
                            open.remove(temp);
                            open.addFirst(tr.right);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.right)) {
                        MyNode temp = closed.get(open.indexOf(tr.up));
                        if (temp.level > tr.right.level) {
                            closed.remove(temp);
                            open.addFirst(tr.right);
                        }
                    }
                }

                // put tr on closed
                closed.addLast(tr);

                // sort open by heuristic merit (best leftmost)
                if (!open.isEmpty()) {
                    open = sortNodeGreedy(open);
                }
//                System.out.println(Search.nodes.indexOf(Search.nodes.getLast()) + "nodes created");
            }
        }
    }

    public void astar(int n, int[] state, int[] goalState) {

        // initialize list of closed and opened
        LinkedList<MyNode> open = new LinkedList();
        LinkedList<MyNode> closed = new LinkedList();
        LinkedList<MyNode> allNodes = new LinkedList();

        MyNode tr = getRoot();
        tr.level = 0;
        open.add(tr);

        // performs loop while the open list is not empty
        while (!open.isEmpty()) {
            // remove the leftmost part of the list
            tr = (MyNode) open.getFirst();
            open.removeFirst();

            // check if the tr is the goal
            if (arrayIsSame(tr.state, goalState)) {
//                System.out.println("The goal is found");

                // find the path from goal to the root
                Iterator findGoal = allNodes.iterator();
                while (findGoal.hasNext()) {
                    MyNode nd = (MyNode) findGoal.next();
                    if (arrayIsSame(nd.state, tr.state)) {
                        while (!nd.info.equals("root")) {
                            Search.steps.addFirst(nd.info);
                            nd = nd.parent;
                        }
                    }
                }
                break;
            } else {
                // visit tr
                // generate children of tr
                tr = tr.expand();
                // for each child of tr
                // 3cases:

                if (tr.up != null) {
                    tr.up.level = tr.level + 1;

                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.up.heuristicValue = evaluateHeuristic(n, tr.up.state, goalState);

                    // add the child to open
                    open.addLast(tr.up);
                    allNodes.addLast(tr.up);

                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.up)) {
                        MyNode temp = open.get(open.indexOf(tr.up));
                        if (temp.level > tr.up.level) {
                            open.remove(temp);
                            open.addFirst(tr.up);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.up)) {
                        MyNode temp = closed.get(open.indexOf(tr.up));
                        if (temp.level > tr.up.level) {
                            closed.remove(temp);
                            open.addFirst(tr.up);
                        }
                    }
                }
                if (tr.bottom != null) {
                    tr.bottom.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.bottom.heuristicValue = evaluateHeuristic(n, tr.bottom.state, goalState);

                    // add the child to open
                    open.addLast(tr.bottom);
                    allNodes.addLast(tr.bottom);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.bottom)) {
                        MyNode temp = open.get(open.indexOf(tr.bottom));
                        if (temp.level > tr.bottom.level) {
                            open.remove(temp);
                            open.addFirst(tr.bottom);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.bottom)) {
                        MyNode temp = closed.get(open.indexOf(tr.bottom));
                        if (temp.level > tr.bottom.level) {
                            closed.remove(temp);
                            open.addFirst(tr.bottom);
                        }
                    }
                }
                if (tr.left != null) {
                    tr.left.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.left.heuristicValue = evaluateHeuristic(n, tr.left.state, goalState);

                    // add the child to open
                    open.addLast(tr.left);
                    allNodes.addLast(tr.left);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.left)) {
                        MyNode temp = open.get(open.indexOf(tr.left));
                        if (temp.level > tr.left.level) {
                            open.remove(temp);
                            open.addFirst(tr.left);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.left)) {
                        MyNode temp = closed.get(open.indexOf(tr.left));
                        if (temp.level > tr.left.level) {
                            closed.remove(temp);
                            open.addFirst(tr.left);
                        }
                    }
                }
                if (tr.right != null) {
                    tr.right.level = tr.level + 1;
                    // 1. never been seen before
                    // assign the child a heuristic value
                    tr.right.heuristicValue = evaluateHeuristic(n, tr.right.state, goalState);

                    // add the child to open
                    open.addLast(tr.right);
                    allNodes.addLast(tr.right);
                    // 2. child is waiting --> in the open list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (open.contains(tr.right)) {
                        MyNode temp = open.get(open.indexOf(tr.right));
                        if (temp.level > tr.right.level) {
                            open.remove(temp);
                            open.addFirst(tr.right);
                        }
                    }
                    // 3. child has been visited --> in the closed list
                    // if the child was reached by the shorter path
                    // then give the state of open to the shorter path
                    if (closed.contains(tr.right)) {
                        MyNode temp = closed.get(open.indexOf(tr.up));
                        if (temp.level > tr.right.level) {
                            closed.remove(temp);
                            open.addFirst(tr.right);
                        }
                    }
                }

                // put tr on closed
                closed.addLast(tr);

                // sort open by heuristic merit (best leftmost)
                if (!open.isEmpty()) {
                    open = sortNodeAStar(open);
                }
//                System.out.println(Search.nodes.indexOf(Search.nodes.getLast()) + "nodes created");
            }
        }
    }

    public LinkedList<MyNode> sortNodeGreedy(LinkedList<MyNode> list) {
        LinkedList<Integer> hVal = new LinkedList();
        LinkedList<MyNode> shortedList = new LinkedList();

        // add all list components to hVal
        Iterator<MyNode> it = list.iterator();
        while (it.hasNext()) {
            MyNode nd = it.next();
            hVal.addLast(nd.heuristicValue);
            shortedList.addLast(nd);
        }
        int i = 0;
        int j = 1;
        int listLength = list.indexOf(list.getLast());
        while (i < listLength) {
            while (j <= listLength) {
                if (shortedList.get(j).heuristicValue < shortedList.get(i).heuristicValue) {
                    MyNode temp = shortedList.get(i);
                    shortedList.set(i, shortedList.get(j));
                    shortedList.set(j, temp);
                }
                j++;
            }
            i++;
            j = i + 1;
        }
        return shortedList;
    }

    public LinkedList<MyNode> sortNodeAStar(LinkedList<MyNode> list) {
        LinkedList<Integer> hVal = new LinkedList();
        LinkedList<MyNode> shortedList = new LinkedList();

        // add all list components to hVal
        Iterator<MyNode> it = list.iterator();
        while (it.hasNext()) {
            MyNode nd = it.next();
            hVal.addLast(nd.heuristicValue);
            shortedList.addLast(nd);
        }
        int i = 0;
        int j = 1;
        int listLength = list.indexOf(list.getLast());
        while (i < listLength) {
            while (j <= listLength) {
                // heuristic values
                int hVj = shortedList.get(j).heuristicValue;
                int hVi = shortedList.get(i).heuristicValue;

                // level values
                int lvj = shortedList.get(j).level;
                int lvi = shortedList.get(i).level;

                if ((hVj + lvj) < (hVi + lvi)) {
                    MyNode temp = shortedList.get(i);
                    shortedList.set(i, shortedList.get(j));
                    shortedList.set(j, temp);
                }
                j++;
            }
            i++;
            j = i + 1;
        }
        return shortedList;
    }

    private int evaluateHeuristic(int n, int[] initState, int[] goalState) {
        int total = 0;
        for (int i = 0; i < initState.length; i++) {
            int posInit = findPosition(initState[i], initState);
            int posGoal = findPosition(initState[i], goalState);

            // find the distance between two points
            int posXInit = posInit / n;
            int posYInit = posInit % n;

            int posXGoal = posGoal / n;
            int posYGoal = posGoal % n;

            total = total + Math.abs(posXInit - posXGoal) + Math.abs(posYInit - posYGoal);
        }
        return total;
    }

    private int findPosition(int q, int[] state) {
        int i = 0;
        while (i < state.length) {
            if (state[i] == q) {
                break;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * detect if the state has been created before
     *
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
