import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyTextField extends JTextField {
    public MyTextField(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if((key <'1') || (key > '9')){
                    e.setKeyChar((char)KeyEvent.VK_CLEAR);
                }
                if(getText().length() + 1 > 1){
                    e.setKeyChar((char)KeyEvent.VK_CLEAR);
                }
                if(SudokuGUI.judgeWin()){
                    SudokuGUI.stopTiming();
                    new WinFrame();
                    SudokuGUI.setAllDisabled();
                }
            }
        });
        setDisabledTextColor(Color.BLACK);
        setForeground(Color.RED);
    }
}
