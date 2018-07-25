public class TimeThread extends Thread{
    private SudokuGUI sudokuGUI;
    private int second = 0;
    private int minute = 0;
    private boolean flag = true;

    public TimeThread(SudokuGUI sudokuGUI){
        this.sudokuGUI = sudokuGUI;
    }

    public void run(){
        while(flag) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second++;
            if (second == 60) {
                minute++;
                second = 0;
            }
            sudokuGUI.setTime(String.format("%02d", minute) + ":" + String.format("%02d", second));
        }
    }

    public void terminate() {
        this.flag = false;
    }
}
