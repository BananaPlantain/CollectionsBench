package de.heidelberg.pvs.container_bench.benchmarks.singleoperations.sets;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import com.carrotsearch.hppc.ObjectSet;
import com.carrotsearch.hppc.procedures.ObjectProcedure;

import de.heidelberg.pvs.container_bench.factories.HPPCSetFact;

/**
 * Abstract class for every test with HPPC Sets implementation
 * 
 * @author Diego
 */
public class HPPCSetBench extends AbstractSetBench<Object> {
	private ObjectSet<Object> fullSet;
	private Object[] values;

	@Param
	public HPPCSetFact impl;

	@Param
	public HPPCSetWorkload workload;

	protected ObjectSet<Object> getNewSet() {
		return impl.maker.get();
	}

	protected ObjectSet<Object> copySet(ObjectSet<Object> original) {
		return impl.copyMaker.apply(original);
	}

	public void testSetup() {
		fullSet = this.getNewSet();
		values = generator.generateArray(size);
		for (int i = 0; i < size && failIfInterrupted(); i++) {
			fullSet.add(values[i]);
		}
	}

	@Benchmark
	public void bench() {
		workload.run(this);
		blackhole.consume(fullSet);
	}

	public enum HPPCSetWorkload {
		POPULATE {
			@Override
			void run(HPPCSetBench self) {
				ObjectSet<Object> newSet = self.getNewSet();
				for (int i = 0; i < self.size && failIfInterrupted(); i++) {
					newSet.add(self.values[i]);
				}
				self.blackhole.consume(newSet);
			}
		}, //

		ITERATE {
			@Override
			void run(HPPCSetBench self) {
				for (Object element : self.fullSet) {
					failIfInterrupted();
					self.blackhole.consume(element);
				}
			}
		}, //

		FOREACH {
			@Override
			void run(HPPCSetBench self) {
				self.fullSet.forEach(new ObjectProcedure<Object>() {
					@Override
					public void apply(Object value) {
						failIfInterrupted();
						self.blackhole.consume(value);
					}
				});
			}
		}, //

		COPY {
			@Override
			void run(HPPCSetBench self) {
				ObjectSet<Object> newSet = self.copySet(self.fullSet);
				self.blackhole.consume(newSet);
			}
		},

		CONTAINS {
			@Override
			void run(HPPCSetBench self) {
				int index = self.generator.generateIndex(self.size);
				self.blackhole.consume(self.fullSet.contains(self.values[index]));
			}
		};

		abstract void run(HPPCSetBench self);
	}
}