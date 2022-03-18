import java.util.*;

public class BFSAlgorithm
{
    public Queue<Node> Frontier;
    private Set<Integer> Visited;
    private int distances[];
    private int numberOfNodes;
    private int adjacencyMatrix[][];
    private LinkedList<Integer> path;
    public int[] parent;
    public int source, destination, perform = -1, countUpdates;
    public static final int MAX_VALUE = 999;
    private int cost;
    public int[] updates;
    // 1 = BFS, 2 = UCS
    class Node implements Comparator<Node>
    {
        public int node;
        public int cost;

        public Node()
        {

        }

        public Node(int node, int cost)
        {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2)
        {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            if (node1.node < node2.node)
                return -1;
            return 0;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Node)
            {
                Node node = (Node) obj;
                if (this.node == node.node)
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + node + ", " + cost + ")";
        }
    }


    public BFSAlgorithm(int numberOfNodes, int adjacencyMatrix[][], int source, int destination)
    {
        updates = new int[numberOfNodes + 1];
        this.numberOfNodes = numberOfNodes;
        this.Visited = new HashSet<Integer>();
        this.Frontier = new LinkedList<>();
        this.distances = new int[numberOfNodes + 1];
        this.path = new LinkedList<Integer>();
        this.adjacencyMatrix = new int[numberOfNodes + 1][numberOfNodes + 1];
        this.parent = new int[numberOfNodes + 1];
        this.source = source;
        this.destination = destination;

        for (int i = 1; i <= numberOfNodes; i++)
        {
            distances[i] = MAX_VALUE;
            parent[i] = -1;
        }

        for (int sourcevertex = 1; sourcevertex <= numberOfNodes; sourcevertex++)
        {
            for (int destinationvertex = 1; destinationvertex <= numberOfNodes; destinationvertex++)
            {
                this.adjacencyMatrix[sourcevertex][destinationvertex] =
                        adjacencyMatrix[sourcevertex][destinationvertex];
            }
        }

        Frontier.add(new Node(source, 0));
        distances[source] = 0;

    }

    //  -1: Hết đường đi, > 0 kết quả, 0 còn đi nữa
    public int oneMove() {
        Node Current;
        if (!Frontier.isEmpty()) {
            Current  = getNextNode();
            perform = Current.node;
            cost = Current.cost;
            if (Current.node  == destination)
            {
                return distances[destination];
            }
            Visited.add(Current.node);
            addFrontiersToQueue(Current.node);
            return 0;
        }
        else {
            return -1;
        }
    }

    private void addFrontiersToQueue(int evaluationNode)
    {
        for (int destination = 1; destination <= numberOfNodes; destination++)
        {
            if (!Visited.contains(destination) && adjacencyMatrix[destination][evaluationNode] != MAX_VALUE)
            {
                distances[destination] = adjacencyMatrix[evaluationNode][destination] + distances[evaluationNode];
                parent[destination] = evaluationNode;

                Node node = new Node(destination, distances[destination]);
                if (!Frontier.contains(node))
                {
                    Frontier.add(node);
                }
                Visited.add(destination);
            }
        }
    }

    public String getPath() {
        String path = "";
        int x = destination, y = source;
        while (x != y) {
            path = "-->" + String.valueOf(x) + path;
            x = parent[x];
        }
        path = String.valueOf(y) + path;
        return path;
    }

    public int getCost() {
        return cost;
    }

    public String getParents() {
        String[] parentString = new String[numberOfNodes + 1];
        parentString[0] = "";
        for (int i = 1; i <= numberOfNodes; i++) {
            parentString[i] = "(" + String.valueOf(i) + ", " + String.valueOf(parent[i]) + ")";
        }
        return String.join(", ", parentString);
    }

    public String getQueue() {
        String q = "";
        BFSAlgorithm.Node[] temp = new BFSAlgorithm.Node[Frontier.size()];
        BFSAlgorithm.Node[] nodes = Frontier.toArray(temp);
        for (int i = 0; i < Frontier.size(); i++) {
            q += nodes[i].toString() + ", ";
        }
        return q;
    }

    private Node getNextNode()
    {
        Node node = Frontier.remove();
        return node;
    }
}
