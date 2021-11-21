package com.serviceInterface;

import java.util.List;
import java.util.Map;

import com.beans.AssetInfo;
import com.beans.UserDevice;

public interface DevicesInterface {
	List<UserDevice> getUserDevice(Map<String,String> params);
	AssetInfo getAssetInformation(String serialNumber);
	String getAssignedUserName(int userAssetId);
	boolean setCheckOut(int userAssetId);
}
