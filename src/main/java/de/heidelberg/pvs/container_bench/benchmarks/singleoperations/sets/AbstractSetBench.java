package de.heidelberg.pvs.container_bench.benchmarks.singleoperations.sets;

import java.io.IOException;

import de.heidelberg.pvs.container_bench.benchmarks.singleoperations.AbstractSingleOperationsBench;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;

public abstract class AbstractSetBench<T> extends AbstractSingleOperationsBench {

	/**
	 * Implementation of our Randomness
	 */
	protected ElementGenerator<T> generator;

	@SuppressWarnings("unchecked")
	@Override
	public void generatorSetup() throws IOException {
		generator = (ElementGenerator<T>) GeneratorFactory.buildRandomGenerator(payloadType);
		generator.init(size, seed);
	}

	/**
	 * Benchmark GetAll
	 * 
	 * This benchmark measure the time spent in traversing the set. <br>
	 * <code> 
	 * <pre>
	 * for(;;) 
	 *     set.get(i) 
	 * </pre> 
	 * </code>
	 */
	abstract public void iterate();

	/**
	 * Benchmark Contains
	 * 
	 * This benchmark measure the time spent executing a contain to a
	 * <b>random</b> element in the set <br>
	 * <br>
	 * <code>
	 * randomElement = random()
	 * set.contains(randomElement)
	 * </code>
	 * 
	 */
	abstract public void containsElement();

	/**
	 * Benchmark AddAll
	 * 
	 * This benchmark measure the time spent by adding <b>random</b> elements
	 * into the set, until it reaches its specified size <code> 
	 * <pre>
	 * for(;;) 
	 *     set.add(random())
	 * </pre>    
	 * </code>
	 * 
	 */
	abstract public void populate();

	/**
	 * Benchmark AddElement
	 * 
	 * This benchmark measure the time spent by one <b>random</b> element into a
	 * full set. The element has 50% of chance of collision. <code> 
	 * <pre>
	 *     set.add(random(2 * range))
	 *   </pre>  
	 * </code>
	 * 
	 */
	abstract public void addElement();

	/**
	 * Benchmark Copy
	 * 
	 * This benchmark measure the time spent by copying the entire set to a new
	 * instance <code>
	 * <pre>
	 * newset = copy(oldSet)
	 * </pre>
	 * </code>
	 * 
	 */
	abstract public void copy();

	abstract public void removeElement();


}