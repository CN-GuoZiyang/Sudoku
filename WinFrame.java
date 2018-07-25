import javax.swing.*;
import java.awt.*;

public class WinFrame extends JFrame{
    private String time;
    private JPanel winPanel;
    private JLabel winLabel;
    private JLabel timeLabel;
    private JButton closeBtn;

    public WinFrame(){
        setTitle("You Win!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setSize(300, 175);
        winPanel = new JPanel();
        winPanel.setLayout(null);
        setContentPane(winPanel);
        winLabel = new JLabel("恭喜，你赢了。本关共用时");
        winLabel.setBounds(75, 10, 200, 30);
        winPanel.add(winLabel);
        time = SudokuGUI.getTime();
        timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("宋体", Font.PLAIN, 60));
        timeLabel.setBounds(70, 20, 200, 100);
        winPanel.add(timeLabel);
        closeBtn = new JButton("确定");
        closeBtn.setBounds(115, 105, 80, 40);
        closeBtn.addActionListener(e -> {
            setVisible(false);
        });
        winPanel.add(closeBtn);
        setVisible(true);
    }
}
