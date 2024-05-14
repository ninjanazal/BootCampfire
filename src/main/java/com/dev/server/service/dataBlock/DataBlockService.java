package com.dev.server.service.dataBlock;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.server.constants.data.DataType;
import com.dev.server.entity.DataBlock;
import com.dev.server.entity.User;
import com.dev.server.repository.IDataBlockRepository;
import com.dev.server.tools.JsonOperations;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataBlockService implements IDataBlockService {
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private IDataBlockRepository dataRepository;

	@Override
	public void createDefaultBlocksToUser(User user) {
		List<DataBlock> blocks = getDataByUser(user);
		if (!blocks.isEmpty()) {
			dataRepository.deleteAll(blocks);
		}

		for (DataType type : DataType.values()) {
			DataBlock tBlock = new DataBlock();
			tBlock.setType(type);
			tBlock.setNodeData(mapper.createObjectNode().toString());

			blocks.add(tBlock);
		}

		dataRepository.saveAll(blocks);
	}

	@Override
	public List<DataBlock> getDataByUser(User user) {
		Optional<List<DataBlock>> oBlocks = dataRepository.findByOwnerUserId(user.getId().toHexString());
		if (oBlocks.isPresent()) {
			return oBlocks.get();
		}
		return new ArrayList<DataBlock>();
	}
/**
 * Updates the data associated with a user and data type.
 * If the data block for the given user and data type doesn't exist, it creates a new one.
 * If it exists, it merges the existing data with the new data.
 * 
 * @param jsonNode The JSON data to be updated.
 * @param dataType The type of data to be updated.
 * @param userId   The ID of the user associated with the data.
 * @throws IOException If there is an I/O error while processing the data.
 */
	@Override
	public void UpdateData(JsonNode jsonNode, DataType dataType, String userId) throws IOException {
		Optional<DataBlock> oBlock = dataRepository.findByOwnerUserIdAndType(userId, dataType);
		DataBlock dBlock;
		if (!oBlock.isPresent()) {
			dBlock = new DataBlock();

			dBlock.setType(dataType);
			dBlock.setOwnerUserId(userId);
			dBlock.setNodeData(jsonNode.toString());

		} else {
			dBlock = oBlock.get();
			JsonNode blockData = mapper.readTree(dBlock.getNodeData());
			dBlock.setNodeData(JsonOperations.MergeJsons(blockData, jsonNode).toString());
		}

		dataRepository.save(dBlock);
	}

	/**
 * Resets the data associated with a user and data type.
 * If the data block for the given user and data type doesn't exist, it creates a new one with an empty JSON object.
 * If it exists, it resets the data by setting it to an empty JSON object.
 * 
 * @param userId   The ID of the user associated with the data.
 * @param dataType The type of data to be reset.
 */
	@Override
	public void resetData(String userId, DataType dataType) {
		Optional<DataBlock> oBlock = dataRepository.findByOwnerUserIdAndType(userId, dataType);
		DataBlock dBlock;
		if (!oBlock.isPresent()) {
			dBlock = new DataBlock();

			dBlock.setType(dataType);
			dBlock.setOwnerUserId(userId);
			dBlock.setNodeData(mapper.createObjectNode().toString());

		} else {
			dBlock = oBlock.get();
			dBlock.setNodeData(mapper.createObjectNode().toString());
		}

		dataRepository.save(dBlock);
	}
}
