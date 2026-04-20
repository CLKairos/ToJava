package org.clkairos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class renderingManager {
    private JFrame frame;
    private taskManager tm;

    // Brand Colors
    private static final Color BG_DEEP    = new Color(248, 248, 250);
    private static final Color BG_CARD    = new Color(255, 255, 255);
    private static final Color ACCENT     = new Color(99, 91, 210);
    private static final Color ACCENT_HOV = new Color(120, 112, 235);
    private static final Color TEXT_PRI   = new Color(20, 20, 30);
    private static final Color TEXT_MUT   = new Color(140, 140, 155);

    public renderingManager(taskManager tm) {
        this.tm = tm;
    }

    public void init(int width, int height) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("ToJava");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(width, height);
            frame.setMinimumSize(new Dimension(400, 500));

            // Using BorderLayout for the main frame to control the container position
            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(BG_DEEP);

            // Container for the content
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setBackground(BG_DEEP);
            container.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

            buildUI(container);

            // Wrap in a scroll pane so the UI doesn't break when many tasks are added
            JScrollPane mainScroll = new JScrollPane(container);
            mainScroll.setBorder(null);
            mainScroll.getVerticalScrollBar().setUnitIncrement(16);

            frame.add(mainScroll, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    private void buildUI(JPanel root) {
        root.removeAll();

        // Header Section
        root.add(header());
        root.add(Box.createRigidArea(new Dimension(0, 24)));

        // Inputs
        JTextField taskField = input("Task name");
        JTextField dueField  = input("Due date");
        JButton addBtn       = button("Add task");

        root.add(taskField);
        root.add(Box.createRigidArea(new Dimension(0, 12)));
        root.add(dueField);
        root.add(Box.createRigidArea(new Dimension(0, 12)));
        root.add(addBtn);
        root.add(Box.createRigidArea(new Dimension(0, 32)));

        // Section Label
        JLabel section = new JLabel("TASKS");
        section.setForeground(TEXT_MUT);
        section.setFont(new Font("SansSerif", Font.BOLD, 11));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(section);
        root.add(Box.createRigidArea(new Dimension(0, 16)));

        // Tasks List Panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_DEEP);
        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(listPanel);

        // Logic
        addBtn.addActionListener(e -> {
            String name = taskField.getText().trim();
            String due  = dueField.getText().trim();
            if (name.isEmpty() || name.equals("Task name")) return;

            tm.createTask(name, due.equals("Due date") ? "" : due);
            taskField.setText("");
            dueField.setText("");

            rebuild(listPanel);
        });

        taskField.addActionListener(e -> dueField.requestFocus());
        dueField.addActionListener(e -> addBtn.doClick());

        rebuild(listPanel);
    }

    private void rebuild(JPanel list) {
        list.removeAll();

        for (Task t : tm.getTaskList()) {
            JPanel card = new RoundedPanel(16, BG_CARD);
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel textWrap = new JPanel();
            textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));
            textWrap.setOpaque(false);

            JLabel name = new JLabel(t.getName());
            name.setFont(new Font("SansSerif", Font.BOLD, 14));
            name.setForeground(TEXT_PRI);
            textWrap.add(name);

            if (t.getDueDate() != null && !t.getDueDate().isEmpty()) {
                JLabel due = new JLabel(t.getDueDate());
                due.setFont(new Font("SansSerif", Font.PLAIN, 12));
                due.setForeground(TEXT_MUT);
                textWrap.add(due);
            }

            JButton done = ghostButton("Done");
            done.addActionListener(e -> {
                tm.deleteTask(t.getID());
                rebuild(list);
            });

            card.add(textWrap, BorderLayout.CENTER);
            card.add(done, BorderLayout.EAST);

            // Hover effects
            card.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    card.setBackground(new Color(250, 250, 252));
                }
                public void mouseExited(MouseEvent e) {
                    card.setBackground(BG_CARD);
                }
            });

            list.add(card);
            list.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        list.revalidate();
        list.repaint();
    }

    private JPanel header() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("ToJava");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT_PRI);

        JLabel sub = new JLabel("Task manager");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(TEXT_MUT);

        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(sub);
        return p;
    }

    private JTextField input(String placeholder) {
        JTextField f = new JTextField(placeholder);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        f.setPreferredSize(new Dimension(100, 45));
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        f.setBackground(Color.WHITE);
        f.setForeground(TEXT_MUT);
        f.setAlignmentX(Component.LEFT_ALIGNMENT);

        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) {
                    f.setText("");
                    f.setForeground(TEXT_PRI);
                }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setText(placeholder);
                    f.setForeground(TEXT_MUT);
                }
            }
        });
        return f;
    }

    private JButton button(String text) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        b.setPreferredSize(new Dimension(100, 45));
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(ACCENT_HOV); }
            public void mouseExited(MouseEvent e) { b.setBackground(ACCENT); }
        });
        return b;
    }

    private JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        b.setContentAreaFilled(false);
        b.setForeground(TEXT_MUT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setForeground(new Color(220, 80, 80)); }
            public void mouseExited(MouseEvent e) { b.setForeground(TEXT_MUT); }
        });
        return b;
    }

    static class RoundedPanel extends JPanel {
        private int radius;

        RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            setBackground(bg);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(0, 0, 0, 20));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
}