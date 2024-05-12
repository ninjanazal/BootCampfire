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
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataBlockService implements IDataBlockService{
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private IDataBlockRepository blockRepository;


	public void createDefaultBlocksToUser(User user) {
		List<DataBlock> blocks = getDataByUser(user);
		if(!blocks.isEmpty()){
			blockRepository.deleteAll(blocks);
		}

		for (DataType type : DataType.values()) {
			DataBlock tBlock = new DataBlock();
			tBlock.setType(type);
			tBlock.setNodeData(mapper.createObjectNode());

			blocks.add(tBlock);
		}

		blockRepository.saveAll(blocks);
	}


	@Override
	public List<DataBlock> getDataByUser(User user) {
		Optional<List<DataBlock>> oBlocks = blockRepository.findByOwnerUserId(user.getId().toHexString());
		if(oBlocks.isPresent()){
			return oBlocks.get();
		}
		return new ArrayList<DataBlock>();
	}
}
