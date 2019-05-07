package com.itxiaox.xview.progressbar;//package com.itxiaox.xxview.progressbar;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//import com.itxiaox.xxview.R;
//
//public class ProgressBarActivity extends Activity {
//    private Timer timer;
//    private DonutProgress donutProgress;
//    private CircleProgress circleProgress;
//    private ArcProgress arcProgress;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_progressbar);
//        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
//        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
//        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        donutProgress.setProgress(donutProgress.getProgress() + 1);
//                        circleProgress.setProgress(circleProgress.getProgress() + 1);
//                        arcProgress.setProgress(arcProgress.getProgress() + 1);
//                    }
//                });
//            }
//        }, 1000, 100);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
//    }
//
//}
