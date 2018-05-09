package de.heidelberg.pvs.container_bench.fastutils.sets;

import java.util.Set;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKSetBench;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class FastUtils_Integer_HashSet_Test extends AbstractJDKSetBench<Integer> {

	@Override
	protected Set<Integer> getNewSet() {
		return new ObjectOpenHashSet<>();
	}

	@Override
	protected Set<Integer> copySet(Set<Integer> fullSet2) {
		return new ObjectOpenHashSet<>(fullSet2);
	}

}
