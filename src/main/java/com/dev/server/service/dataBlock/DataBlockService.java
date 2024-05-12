package com.dev.server.service.dataBlock;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.server.constants.data.DataType;
import com.dev.server.entity.DataBlock;
import com.dev.server.entity.User;
import com.dev.server.repository.IDataBlockRepository;
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
			tBlock.setNodeData(mapper.createObjectNode());

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

	@Override
	public void UpdateData(JsonNode jsonNode, DataType dataType, User user) {
		DataBlock block = dataRepository.findByOwnerUserIdAndDataType(user.getId().toHexString(), dataType);
		if(block)
	}
}
