package de.heidelberg.pvs.container_bench.fastutils.lists;

import java.util.List;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKListTest;
import de.heidelberg.pvs.container_bench.random.LongRandomGenerator;
import de.heidelberg.pvs.container_bench.random.RandomGenerator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class FastUtils_Long_ArrayList_Test extends AbstractJDKListTest<Long> {

	@Override
	protected List<Long> getNewList(int size) {
		return new ObjectArrayList<>();
	}

	@Override
	protected List<Long> copyList(List<Long> fullList2) {
		return new ObjectArrayList<>(fullList2);
	}

	@Override
	protected RandomGenerator<Long> instantiateRandomGenerator() {
		return new LongRandomGenerator();
	}

}
