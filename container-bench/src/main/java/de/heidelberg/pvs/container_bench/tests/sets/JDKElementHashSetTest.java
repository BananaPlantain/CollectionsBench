package de.heidelberg.pvs.container_bench.tests.sets;

import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.container_bench.abstracts.AbstractJDKSetTest;
import de.heidelberg.pvs.container_bench.element.Element;

public class JDKElementHashSetTest extends AbstractJDKSetTest<Element> {

	@Override
	protected Element[] generateUniqueRandomArray(int size) {
		return randomGenerator.generateElements(size);
	}

	@Override
	protected Integer generateRandomIndex(int size) {
		return randomGenerator.generateIntegerInRange(size);
	}

	@Override
	protected Set<Element> getNewSet(int size) {
		return new HashSet<Element>();
	}

	@Override
	protected Set<Element> copySet(Set<Element> fullSet2) {
		return new HashSet<Element>(fullSet2);
	}


}
