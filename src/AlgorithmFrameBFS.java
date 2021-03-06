import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AlgorithmFrameBFS extends JPanel {
    int width, height;
    static Color COLORDEFAULT = Color.WHITE;

    int startTop, endTop;

    int xDragged = -1, yDragged = -1;

    ArrayList<Integer> Top;

    boolean activeS = false, activeE = false;

    static int DEFAULT = 2000, LENGTHARROW = 10;
    int r = 25, xMove, yMove;
    static Font FONTDEFAULT = new Font(Font.MONOSPACED, Font.BOLD, 25);
    private boolean dragged = false;

    int[][] edges;

    int curTop = -1, status = 0, performed = -1;

    DefaultTableModel model;
    JTable table;

    BFSAlgorithm bfsAlgorithm;
    int[][] adjacencyMatrix;

    boolean finished = false;

    JPanel queueJPane;
    JLabel queueContent;
    JPanel parentJPane;
    JLabel parentContent;

    int value = 0;

    static Color PERFORM = Color.GREEN, PERFORMED = Color.CYAN, UNFULFILLED = Color.ORANGE;

    AlgorithmFrameBFS(int width, int height, DefaultTableModel model , JTable table, JPanel queueJPane, JPanel parentJPane) {
        this.queueJPane = queueJPane;
        this.parentJPane = parentJPane;
        queueContent = new JLabel();
        parentContent = new JLabel();
        this.queueJPane.add(queueContent);
        this.parentJPane.add(parentContent);
        this.table = table;
        System.out.println(this.table.getHeight());
        edges = new int[20][20];
        this.model = model;
        for (int i = 0; i < 20; i ++) {
            for (int j = 0; j < 20; j++) {
                edges[i][j] = -1;
            }
        }
        Top = new ArrayList<>();
        this.height = height;
        this.width = width;
        setSize(width, height);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (status == 0) {
                    int x = e.getX(), y = e.getY();
                    boolean check = true;
                    for (int i = 0; i < Top.size(); i++) {
                        check = check && !(distance(x, y, Top.get(i) / DEFAULT, Top.get(i) % DEFAULT) < 2 * r);
                    }
                    if (check) {
                        Top.add(x * DEFAULT + y);

                        int intTop = Top.size();
                        drawCycle(intTop - 1, Color.PINK);

                        model.addColumn(String.valueOf(intTop));
                        model.addRow(new Object[]{String.valueOf(intTop)});

                        edges[intTop][intTop] = -1;
                        for (int i = 0; i < Top.size() - 1; i++) {
                            model.setValueAt(edges[i][intTop], i, intTop);
                            model.setValueAt(edges[intTop - 1][i + 1], intTop - 1, i + 1);
                        }
                        model.setValueAt(edges[intTop][intTop], intTop - 1, intTop);
//                        System.out.println(table.getHeight());
                        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth() / (intTop + 1), table.getWidth() / (intTop + 1)));
                        table.setRowHeight(table.getWidth() / (intTop + 1));

                        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                        for (int i = 0; i < intTop + 1; i++) {
                            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                        }

//                        table.getColumnModel().getColumn(0).set
                    }
                }
                else {
                    if (!activeS && status == 1) {
                        int x = e.getX(), y = e.getY(), temp, xCur, yCur;
                        for (int i = 0; i < Top.size(); i++) {
                            temp = Top.get(i);
                            xCur = temp / DEFAULT;
                            yCur = temp % DEFAULT;

                            if (distance(x, y, xCur, yCur) < r) {

                                activeS = true;

                                startTop = i;

                                drawCycle(i, Color.YELLOW);
                            }
                        }
                    }
                    if (!activeE && status == 2) {

                        activeE = true;

                        int x = e.getX(), y = e.getY(), temp, xCur, yCur;
                        for (int i = 0; i < Top.size(); i++) {
                            temp = Top.get(i);
                            xCur = temp / DEFAULT;
                            yCur = temp % DEFAULT;

                            if (distance(x, y, xCur, yCur) < r) {

                                endTop = i;
                                drawCycle(i, Color.RED);
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragged) {

                    int x = e.getX(), y = e.getY(), xCur, yCur;

                    int temp = 0;

                    for (int i = 0; i < Top.size(); i++) {

                        temp = Top.get(i);

                        xCur = temp / DEFAULT;
                        yCur = temp % DEFAULT;

                        if (i != curTop && distance(x, y, xCur, yCur) < r) {

                            dragged = false;

                            drawArrow(xDragged, yDragged, xCur, yCur, Color.BLACK, i);

                            model.setValueAt(edges[curTop][i], i, curTop + 1);
                            model.setValueAt(edges[i][curTop], curTop, i + 1);
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragged) {

                    int x = e.getX(), y = e.getY(), xCur, yCur;

                    int temp = 0;

                    for (int i = 0; i < Top.size(); i++) {
                        temp = Top.get(i);
                        xCur = temp / DEFAULT;
                        yCur = temp % DEFAULT;
                        if (distance(x, y, xCur, yCur) < r) {
                            dragged = true;
                            xDragged = xCur;
                            yDragged = yCur;

                            xMove = xCur;
                            yMove = yCur;

                            curTop = i;
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void clear() {

        xDragged = -1; yDragged = -1;

        Top.clear();

        dragged = false;

        edges = new int[20][20];

        curTop = -1;

        model.setColumnIdentifiers(new Object[]{"X/Y"});
        model.setNumRows(0);

        status = 0;

        activeS = false;
        activeE = false;

        queueContent.setText("");

        finished = false;

        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        parentContent.setText("");
        for (int i = 0; i < 20; i ++) {
            for (int j = 0; j < 20; j++) {
                edges[i][j] = -1;
            }
        }

//        bfsAlgorithm.perform = -1;

        performed = -1;
    }

    public void setUniformCostSearch() {
        int len = Top.size(), temp;
        adjacencyMatrix = new int[len + 1][len + 1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                temp = (int) table.getValueAt(i, j + 1);
                adjacencyMatrix[i + 1][j + 1] = (temp == -1) ? BFSAlgorithm.MAX_VALUE : temp;
            }
        }
        bfsAlgorithm = new BFSAlgorithm(len, adjacencyMatrix, startTop + 1, endTop + 1);
    }

    public void oneMove() {
        if (!finished) {
            if (value == -1) {
                // Khong thay duong di
                JOptionPane.showMessageDialog(null, "Kh??ng t??m th???y ???????ng ??i!");
                value = 0;
            }
            else {
                if (value > 0) {
                    // Tim den dich
                    value = 0;
                    String temp = "???????ng ??i l??: " + bfsAlgorithm.getPath() + "\nChi ph?? l??: " + String.valueOf(bfsAlgorithm.getCost());
                    JOptionPane.showMessageDialog(null, temp);
                    finished = true;
                    for (int i = 0; i < Top.size(); i++) {
                        drawCycle(i, Color.PINK);
                    }
                    int top = bfsAlgorithm.destination;
                    int old = Top.get(bfsAlgorithm.destination - 1);
                    int cur = 0;
                    while (top != bfsAlgorithm.source) {
                        drawCycle(top - 1, Color.RED);
                        cur = Top.get(top - 1);
                        top = bfsAlgorithm.parent[top];
                        old = Top.get(top - 1);
                        drawArrow(old / DEFAULT, old % DEFAULT, cur / DEFAULT, cur % DEFAULT, Color.RED);
                    }
                    drawCycle(top - 1, Color.RED);
                }
                else {
                    if (value == 0) {
                        value = bfsAlgorithm.oneMove();
                        parentContent.setText("(?????nh, ?????nh cha): " + bfsAlgorithm.getParents());
                        queueContent.setText("(?????nh, ????? d??i): " + bfsAlgorithm.getQueue());

                        drawCycle(performed, Color.RED);
                        performed = bfsAlgorithm.perform - 1;
                        drawCycle(performed, Color.GREEN);

                        int size = bfsAlgorithm.Frontier.size();

                        BFSAlgorithm.Node[] tempNode = new BFSAlgorithm.Node[size];
                        BFSAlgorithm.Node[] nodes = bfsAlgorithm.Frontier.toArray(tempNode);
                        for (int i = 0; i < size; i++) {
                            drawCycle(nodes[i].node - 1, Color.ORANGE);
                        }
                    }
                }
            }
        }
    }

    private void drawCycle(int top, Color color) {
        if ((top >= 0 && top < Top.size())) {
            Graphics g = getGraphics();

            int x = Top.get(top) / DEFAULT;
            int y = Top.get(top) % DEFAULT;

            g.setColor(color);
            g.fillArc(x - r, y - r, 2 * r, 2 * r, 0, 360);

            g.setFont(FONTDEFAULT);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf((top + 1)), x - r / 4 * String.valueOf(top + 1).length(), y + r / 3);
        }
    }

    private void drawArrow(int xStart, int yStart, int xEnd, int yEnd, Color color, int top) {
        Graphics g = getGraphics();

        int x1, y1, x2, y2;

        float gap = (float) r / distance(xStart, yStart, xEnd, yEnd);

        x1 = (int) ((xEnd - xStart) * gap) + xStart;
        y1 = (int) ((yEnd - yStart) * gap) + yStart;
        x2 = (int) ((xStart - xEnd) * gap) + xEnd;
        y2 = (int) ((yStart - yEnd) * gap) + yEnd;

        drawWeight(x1, y1, x2, y2, Color.BLACK, 3);

        int len = Integer.parseInt(JOptionPane.showInputDialog(null, "Nh???p ????? d??i c???a c???nh n??y: "));

        drawWeightArrow(xStart, yStart, xEnd, yEnd, color, len);

        edges[curTop][top] = len; edges[top][curTop] = len;
    }

    private void drawArrow(int xStart, int yStart, int xEnd, int yEnd, Color color) {
        Graphics g = getGraphics();

        int x1, y1, x2, y2;

        float gap = (float) r / distance(xStart, yStart, xEnd, yEnd);

        x1 = (int) ((xEnd - xStart) * gap) + xStart;
        y1 = (int) ((yEnd - yStart) * gap) + yStart;
        x2 = (int) ((xStart - xEnd) * gap) + xEnd;
        y2 = (int) ((yStart - yEnd) * gap) + yEnd;

        drawWeight(x1, y1, x2, y2, color, 3);
        drawArrowTop(x1, y1, x2, y2, color);
    }

    private void drawArrowTop(int xStart, int yStart, int xEnd, int yEnd, Color color) {

        float gap = (float)20 / distance(xStart, yStart, xEnd, yEnd);

        int xTemp = (int)(gap * (xStart - xEnd)), yTemp = (int)(gap * (yStart - yEnd));

        int xMid = xTemp + xEnd, yMid = yTemp + yEnd;

        int x1 = xMid + yTemp, y1 = yMid - xTemp;
        int x2 = xMid - yTemp, y2 = yMid + xTemp;

        drawWeight(xEnd, yEnd, x1, y1, color, 3);
        drawWeight(xEnd, yEnd, x2, y2, color, 3);
    }

    private void drawWeight(int x1, int y1, int x2, int y2, Color color, int weight) {
        Graphics g = getGraphics();
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);

        boolean temp = Math.abs(x1 - x2) > Math.abs(y1 - y2);

        for (int i = 1; i <= weight; i++) {
            g.drawLine(x1 - (temp ? 0 : 1) * i, y1 - (temp ? 1 : 0) * i, x2 - (temp ? 0 : 1) * i, y2 - (temp ? 1 : 0) * i);
        }
    }

    private void drawWeightArrow(int x1, int y1, int x2, int y2, Color color, int weight) {
        Graphics g = getGraphics();
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        int x, y;
        y = (y1 + y2) / 2;
        if (x2 - x1 > 50) {
            x = (x1 + x2) / 2;
        }
        else {
            if (x2 - x1 > 0) {
                x = (x1 + x2 + 50) / 2;
            }
            else {
                if (x2 - x1 < 50) {
                    x = (x1 + x2 - 50) / 2;
                }
                else {
                    x = (x1 + x2 - 80) / 2;
                }
            }
        }
        if (Math.abs(y2 - y1) < 200) {
            y -= 20;
        }
        else {
            if (Math.abs(y2 - y1) < 500) {
                y -= 50;
            }
        }
        g.drawString(String.valueOf(weight), x, y);
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
