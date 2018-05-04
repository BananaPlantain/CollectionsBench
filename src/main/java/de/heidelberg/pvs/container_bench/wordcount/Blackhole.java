package de.heidelberg.pvs.container_bench.wordcount;

import org.openjdk.jmh.annotations.Param;

import de.heidelberg.pvs.container_bench.factories.BlackholeFact;

public class Blackhole extends AbstractWordcountBenchmark<Void> {
	@Param
	public BlackholeFact impl;

	@Override
	protected Void makeMap() {
		return null;
	}

	@Override
	protected void count(Void map, String object) {
		bh.consume(object);
	}
}
