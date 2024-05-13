package com.dev.server.tools;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonOperations {
	public static JsonNode MergeJsons(JsonNode nodeA, JsonNode nodeB)
			throws IOException {
		if (nodeA == null || nodeA.isMissingNode()) {
			return nodeB;
		}
		if (nodeB == null || nodeB.isMissingNode()) {
			return nodeA;
		}

		if (nodeA.isObject() && nodeB.isObject()) {
			ObjectNode mergedNode = ((ObjectNode) nodeA);
			nodeB.fields().forEachRemaining(entry -> {
				String fieldName = entry.getKey();
				JsonNode valueB = entry.getValue();
				JsonNode valueA = mergedNode.get(fieldName);
				if (valueA != null && valueA.isObject() && valueB.isObject()) {
					try {
						mergedNode.set(fieldName, MergeJsons(valueA, valueB));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					mergedNode.set(fieldName, valueB);
				}
			});
			return mergedNode;
		} else {
			return nodeB;
		}
	}
}
