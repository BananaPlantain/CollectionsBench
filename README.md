# Collections-Bench

Collections-Bench is a benchmark suite built upon [JMH] (http://openjdk.java.net/projects/code-tools/jmh/) for performance evaluation of Java Collections libraries.

## Usage

To build Collections-bench you need [Maven] (https://maven.apache.org/) and Java version >= 8.

**Build the jar file:** Use `maven install` to generate the .jar file. It will be generated by default no /target folder.

**Running the complete benchmark** Use `java -jar Benchmarks.jar` to run the complete benchmark with the default parameters. The complete benchmark with default parameters takes **3 days** to finish, therefore we came with a naming system to allow partial benchmarks to be run easily.

**Running a specific benchmark** The JMH allows regex when specifying the classes that will be executed during the benchmark. To facilitate, we design the classes name, to contain the configuration about the benchmark such as the library, collection type and data type:

Each class has the following name structure: [LIBRARY]_[DATATYPE]_[COLLECTIONTYPE]_Test.java

For instance, to run the benchmark of standard (JDK) ArrayList containing integers, you can execute the following command:

```
java -jar Benchmarks.jar JDK_Integer_ArrayList_*
```

**Specifying the benchmark parameters** The benchmark parameters are defined in the class AbstractBenchmarkTest.java (10 warmups, 30 replications, 1 second each). You can re-define the parameters by using the JMH command line structure. 

For 20 warmup iterations, 30 replications of 5 seconds each.
```
java -jar Benchmarks.jar -wi 20 -i 30 -r 5
```

For more information about the parameters, run the help menu: `java -jar Benchmarks.jar -h`


## Available Test Scenarios

Currently implemented scenarios. Each scenario is implemented in a distinct method, and can be specified in the benchmark parameters. 

| Scenario	| Description											|
| --------- | -----------------------------------------				|
| add		| Add a random element into the collection. 			|
| populate	| Populate an empty collection with N random elements 	|
| get		| Get a random element from the collection 				|
| iterate	| Traverse the collection								| 
| remove	| Remove a random element from the collection 			|
| copy		| Copy to an empty collection							|  
| contains	| Search a random element in the collection 			|

## Available Collection Libraries
 
We have implemented the benchmark for alternatives of the four most commonly used Java collections, ie, ArrayList, LinkedList, HashSet and HashMap with implementations of the following libraries:  
 
| Library	| Version	|
| --------- | --------- |
| JDK (SDK8)| 8.0_65	|
| Fastutil	| 7.0.10	|
| Guava 	| 18.0		|
| Koloboke	| 0.6.8		|
| HPPC		| 0.7.1		|
| GSCollections	| 6.2.0	|
| Trove		| 3.0.3		|
 	

 
