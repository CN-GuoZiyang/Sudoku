import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;

public class SudokuGUI extends JFrame{
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu helpMenu;
    private JMenuItem createNewItem;
    private JMenuItem answerItem;
    private JMenuItem luBenwei;
    private JMenuItem helpItem;
    private JMenuItem aboutItem;
    private JComboBox<String> levelBox;
    private JComboBox<String> diffBox;
    private JButton startBtn;
    private JLabel diff;
    private JLabel showDiff;
    private JLabel level;
    private JLabel showLevel;
    private JLabel time;
    private static JLabel showTime;
    private JPanel mainPanel;
    private JPanel sudokuTable;
    private static JTextField[][] textFields;
    private GenerateSudoku generateSudoku;
    private static TimeThread timing;

    public SudokuGUI(){
        menuBar = new JMenuBar();
        gameMenu = new JMenu("游戏");
        helpMenu = new JMenu("帮助");
        createNewItem = new JMenuItem("开启新战局");
        answerItem = new JMenuItem("参考答案");
        luBenwei = new JMenuItem("卢本伟牛逼");
        helpItem = new JMenuItem("玩法");
        aboutItem = new JMenuItem("关于");
        String[] levels = {"第1关", "第2关", "第3关", "第4关", "第5关", "第6关", "第7关", "第8关", "第9关"};
        levelBox = new JComboBox<>(levels);
        String[] diffs = {"简单", "普通", "困难", "地狱"};
        diffBox = new JComboBox<>(diffs);
        startBtn = new JButton("开始");
        diff = new JLabel("难度：");
        showDiff = new JLabel("简单");
        level = new JLabel("关卡：");
        showLevel = new JLabel("第1关");
        time = new JLabel("时间：");
        showTime = new JLabel("00:00");
        mainPanel = new JPanel();
        sudokuTable = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D)g;
                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.BLACK);
                g2.drawLine(0, 0, 540, 0);
                g2.drawLine(0, 0, 0, 540);
                g2.drawLine(540, 0, 540, 540);
                g2.drawLine(0, 540, 540, 540);
                g2.drawLine(0, 180, 540, 180);
                g2.drawLine(0, 360, 540, 360);
                g2.drawLine(180, 0, 180, 540);
                g2.drawLine(360, 0, 360, 540);
                g2.setColor(Color.CYAN);
                g2.drawLine(0, 60, 540, 60);
                g2.drawLine(0, 120, 540, 120);
                g2.drawLine(0, 240, 540, 240);
                g2.drawLine(0, 300, 540, 300);
                g2.drawLine(0, 420, 540, 420);
                g2.drawLine(0, 480, 540, 480);
                g2.drawLine(60, 0, 60, 540);
                g2.drawLine(120, 0, 120, 540);
                g2.drawLine(240, 0, 240, 540);
                g2.drawLine(300, 0, 300, 540);
                g2.drawLine(420, 0, 420, 540);
                g2.drawLine(480, 0, 480, 540);
            }
        };
        textFields = new MyTextField[9][9];
        generateSudoku = new GenerateSudoku();
        initFrame();
        generateNew();
    }

    public static void setAllDisabled() {
        for(int row = 0; row < 9; row ++){
            for(int col = 0; col < 9; col ++){
                textFields[row][col].setEnabled(false);
            }
        }
    }

    private void initFrame() {
        setSize(540, 613);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Sudoku数独");
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        initMenu();
        setJMenuBar(menuBar);
        initComp();
        initTable();
        initBtn();
        setVisible(true);
    }

    private void initMenu() {
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        createNewItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "确定要开启新战局吗？\n提示：您当前的所有关卡将被重置", "确定吗", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch(choice){
                case 0:generateNew();startBtn.doClick();break;
                case 1:break;
                default:break;
            }
        });
        gameMenu.add(createNewItem);
        answerItem.addActionListener(e -> new AnswerFrame(showLevel.getText().substring(1, 2)));
        gameMenu.add(answerItem);
        luBenwei.addActionListener(new LuBenWeiNiuBi());
        gameMenu.add(luBenwei);
        helpItem.addActionListener(e -> new HelpFrame());
        helpMenu.add(helpItem);
        aboutItem.addActionListener(e -> new AboutFrame());
        helpMenu.add(aboutItem);
    }

    private void initComp() {
        levelBox.setBounds(10, 3, 90, 30);
        mainPanel.add(levelBox);

        diffBox.setBounds(100, 3, 80, 30);
        mainPanel.add(diffBox);

        startBtn.setBounds(185, 3, 60, 31);
        mainPanel.add(startBtn);

        diff.setBounds(255, 3, 40, 31);
        mainPanel.add(diff);

        showDiff.setBounds(295, 3, 40, 31);
        mainPanel.add(showDiff);

        level.setBounds(345, 3, 40, 31);
        mainPanel.add(level);

        showLevel.setBounds(385, 3, 40, 31);
        mainPanel.add(showLevel);

        time.setBounds(440, 3, 40, 31);
        mainPanel.add(time);

        showTime.setBounds(480, 3, 40, 31);
        mainPanel.add(showTime);
    }

    private void initTable(){
        sudokuTable.setBounds(0, 30, 540, 540);
        sudokuTable.setLayout(new GridLayout(9, 9));
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j ++){
                textFields[i][j] = new MyTextField();
                textFields[i][j].setFont(new Font("宋体", Font.PLAIN, 40));
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                sudokuTable.add(textFields[i][j]);
            }
        }
        mainPanel.add(sudokuTable);
    }

    private void initBtn(){
        startBtn.addActionListener(e -> {
            String level = ((String)levelBox.getSelectedItem()).substring(1, 2);
            String diffStr = (String)diffBox.getSelectedItem();
            showLevel.setText("第" + level + "关");
            showDiff.setText(diffStr);
            String realDiff = null;
            int[][] levelArray = new int[9][9];
            int[][] diffArray = new int[9][9];
            realDiff = getString(diffStr, realDiff);
            try {
                BufferedReader br = new BufferedReader(new FileReader("./levels/level_" + level + ".txt"));
                for(int row = 0; row < 9; row ++){
                    String tempLine = br.readLine();
                    for(int col = 0; col < 9; col ++){
                        levelArray[row][col] = Integer.parseInt(tempLine.split(",")[col]);
                    }
                }
                br = new BufferedReader(new FileReader("./levels/level_"  + level + "_" + realDiff + ".txt"));
                for(int row = 0; row < 9; row ++){
                    String tempLine = br.readLine();
                    for(int col = 0; col < 9; col ++){
                        diffArray[row][col] = Integer.parseInt(tempLine.split(",")[col]);
                    }
                }
                br.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
            for(int row = 0; row < 9; row ++){
                for(int col = 0; col < 9; col ++){
                    if(diffArray[row][col] == 0){
                        textFields[row][col].setText(String.valueOf(levelArray[row][col]));
                        textFields[row][col].setEnabled(false);
                    }else{
                        textFields[row][col].setText("");
                        textFields[row][col].setEnabled(true);
                    }
                }
            }
            startTiming();
        });
    }

    private String getString(String diffStr, String realDiff) {
        switch (diffStr){
            case "简单": realDiff = "EASY";break;
            case "普通": realDiff = "MEDIUM";break;
            case "困难": realDiff = "DIFFICULT";break;
            case "地狱": realDiff = "EVIL";break;
            default:break;
        }
        return realDiff;
    }

    private void generateNew(){
        File levelFile = new File("./levels");
        if(!levelFile.exists()){
            levelFile.mkdir();
        }
        try {
            for (int i = 1; i < 10; i++) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("./levels/level_" + i + ".txt"));
                int[][] sudokuMatrix = generateSudoku.generatePuzzleMatrix();
                writeMatrix(bw, sudokuMatrix);
                String[] diffs = {"EASY", "MEDIUM", "DIFFICULT", "EVIL"};
                for(String tempdiff : diffs){
                    int[][] diffArray = GetDifficultyArray.getDifficultyArray(tempdiff);
                    bw = new BufferedWriter(new FileWriter("./levels/level_" + i + "_" + tempdiff + ".txt"));
                    writeMatrix(bw, diffArray);
                }
                bw.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeMatrix(BufferedWriter bw, int[][] sudokuMatrix) throws IOException {
        for(int row = 0; row < 9; row ++){
            for(int col = 0; col < 9; col ++){
                bw.write(sudokuMatrix[row][col] + ",");
                bw.flush();
            }
            bw.newLine();
        }
    }

    private void startTiming(){
        showTime.setText("00:00");
        stopTiming();
        timing = new TimeThread(this);
        timing.start();
    }

    public void setTime(String time){
        showTime.setText(time);
    }

    public static boolean judgeWin(){
        for(JTextField[] samples : textFields){
            for(JTextField sample : samples){
                if(sample.getText().equals("")){
                    System.out.println("Empty judge finished. false");
                    return false;
                }
            }
        }
        System.out.println("Empty judge finished. success");

        for(int row = 0; row < 9; row ++){
            HashSet<Integer> rowTempHash = new HashSet<>();
            for(int col = 0; col < 9; col ++){
                rowTempHash.add(Integer.parseInt(textFields[row][col].getText()));
            }
            if(rowTempHash.size() != 9){
                System.out.println("Row judge finished. false");
                return false;
            }
        }
        System.out.println("Row judge finished. success");

        for(int col = 8; col >= 0; col--){
            HashSet<Integer> colTempHash = new HashSet<>();
            for(int row = 0; row < 9; row ++){
                colTempHash.add(Integer.parseInt(textFields[row][col].getText()));
            }
            if(colTempHash.size() != 9){
                System.out.println("Col judge finished. false");
                return false;
            }
        }
        System.out.println("Col judge finished. success");

        for(int block = 0; block < 9; block ++){
            int rowBase = block / 3 * 3;
            int colBase = block % 3 * 3;
            HashSet<Integer> blockHash = new HashSet<>();
            for(int i = 0; i < 9; i ++ ){
                int row = i / 3;
                int col = i % 3;
                blockHash.add(Integer.parseInt(textFields[rowBase + row][colBase + col].getText()));
            }
            if(blockHash.size() != 9){
                System.out.println("Block judge finished. false");
                return false;
            }
        }
        System.out.println("Block judge finished. success");
        System.out.println("All judge finished. success");
        return true;
    }

    public static void stopTiming(){
        if(timing != null){
            while(timing.isAlive()){
                timing.terminate();
            }
        }
    }

    public static String getTime(){
        return showTime.getText();
    }

    class LuBenWeiNiuBi implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String diffStr = showDiff.getText();
            String levelStr = showLevel.getText();
            String diff = null;
            diff = getString(diffStr, diff);
            String level = levelStr.substring(1, 2);
            try {
                BufferedReader br = new BufferedReader(new FileReader("./levels/level_" + level + ".txt"));
                int[][] levelMatrix = new int[9][9];
                for (int row = 0; row < 9; row++) {
                    String tempRow = br.readLine();
                    for(int col = 0; col < 9; col ++){
                        levelMatrix[row][col] = Integer.parseInt(tempRow.split(",")[col]);
                    }
                }
                br = new BufferedReader(new FileReader("./levels/level_" + level + "_" + diff + ".txt"));
                int[][] diffMatrix = new int[9][9];
                for (int row = 0; row < 9; row++) {
                    String tempRow = br.readLine();
                    for(int col = 0; col < 9; col ++){
                        diffMatrix[row][col] = Integer.parseInt(tempRow.split(",")[col]);
                    }
                }
                for(int row = 0; row < 9; row ++){
                    for(int col = 0; col < 9; col ++){
                        if(diffMatrix[row][col] == 1){
                            textFields[row][col].setText(String.valueOf(levelMatrix[row][col]));
                        }
                    }
                }
                br.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }
}
