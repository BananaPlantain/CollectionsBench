package de.heidelberg.pvs.container_bench.online_adapter.maps;

import java.util.Map;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKMapTest;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.facade.MapAllocationContextFacade;

public class OnlineAdapter_IntegerInteger_HashMap_Test extends AbstractJDKMapTest<Integer, Integer> {

	MapAllocationContext<Integer, Integer> contextEmptyList = new MapAllocationContextFacade<>(CollectionTypeEnum.HASH);
	MapAllocationContext<Integer, Integer> contextCopyList = new MapAllocationContextFacade<>(CollectionTypeEnum.HASH);
	
	@Override
	protected Map<Integer, Integer> getNewMap() {
		return contextEmptyList.createMap();
	}
	
	@Override
	protected Map<Integer, Integer> copyMap(Map<Integer, Integer> fullMap2) {
		return contextCopyList.createMap(fullMap2);
	}
	
	

}
