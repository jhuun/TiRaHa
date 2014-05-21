package labyrinthsolver;

import java.util.Random;
import main.Labyrinth;
import main.MyList;
import main.MyStack;

/**
 * Labyrintin ratkoja-algoritmien yliluokka.
 *
 * @author Juri Kuronen
 */
public abstract class LabyrinthSolver {

    /**
     * Labyrintti, jolle algoritmit ajetaan.
     */
    protected Labyrinth labyrinth;
    /**
     * Random-olio, jota käytetään satunnaisalgoritmeissa.
     */
    protected Random random;
    /**
     * Tutkittujen solujen määrä.
     */
    protected int exploredCells;
    /**
     * Algoritmin lyhin löytämä reitti maaliin.
     */
    protected MyList<Integer> path;
    /**
     * Array, jossa on tietoa labyrintin solujen tiloista.
     */
    protected int[][] visited;

    /**
     * Ottaa syötteenä labyrintin ja alustaa Random-olion..
     *
     * @param l Labyrintti, jolle algoritmi ajetaan.
     */
    public LabyrinthSolver(Labyrinth l) {
        labyrinth = l;
        random = new Random();
        visited = new int[labyrinth.height][labyrinth.width];
    }

    /**
     * Labyrintin ratkova metodi.
     *
     * @return Palauttaa true, jos labyrintti ratkaistiin.
     */
    public abstract boolean solveLabyrinth();

    /**
     * Palauttaa kokonaisluvun tekstimuodossa ### ### ###.
     *
     * @param integerPart Kokonaislukuosa.
     * @return Palauttaa formatoidun kokonaislukuosan.
     */
    private String formatIntegerPart(long integerPart) {
        String timeFormat;
        if (integerPart / 1000000 > 0) {
            timeFormat = (integerPart / 1000000) + " " + (integerPart / 1000) + " " + (integerPart % 1000);
        } else if (integerPart / 1000 > 0) {
            timeFormat = (integerPart / 1000) + " " + (integerPart % 1000);
        } else {
            timeFormat = integerPart + "";
        }
        return timeFormat;
    }

    /**
     * Formatoi ajan tekstimuotoon ### ### ###,### ms.
     *
     * @param startTime Algoritmin suorituksen aloitusaika.
     * @param finishTime Algoritmin suorituksen lopetusaika.
     * @return Palauttaa formatoidun ajan.
     */
    private String timeFormat(long startTime, long finishTime) {
        long microTime = finishTime - startTime;
        String timeFormat = "," + (microTime % 1000) + " ms";
        return formatIntegerPart(microTime / 1000) + timeFormat;
    }

    /**
     * Tulostusrutiini.
     */
    public void routine() {
        long startTime = System.nanoTime() / 1000;
        boolean solved = solveLabyrinth();
        long finishTime = System.nanoTime() / 1000;
        String timeFormat = timeFormat(startTime, finishTime);
        if (solved) {
            System.out.print("  ::  Solution found in " + timeFormat);
            getExploredCells();
            String exploredCellsFormat = formatIntegerPart(exploredCells);
            System.out.println("  ::  Cells explored: " + exploredCellsFormat);
        } else {
            System.out.println("TIMEOUT LIMIT EXCEEDED (" + timeFormat + ")");
        }
    }

    /**
     * Laskee visited-solujen määrän.
     */
    protected void getExploredCells() {
        exploredCells = 0;
        for (int i = 0; i < labyrinth.height * labyrinth.width; i++) {
            if (visited[i / labyrinth.width][i % labyrinth.width] == 2) {
                exploredCells++;
            }
        }
    }

//    private class TreeNode {
//
//        TreeNode parent;
//        int coordinate;
//
//        TreeNode(TreeNode p, int c) {
//            parent = p;
//            coordinate = c;
//        }
//    }
//
//    /**
//     * Etsii polun, jota pitkin kuljettiin..
//     *
//     * @param visited Array, jossa on tietoa labyrintin solujen tiloista.
//     */
//    protected void findPath(int[][] visited) {
//        path = new MyList<>();
//        MyStack<TreeNode> stack = new MyStack<>();
//        stack.push(new TreeNode(null, 0));
//        int target = labyrinth.height * labyrinth.width - 1;
//        while (!stack.empty()) {
//            TreeNode node = stack.pop();
//            if (visited[node.coordinate / labyrinth.width][node.coordinate % labyrinth.width] == 0) {
//                continue;
//            }
//            visited[node.coordinate / labyrinth.width][node.coordinate % labyrinth.width] = 0;
//            if (node.coordinate == target) {
//                while (node != null) {
//                    path.add(node.coordinate);
//                    node = node.parent;
//                }
//                path.reverseList();
//                return;
//            }
//            MyList neighbors = labyrinth.getListOfEdgesToVisitedNeighbors(node.coordinate, visited);
//            for (int i = 0; i < neighbors.size(); i++) {
//                stack.push(new TreeNode(node, (int) neighbors.get(i)));
//            }
//        }
//    }
}
