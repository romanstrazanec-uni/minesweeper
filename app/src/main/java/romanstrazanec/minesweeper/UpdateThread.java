package romanstrazanec.minesweeper;

import android.os.Handler;

public class UpdateThread extends Thread {
    Handler updatingHandler;

    public UpdateThread(Handler updatingHandler) {
        super();
        this.updatingHandler = updatingHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (Exception ex) {
            }
            updatingHandler.sendEmptyMessage(0);
        }
    }
}
