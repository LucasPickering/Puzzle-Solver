package puzzlesolver.ui.fx_2d;

import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class VariableRateTimer extends Timer {
  private Timer delegate = new Timer();
  private final Object lock = new Object();

  public VariableRateTimer() {
    delegate = new Timer();
  }

  public VariableRateTimer(boolean isDaemon) {
    delegate = new Timer(isDaemon);
  }

  public VariableRateTimer(String name) {
    delegate = new Timer(name);
  }

  public VariableRateTimer(String name, boolean isDaemon) {
    delegate = new Timer(name, isDaemon);
  }

  private VariableRateTimer(Timer delegate) {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null ) {
      return false;
    }
    if (getClass() != o.getClass()) {
      if (o.getClass() == delegate.getClass()) {
        return delegate.equals(o);
      }
    }
    return Objects.equals(delegate, ((VariableRateTimer) o).delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public void schedule(TimerTask task, long delay) {
    delegate.schedule(task, delay);
  }

  @Override
  public void schedule(TimerTask task, Date time) {
    delegate.schedule(task, time);
  }

  @Override
  public void schedule(TimerTask task, long delay, long period) {
    delegate.schedule(task, delay, period);
  }

  @Override
  public void schedule(TimerTask task, Date firstTime, long period) {
    delegate.schedule(task, firstTime, period);
  }

  @Override
  public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
    delegate.scheduleAtFixedRate(task, delay, period);
  }

  @Override
  public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
    delegate.scheduleAtFixedRate(task, firstTime, period);
  }

  @Override
  public void cancel() {
    delegate.cancel();
  }

  @Override
  public int purge() {
    return delegate.purge();
  }
}
