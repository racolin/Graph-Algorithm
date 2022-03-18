import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainBFS {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Algorithm");
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("X/Y");
        JTable table = new JTable(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        JPanel queue = new JPanel();
        queue.setBounds(20, 160, 800, 40);
        Border titleQueue = BorderFactory.createTitledBorder("Queue");
        queue.setBorder(titleQueue);

        JPanel parent = new JPanel();
        parent.setBounds(20, 110, 800, 40);
        Border titleParent = BorderFactory.createTitledBorder("Cha");
        parent.setBorder(titleParent);

        table.setBounds(0, 0, 800, 800);

        JScrollPane sp = new JScrollPane(table);

        sp.setBounds(20, 230, 800, 800);

        AlgorithmFrameBFS algorithmFrame = new AlgorithmFrameBFS(1030, 1030, model, table, queue, parent);
        algorithmFrame.setLocation(900, 0);

        JButton start = new JButton("Chọn điểm bắt đầu");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.status = 1;
            }
        });
        start.setBounds(20, 10, 200, 40);
        frame.add(start);

        JButton end = new JButton("Chọn điểm kết thúc");
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.status = 2;
            }
        });
        end.setBounds(20, 60, 200, 40);
        frame.add(end);

        JButton clear = new JButton("Làm mới");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.clear();
            }
        });
        clear.setBounds(300, 10, 100, 40);
        frame.add(clear);

        JButton startAlgorithm = new JButton("Bắt đầu");
        startAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.setUniformCostSearch();
            }
        });
        startAlgorithm.setBounds(300, 60, 100, 40);
        frame.add(startAlgorithm);

        JButton next = new JButton("Bước kế");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.oneMove();
            }
        });
        next.setBounds(480, 60, 100, 40);
        frame.add(next);

        frame.add(algorithmFrame);
        frame.add(parent);

        frame.add(queue);
        frame.add(sp);
        frame.setSize(1920, 1080);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
