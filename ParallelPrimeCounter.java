import java.io.*;
import java.util.*;

public class ParrallelPrimeCounter {
	public static void main(String[] args) {
    FileWriter f = new FileWriter("primes.txt");
    long start = System.currentTimeMillis();

    long end = System.currentTimeMillis();
    f.write(end - start);
    f.close();
  }
}
public class IsPrime extends Thread {
  public void isPrime() {


  }
}
public class AtomicCounter {
  private final AtomicInteger count = new AtomicInteger(0);

  public int getValue() {
    return count.get();
  }
  public void increment() {
    while(1==1) {
      int preVal = getValue();
      int newVal = preVal + 1;
      if(count.compareAndSet(preVal, newVal)) {
        return;
      }
    }
  }
}
