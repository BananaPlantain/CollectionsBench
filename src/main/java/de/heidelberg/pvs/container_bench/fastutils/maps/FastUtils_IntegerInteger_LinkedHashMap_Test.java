package de.heidelberg.pvs.container_bench.fastutils.maps;

import java.util.Map;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKMapBench;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

public class FastUtils_IntegerInteger_LinkedHashMap_Test extends AbstractJDKMapBench<Integer, Integer> {

	@Override
	protected Map<Integer, Integer> getNewMap() {
		return new Object2ObjectLinkedOpenHashMap<>();
	}

	@Override
	protected Map<Integer, Integer> copyMap(Map<Integer, Integer> fullMap2) {
		return new Object2ObjectLinkedOpenHashMap<>(fullMap2);
	}

}
