package de.heidelberg.pvs.container_bench.gscollections.lists;

import java.util.List;

import com.gs.collections.impl.list.mutable.FastList;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKListTest;

public class GSCollections_Long_FastList_Test extends AbstractJDKListTest<Long>{

	@Override
	protected List<Long> getNewList() {
		return new FastList<Long>();
	}

	@Override
	protected List<Long> copyList(List<Long> fullList2) {
		return new FastList<Long>(fullList2);
	}

}
