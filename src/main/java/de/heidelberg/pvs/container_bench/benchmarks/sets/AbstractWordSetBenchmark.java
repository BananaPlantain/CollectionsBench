package de.heidelberg.pvs.container_bench.benchmarks.sets;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
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

import de.heidelberg.pvs.container_bench.generators.Wordlist;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Timeout(time = 10, timeUnit = TimeUnit.MINUTES)
@Warmup(iterations = 5)
@Measurement(iterations = 11)
@Fork(1)
@Threads(1)
@State(Scope.Thread)
public abstract class AbstractWordSetBenchmark<T> {
	/** Workload to process */
	@Param
	public Workload workload;

	/**
	 * Number of words to load from the file.
	 *
	 * Named zz to have this sort last.
	 */
	@Param({ "100000" })
	public int zzsize = 100_000;

	/** -1: no random shuffling */
	@Param({ "-1" })
	public int seed = -1;

	Blackhole bh;

	T set;

	/** Word list */
	protected List<String> words;

	@Setup(Level.Iteration)
	public void setup(Blackhole b) throws IOException, InterruptedException {
		bh = b;
		words = words != null ? words : Wordlist.loadWords(zzsize, seed);
		workload.init(this);
	}

	@Benchmark
	public void bench() throws InterruptedException {
		workload.run(this);
		bh.consume(set); // prevent elimination
	}

	/**
	 * Provide the set to use.
	 *
	 * @return Set
	 */
	abstract protected T makeSet();

	/**
	 * Add a single object
	 *
	 * @param object
	 *            Object to add.
	 */
	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	abstract protected void add(String object);

	/**
	 * Test for containment.
	 *
	 * @param object
	 *            Object to check.
	 */
	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	abstract protected boolean contains(String object);

	/**
	 * Remove a single object
	 *
	 * @param object
	 *            Object to remove.
	 */
	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	abstract protected void remove(String object);

	public enum Workload {
		ADD {
			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
				}
			}
		}, //
		REMOVE {
			@Override
			public <T> void init(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				self.set = self.makeSet(); // Same as super()
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
				}
			}

			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.remove(words.get(i));
				}
			}
		}, //
		CONTAINS {
			@Override
			public <T> void init(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				self.set = self.makeSet(); // Same as super()
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
					++i; // Skip every other word for the contains benchmark.
				}
			}

			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				int found = 0;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					if (self.contains(words.get(i))) {
						++found;
					}
				}
				self.bh.consume(found); // Prevent elimination
			}
		}, //
		ADD_OR_REMOVE {
			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted();) {
					self.add(words.get(i++));
					if (i >= size) {
						break;
					}
					self.remove(words.get(i++));
				}
			}
		}, //
		ADD_THEN_REMOVE {
			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
				}
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.remove(words.get(i));
				}
			}
		}, //
		REMOVE_THEN_ADD {
			@Override
			public <T> void init(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				self.set = self.makeSet(); // same as super();
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
				}
			}

			@Override
			public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException {
				List<String> words = self.words;
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.remove(words.get(i));
				}
				for (int i = 0, size = words.size(); i < size && failIfInterrupted(); i++) {
					self.add(words.get(i));
				}
			}
		}, //
		;

		public <T> void init(AbstractWordSetBenchmark<T> self) throws InterruptedException {
			self.set = self.makeSet();
		}

		abstract public <T> void run(AbstractWordSetBenchmark<T> self) throws InterruptedException;

		private static boolean failIfInterrupted() throws InterruptedException {
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			return true;
		}
	}
}