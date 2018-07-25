import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class AboutFrame extends JFrame{
    private JPanel mainPanel;
    private JLabel aboutLabel;
    private JButton openGithub;
    private JButton closeBtn;

    public AboutFrame(){
        setTitle("关于");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(300, 230);
        setResizable(false);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
        aboutLabel = new JLabel();
        String aboutStr = "<html><body><center>一个简单的数独游戏。<br>" +
                "可以……emmm……玩<br>" +
                "欢迎贡献你的代码帮助我提升<br>" +
                "如果你觉得我做的好<br>" +
                "请打开我的GitHub给我一个star<br>" +
                "My Gmail：<br>guoziyang0033@gmail.com<br>" +
                "My Github：<br>https://github.com/CN-GuoZiyang</center></body></html>";
        aboutLabel.setText(aboutStr);
        aboutLabel.setBounds(30, 8, 310, 160);
        mainPanel.add(aboutLabel);
        openGithub = new JButton("打开github");
        openGithub.setBounds(10, 170, 110, 29);
        openGithub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://github.com/CN-GuoZiyang"));
                }catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        mainPanel.add(openGithub);
        closeBtn = new JButton("确定");
        closeBtn.setBounds(175, 170, 110, 29);
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        mainPanel.add(closeBtn);
        setVisible(true);
    }
}
