import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpFrame extends JFrame{
    private JPanel mainPanel;
    private JLabel introLabel;
    private JButton closeBtn;

    public HelpFrame(){
        setTitle("数独玩法");
        setSize(377, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 377, 270);
        add(mainPanel);
        introLabel = new JLabel();
        introLabel.setText("<html><body><center>数独是源自18世纪瑞士的一种数学游戏。<br>" +
                "玩家需要根据9×9盘面上的已知数字，<br>推理出所有剩余空格的数字<br>" +
                "满足每一行、每一列、每一个粗线宫（3*3）内的数字均含1-9<br>" +
                "不重复。数独盘面是个九宫，<br>每一宫又分为九个小格。<br><br>" +
                "在这八十一格中给出一定的已知数字和解题条件，<br>利用逻辑和推理，<br>" +
                "在其他的空格上填入1-9的数字。<br>" +
                "使1-9每个数字在每一行、每一列和每一宫中都只出现一次<br>" +
                "所以又称“九宫格”。</center></body></html>");
        introLabel.setBounds(10,10, 360, 200);
        mainPanel.add(introLabel);
        closeBtn = new JButton("确定");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        closeBtn.setBounds(135, 217, 100, 31);
        mainPanel.add(closeBtn);
        setVisible(true);
    }
}
