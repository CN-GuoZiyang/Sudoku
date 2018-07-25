import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnswerFrame extends JFrame{
    private JPanel mainPanel;
    private JPanel answerPanel;
    private JTextField[][] textFields;

    public AnswerFrame(String level){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(360, 380);
        setResizable(false);
        setTitle("参考答案");
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        answerPanel = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D)g;
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.BLACK);
                g2.drawLine(0, 120, 360, 120);
                g2.drawLine(0, 240, 360, 240);
                g2.drawLine(120, 0, 120, 360);
                g2.drawLine(240, 0, 240, 360);
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.CYAN);
                g2.drawLine(0, 40, 360, 40);
                g2.drawLine(0, 80, 360, 80);
                g2.drawLine(0, 160, 360, 160);
                g2.drawLine(0, 200, 360, 200);
                g2.drawLine(0, 280, 360, 280);
                g2.drawLine(0, 320, 360, 320);
                g2.drawLine(40, 0, 40, 360);
                g2.drawLine(80, 0, 80, 360);
                g2.drawLine(160, 0, 160, 360);
                g2.drawLine(200, 0, 200, 360);
                g2.drawLine(280, 0, 280, 360);
                g2.drawLine(320, 0, 320, 360);
            }
        };
        answerPanel.setBounds(0, 0, 360, 360);
        answerPanel.setLayout(new GridLayout(9, 9));
        mainPanel.add(answerPanel);
        textFields = new JTextField[9][9];
        try {
            BufferedReader br = new BufferedReader(new FileReader("./levels/level_" + level + ".txt"));
            for(int row = 0; row < 9; row ++){
                String tempLine = br.readLine();
                for(int col = 0; col < 9; col ++){
                    textFields[row][col] = new JTextField();
                    textFields[row][col].setSize(40, 40);
                    textFields[row][col].setFont(new Font("宋体", Font.PLAIN, 30));
                    textFields[row][col].setHorizontalAlignment(JTextField.CENTER);
                    textFields[row][col].setDisabledTextColor(Color.BLACK);
                    textFields[row][col].setEnabled(false);
                    textFields[row][col].setText(tempLine.split(",")[col]);
                    answerPanel.add(textFields[row][col]);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        setVisible(true);
    }
}
