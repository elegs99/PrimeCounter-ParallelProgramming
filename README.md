# PrimeCounter-ParallelProgramming

I based my approach around a counter class that used an Atomic Integer to keep track of which numbers had been evaluated already. I believe this solution is correct because each thread can only evaluate once given a number by the counter class. Since the counter operation is atomic there is no way to miss a number or evaluate it twice. Additionally, the count, sum, and max isn't concerned with the order in which the primes are found so it does not matter in what order the threads finish. 
To evaulate the efficiency of my algorithim I manually set only 1 thread to be spawned. The 1 thread took 146 seconds to run on my machine whereas the 8 threads working together only took 112 seconds.

To run my program first navigate to directory with my java file then enter the following commands in your terminal:
- javac ParallelPrimeCounter.java
- java ParallelPrimeCounter

primes.txt will be stored in the same folder as the java file
