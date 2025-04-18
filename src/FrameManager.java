import javax.swing.*;
import java.awt.*;

public class FrameManager extends Frame {
    JFrame window = new JFrame("Todo List frfr");
    JPanel todoPanel = new JPanel();
    JPanel startedPanel = new JPanel();
    JPanel finishedPanel = new JPanel();
    JLabel todoLabel = new JLabel("Todo Tasks");
    JLabel startedLabel = new JLabel("Started Tasks");
    JLabel finishedLabel = new JLabel("Finished Tasks");
    
    todoPanel.add(todoLabel);
    window.add(todoPanel);
}
