package de.heidelberg.pvs.container_bench.wordlist.sets;

import scala.collection.mutable.Set;

import org.openjdk.jmh.annotations.Param;

import de.heidelberg.pvs.container_bench.factories.ScalaSetFact;

public class ScalaGetPut extends AbstractWordSetBenchmark<Set<String>> {
	@Param
	public ScalaSetFact impl;

	@Override
	protected Set<String> makeSet() {
		return impl.maker.get();
	}

	@Override
	protected void add(String object) {
		set.add(object);
	}

	@Override
	protected boolean contains(String object) {
		return ((scala.collection.Set<String>) set).contains(object);
	}

	@Override
	protected void remove(String object) {
		set.remove(object);
	}
}
