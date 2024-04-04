package com.basedeveloper.jellylinkserver.security;


public interface SecurityServiceInterface {
	public String EncodeData(String data);
	public boolean ValidateData(String rawData, String hashedData);
}
