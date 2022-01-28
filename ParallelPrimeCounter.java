import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelPrimeCounter {

  static class Counter {
      private AtomicInteger index = new AtomicInteger(1);

      public int increment() {
         return index.getAndAdd(2);
      }

      public int value() {
         return index.get();
      }
   }

	public static void main(String[] args) {
    long target = (long)Math.pow(10, 8);
    long start = System.currentTimeMillis();
    int primeCount = 1; //acounting for the 2 we are skipping
    long primeSum = 2;
    Queue<Integer> maxPrimesList = new PriorityQueue<>(10);

    Counter index = new Counter();
    IsPrime[] threads = new IsPrime[8];
    for (int i = 0; i < 8; i++) {
      threads[i] = new IsPrime();
      threads[i].start();
    }
    while (index.value() <= target) {
      for (int i = 0; i < 8; i++) {
        if (index.value() <= target) {
          if (threads[i].IsPrime(index.increment())) {
            int primeVal = index.value()-2;
            primeSum += primeVal;
            primeCount++;
            if (maxPrimesList.size() < 10) {
              maxPrimesList.add(primeVal);
            }
            else {
              int lowestPrime = maxPrimesList.poll();
              if (lowestPrime < primeVal) {
                maxPrimesList.add(primeVal);
              }
              else {
                maxPrimesList.add(lowestPrime);
              }
            }
          }
        }
      }
    }

    long end = System.currentTimeMillis();
    try {
      FileWriter f = new FileWriter("primes.txt");
      f.write(String.valueOf((int)(end - start)/1000) + " Seconds");
      f.write(",  Total count of prime numbers: " + String.valueOf(primeCount));
      f.write(",  Total sum of prime numbers: " + String.valueOf(primeSum) + "\n[");
      f.write(String.valueOf(maxPrimesList.poll()));
      while (!maxPrimesList.isEmpty()) {
        f.write(", " + String.valueOf(maxPrimesList.poll()));
      }
      f.write("]");
      f.close();
    } catch (IOException e) {
      System.out.println("error with file IO");
      e.printStackTrace();
    }
  }
}

class IsPrime extends Thread {
  public boolean IsPrime(int currVal) {
    if (currVal < 2) return false;
    if (currVal == 2 || currVal == 3) return true;
    long primeCeiling = (long)Math.sqrt(currVal)+1;
    for (int checkVal = 2; checkVal <= primeCeiling; checkVal++) {
      if (currVal % checkVal == 0) return false;
    }
    return true;
  }
}
