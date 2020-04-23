package sk.romanstrazanec.minesweeper;

import android.os.Handler;

public class UpdateThread extends Thread {
    Handler updatingHandler;

    public UpdateThread(Handler updatingHandler) {
        super();
        this.updatingHandler = updatingHandler;
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (Exception ignored) {
            }
            updatingHandler.sendEmptyMessage(0);
        }
    }
}
