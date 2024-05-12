package com.dev.server.service.dataBlock;

import java.util.List;

import com.dev.server.constants.data.DataType;
import com.dev.server.entity.DataBlock;
import com.dev.server.entity.User;
import com.fasterxml.jackson.databind.JsonNode;

public interface IDataBlockService {
	public void createDefaultBlocksToUser(User user);

	public List<DataBlock> getDataByUser(User user);

	public void UpdateData(JsonNode jsonNode, DataType dataType, User user);
}
