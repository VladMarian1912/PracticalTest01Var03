package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var03Service extends Service {
    private ProcessingThread processingThread;
    private boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.SERVICE_TAG, "startService() method was invoked");
        int nr1 = intent.getIntExtra(Constants.FIRST_NUMBER, 0);
        int nr2 = intent.getIntExtra(Constants.SECOND_NUMBER, 0);
        processingThread = new ProcessingThread(this, nr1, nr2);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.SERVICE_TAG, "Service has stopped!");
        processingThread.stopThread();
    }
}
