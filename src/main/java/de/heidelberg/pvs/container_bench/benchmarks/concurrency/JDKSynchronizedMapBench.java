package de.heidelberg.pvs.container_bench.benchmarks.concurrency;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import de.heidelberg.pvs.container_bench.factories.JDKSyncMap2IntFact;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Timeout(time = 1, timeUnit = TimeUnit.MINUTES)
@Warmup(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 40, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@Threads(1)
@State(Scope.Benchmark)
public class JDKSynchronizedMapBench {

	@Param
	JDKSyncMap2IntFact impl;

	@Param({ "100", "1000", "10000", "100000", "1000000" })
	int size;

	@Param({ "42" })
	int seed;

	Integer values[];
	String keys[];

	ElementGenerator<String> keysGenerator;
	ElementGenerator<Integer> valuesGenerator;

	Blackhole blackhole;

	Map<Object, Integer> sharedEmptyMap;

	// @Param
	// PayloadType payloadType;

	@Setup(Level.Iteration)
	@SuppressWarnings("unchecked")
	public void setup(Blackhole bh) throws IOException {
		sharedEmptyMap = impl.maker.get();

		keysGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(PayloadType.STRING_DICTIONARY);
		keysGenerator.init(size, seed);

		valuesGenerator = (ElementGenerator<Integer>) GeneratorFactory
				.buildRandomGenerator(PayloadType.INTEGER_UNIFORM);
		valuesGenerator.init(size, seed);

		values = valuesGenerator.generateArray(size);
		keys = keysGenerator.generateArray(size);

		blackhole = bh;

	}

	@Benchmark
	@Group("readwrite")
	@GroupThreads(10)
	public void readFromState() {
		int index = keysGenerator.generateIndex(size);
		blackhole.consume(sharedEmptyMap.get(keys[index]));

	}

	@Benchmark
	@Group("readwrite")
	@GroupThreads(1)
	public void writeOnState() {
		for (int i = 0; i < size; i++) {
			sharedEmptyMap.put(keys[i], values[i]);
		}
	}

	@Benchmark
	@Group("searchAndAdd")
	@GroupThreads(10)
	public void searchAndAdd() {

		int index = this.keysGenerator.generateIndex(size);
		if (!sharedEmptyMap.containsKey(keys[index])) {
			sharedEmptyMap.put(keys[index], values[index]);
		}

	}

	@Benchmark
	@Group("addAndRemove")
	@GroupThreads(1)
	public void add() {
		int index = this.keysGenerator.generateIndex(size);
		sharedEmptyMap.put(keys[index], values[index]);
	}
	

	@Benchmark
	@Group("addAndRemove")
	@GroupThreads(1)
	public void remove() {
		int index = this.keysGenerator.generateIndex(size);
		sharedEmptyMap.remove(keys[index]);
	}

}
