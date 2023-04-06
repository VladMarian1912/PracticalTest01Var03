package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread{
    private Context context = null;
    private boolean isRunning = true;

    int nr1, nr2, suma, diff;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        nr1 = firstNumber;
        nr2 = secondNumber;
        suma = nr1 + nr2;
        diff = nr1 - nr2;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage1();
            sleep();
            sendMessage2();
            stopThread();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage1() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.suma");
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + suma);
        context.sendBroadcast(intent);
    }

    private void sendMessage2() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.dif");
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + diff);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
