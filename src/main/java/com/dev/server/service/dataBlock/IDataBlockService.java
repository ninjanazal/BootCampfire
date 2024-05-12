package com.dev.server.service.dataBlock;

import java.util.List;

import com.dev.server.entity.DataBlock;
import com.dev.server.entity.User;

public interface IDataBlockService {
	public void createDefaultBlocksToUser(User user);

	public List<DataBlock> getDataByUser(User user);
}
