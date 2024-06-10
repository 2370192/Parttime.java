// PartTime.java
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class PartTime {
    private double hourlyWage;
    private double totalEarnings;
    private double totalWorkTime; // 今日の総労働時間（秒）
    private Timer earningsTimer;
    private long startTimeMillis; // 働き始めの時間

    public PartTime() {
        this.totalEarnings = 0;
        this.totalWorkTime = 0;
        this.earningsTimer = new Timer();
    }

    public void startEarningsTimer(PartTimeListener listener) {
        startTimeMillis = System.currentTimeMillis(); // 働き始めの時間を記録

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // その日の稼ぎと働いた時間を計算
                long currentTimeMillis = System.currentTimeMillis();
                totalEarnings += hourlyWage / 3600; // 1時間を3600秒として計算
                totalWorkTime += (currentTimeMillis - startTimeMillis) / 1000.0; // ミリ秒を秒に変換して追加
                startTimeMillis = currentTimeMillis; // 働き始めの時間を更新

                // コールバックで結果を通知
                if (listener != null) {
                    listener.onEarningsUpdated(totalEarnings, totalWorkTime);
                }
            }
        };

        // 1秒ごとにタイマータスクを実行
        earningsTimer.scheduleAtFixedRate(task, 0, 1000);
    }

    public double stopEarningsTimer() {
        earningsTimer.cancel(); // タイマーをキャンセルして停止
        return totalEarnings;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
}

