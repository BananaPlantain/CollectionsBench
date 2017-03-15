package de.heidelberg.pvs.container_bench.online_adapter.sets;

import java.util.Set;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKSetTest;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.facade.SetAllocationContextFacade;

public class OnlineAdapter_Integer_HashSet_Test extends AbstractJDKSetTest<Integer> {

	SetAllocationContext<Integer> contextEmptySet = new SetAllocationContextFacade<>(CollectionTypeEnum.HASH);
	
	SetAllocationContext<Integer> contextCopySet = new SetAllocationContextFacade<>(CollectionTypeEnum.HASH);
	
	@Override
	protected Set<Integer> getNewSet() {
		return contextEmptySet.createSet();
	}

	@Override
	protected Set<Integer> copySet(Set<Integer> fullSet2) {
		return contextCopySet.createSet(fullSet2);
	}

}