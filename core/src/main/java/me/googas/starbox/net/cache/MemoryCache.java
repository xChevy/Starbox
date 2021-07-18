package me.googas.starbox.net.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import lombok.experimental.Delegate;

public class MemoryCache extends TimerTask implements Cache {

  /** The map required for the cache */
  @NonNull @Delegate
  private final Map<SoftReference<Catchable>, Long> map = new ConcurrentHashMap<>();

  public MemoryCache register(@NonNull Timer timer) {
    timer.schedule(this, 1000, 1000);
    return this;
  }

  @Override
  public void run() {
    Cache.super.run();
  }

  @Override
  public @NonNull Map<SoftReference<Catchable>, Long> getMap() {
    return this.map;
  }
}
