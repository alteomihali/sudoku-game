
import java.util.ArrayList;

public class AC3 {
    private Node[][] nodes;
    private ArrayList<Arc> arrayList = new ArrayList<>();

    class Arc {
        Node node1;
        Node node2;

        Arc(Node n1, Node n2) {
            node1 = n1;
            node2 = n2;
        }
    }

    AC3(Node[][] nodes) {
        System.out.println("\r\nZbatojme AC3 per rreshtin me siper: ");

        this.nodes = nodes;

        for (int i = 0; i < 9; i++) {
            addRow(i);
            addCol(i);
            addBox(i);
        }

        while (!arrayList.isEmpty()) {
            Arc arch = arrayList.remove(0);
            if (!controlAC(arch)) {
                System.out.println("Nuk ka zgjidhje.");
                break;
            }
        }

        int result = Node.judgeState(nodes);
        if (result != -1) {
            Node.printNodes(nodes);
        }
    }

    void addRow(int row) {
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                arrayList.add(new Arc(nodes[row][i], nodes[row][j]));
                arrayList.add(new Arc(nodes[row][j], nodes[row][i]));
            }
        }
    }

    void addCol(int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                arrayList.add(new Arc(nodes[i][col], nodes[j][col]));
                arrayList.add(new Arc(nodes[j][col], nodes[i][col]));
            }
        }
    }

    void addBox(int box) {
        // add nodes which are in the same box together
        ArrayList<Node> nodesBox = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (nodes[i][j].box == box) {
                    nodesBox.add(nodes[i][j]);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                arrayList.add(new Arc(nodesBox.get(i), nodesBox.get(j)));
                arrayList.add(new Arc(nodesBox.get(j), nodesBox.get(i)));
            }
        }
    }

    boolean controlAC(Arc arc) {
        Node node1 = arc.node1;
        Node node2 = arc.node2;
        for (Integer i : node1.domain) {
            boolean isDiff = false;
            for (Integer j : node2.domain) {
                if (i != j) {
                    isDiff = true;
                    break;
                }
            }

            if (!isDiff) {
                node1.domain.remove(i);
                if (node1.domain.isEmpty()) {
                    return false;
                }
                addArcsExceptB(node1, node2);
            }
        }
        return true;
    }

    void addArcsExceptB(Node A, Node B) {
        // add arcs with nodes in the same row
        for (int i = 0; i < 9; i++) {
            if (i != A.col && nodes[A.row][i] != B) {
                arrayList.add(new Arc(nodes[A.row][i], A));
            }
        }

        // add arcs with nodes in the same col
        for (int i = 0; i < 9; i++) {
            if (i != A.row && nodes[i][A.col] != B) {
                arrayList.add(new Arc(nodes[i][A.col], A));
            }
        }

        // add arcs with nodes in the same box
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (nodes[i][j].box == A.box && i != A.row && j != A.col && nodes[i][j] != B) {
                    arrayList.add(new Arc(nodes[i][j], A));
                }
            }
        }
    }
}
