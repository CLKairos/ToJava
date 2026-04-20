package org.clkairos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class renderingManager {
    private JFrame frame;
    private taskManager tm;

    private static final Color BG_DEEP    = new Color(248, 248, 250);
    private static final Color BG_CARD    = new Color(255, 255, 255);
    private static final Color ACCENT     = new Color(99, 91, 210);
    private static final Color ACCENT_HOV = new Color(120, 112, 235);
    private static final Color TEXT_PRI   = new Color(20, 20, 30);
    private static final Color TEXT_MUT   = new Color(140, 140, 155);

    private static final int PADDING_OUTER  = 30;
    private static final int PADDING_SIDE   = 40;
    private static final int SPACING_LG     = 24;
    private static final int SPACING_MD     = 12;
    private static final int SPACING_XL     = 32;
    private static final int INPUT_HEIGHT   = 45;
    private static final int CARD_HEIGHT    = 70;
    private static final int FONT_TITLE     = 28;
    private static final int FONT_BODY      = 14;
    private static final int FONT_SMALL     = 12;
    private static final int FONT_LABEL     = 11;

    public renderingManager(taskManager tm) {
        this.tm = tm;
        Main.log("renderingManager: created");
    }

    public void init(int width, int height) {
        Main.log("renderingManager.init: " + width + "x" + height);
        SwingUtilities.invokeLater(() -> {
            Main.log("renderingManager.init: building frame on EDT");
            frame = new JFrame("ToJava");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(width, height);
            frame.setMinimumSize(new Dimension(400, 500));

            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(BG_DEEP);

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setBackground(BG_DEEP);
            container.setBorder(BorderFactory.createEmptyBorder(PADDING_OUTER, PADDING_SIDE, PADDING_OUTER, PADDING_SIDE));

            buildUI(container);

            JScrollPane mainScroll = new JScrollPane(container);
            mainScroll.setBorder(null);
            mainScroll.getVerticalScrollBar().setUnitIncrement(16);

            frame.add(mainScroll, BorderLayout.CENTER);
            frame.setVisible(true);
            Main.log("renderingManager.init: frame visible");
        });
    }

    private void buildUI(JPanel root) {
        Main.log("buildUI: constructing UI");
        root.removeAll();

        root.add(header());
        root.add(Box.createRigidArea(new Dimension(0, SPACING_LG)));

        JTextField taskField = input("Task name");
        JTextField dueField  = input("Due date");
        JButton addBtn       = button("Add task");

        root.add(taskField);
        root.add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        root.add(dueField);
        root.add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        root.add(addBtn);
        root.add(Box.createRigidArea(new Dimension(0, SPACING_XL)));

        JLabel section = new JLabel("TASKS");
        section.setForeground(TEXT_MUT);
        section.setFont(new Font("SansSerif", Font.BOLD, FONT_LABEL));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(section);
        root.add(Box.createRigidArea(new Dimension(0, SPACING_MD + 4)));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_DEEP);
        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(listPanel);

        addBtn.addActionListener(e -> {
            String name = taskField.getText().trim();
            String due  = dueField.getText().trim();
            Main.log("addBtn clicked: name=\"" + name + "\" due=\"" + due + "\"");

            if (name.isEmpty() || name.equals("Task name")) {
                Main.log("addBtn: rejected empty or placeholder name");
                return;
            }

            tm.createTask(name, due.equals("Due date") ? "" : due);
            taskField.setText("");
            dueField.setText("");
            rebuild(listPanel);
        });

        taskField.addActionListener(e -> {
            Main.log("taskField: Enter pressed, moving focus to dueField");
            dueField.requestFocus();
        });
        dueField.addActionListener(e -> {
            Main.log("dueField: Enter pressed, triggering addBtn");
            addBtn.doClick();
        });

        rebuild(listPanel);
        Main.log("buildUI: complete");
    }

    private void rebuild(JPanel list) {
        Main.log("rebuild: refreshing task list (" + tm.getTaskList().size() + " tasks)");
        list.removeAll();

        for (Task t : tm.getTaskList()) {
            Main.log("rebuild: rendering task id=" + t.getID() + " name=\"" + t.getName() + "\"");

            JPanel card = new RoundedPanel(16, BG_CARD);
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD + 4, SPACING_MD, SPACING_MD + 4));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, CARD_HEIGHT));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel textWrap = new JPanel();
            textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));
            textWrap.setOpaque(false);

            JLabel name = new JLabel(t.getName());
            name.setFont(new Font("SansSerif", Font.BOLD, FONT_BODY));
            name.setForeground(TEXT_PRI);
            textWrap.add(name);

            if (t.getDueDate() != null && !t.getDueDate().isEmpty()) {
                JLabel due = new JLabel(t.getDueDate());
                due.setFont(new Font("SansSerif", Font.PLAIN, FONT_SMALL));
                due.setForeground(TEXT_MUT);
                textWrap.add(due);
            }

            JButton done = ghostButton("Done");
            done.addActionListener(e -> {
                Main.log("done clicked: deleting task id=" + t.getID());
                tm.deleteTask(t.getID());
                rebuild(list);
            });

            card.add(textWrap, BorderLayout.CENTER);
            card.add(done, BorderLayout.EAST);

            card.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    Main.log("card hover: task id=" + t.getID());
                    card.setBackground(new Color(250, 250, 252));
                }
                public void mouseExited(MouseEvent e) {
                    card.setBackground(BG_CARD);
                }
            });

            list.add(card);
            list.add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        }

        list.revalidate();
        list.repaint();
        Main.log("rebuild: done");
    }

    private JPanel header() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("ToJava");
        title.setFont(new Font("SansSerif", Font.BOLD, FONT_TITLE));
        title.setForeground(TEXT_PRI);

        JLabel sub = new JLabel("Task manager");
        sub.setFont(new Font("SansSerif", Font.PLAIN, FONT_BODY));
        sub.setForeground(TEXT_MUT);

        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(sub);
        return p;
    }

    private JTextField input(String placeholder) {
        JTextField f = new JTextField(placeholder);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        f.setPreferredSize(new Dimension(100, INPUT_HEIGHT));
        f.setFont(new Font("SansSerif", Font.PLAIN, FONT_BODY));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true),
                BorderFactory.createEmptyBorder(0, SPACING_MD, 0, SPACING_MD)
        ));
        f.setBackground(Color.WHITE);
        f.setForeground(TEXT_MUT);
        f.setAlignmentX(Component.LEFT_ALIGNMENT);

        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                Main.log("input focusGained: placeholder=\"" + placeholder + "\"");
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
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        b.setPreferredSize(new Dimension(100, INPUT_HEIGHT));
        b.setFont(new Font("SansSerif", Font.BOLD, FONT_BODY));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(ACCENT_HOV); }
            public void mouseExited(MouseEvent e)  { b.setBackground(ACCENT); }
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
            public void mouseExited(MouseEvent e)  { b.setForeground(TEXT_MUT); }
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