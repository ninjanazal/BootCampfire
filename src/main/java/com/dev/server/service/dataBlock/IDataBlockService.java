package com.dev.server.service.dataBlock;

import java.io.IOException;

import java.util.List;

import com.dev.server.constants.data.DataType;
import com.dev.server.entity.DataBlock;
import com.dev.server.entity.User;
import com.fasterxml.jackson.databind.JsonNode;

public interface IDataBlockService {
	/**
	 * Creates default data blocks for a user.
	 * If there are existing data blocks associated with the user, they are deleted
	 * first.
	 * Default data blocks are created for all data types defined in the DataType
	 * enum.
	 * 
	 * @param user The user for whom default data blocks are to be created.
	 */
	public void createDefaultBlocksToUser(User user);

	/**
	 * Retrieves data blocks associated with a user.
	 * 
	 * @param user The user whose data blocks are to be retrieved.
	 * @return A list of data blocks associated with the user.
	 */
	public List<DataBlock> getDataByUser(User user);

	/**
	 * Updates the data associated with a user and data type.
	 * If the data block for the given user and data type doesn't exist, it creates
	 * a new one.
	 * If it exists, it merges the existing data with the new data.
	 * 
	 * @param jsonNode The JSON data to be updated.
	 * @param dataType The type of data to be updated.
	 * @param userId   The ID of the user associated with the data.
	 * @throws IOException If there is an I/O error while processing the data.
	 */
	public void UpdateData(JsonNode jsonNode, DataType dataType, String userId) throws IOException;

	/**
	 * Resets the data associated with a user and data type.
	 * If the data block for the given user and data type doesn't exist, it creates
	 * a new one with an empty JSON object.
	 * If it exists, it resets the data by setting it to an empty JSON object.
	 * 
	 * @param userId   The ID of the user associated with the data.
	 * @param dataType The type of data to be reset.
	 */
	public void resetData(String userId, DataType dataType);

	/**
	 * Retrieves the data block associated with a user and data type.
	 * If the data block for the given user and data type doesn't exist, it creates
	 * a new one with default values.
	 * 
	 * @param userId   The ID of the user associated with the data block.
	 * @param dataType The type of data for the data block.
	 * @return The data block associated with the user and data type.
	 */
	public DataBlock getData(String userId, DataType dataType);
}
