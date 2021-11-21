package com.serviceImp;

import java.util.List;
import java.util.Map;

import com.beans.AssetInfo;
import com.beans.UserDevice;
import com.dao.DeviceDAO;
import com.serviceInterface.DevicesInterface;

public class DevicesImp implements DevicesInterface {

	@Override
	public List<UserDevice> getUserDevice(Map<String,String> params) {
		return new DeviceDAO().getUserDevice(params);
	}

	@Override
	public AssetInfo getAssetInformation(String serialNumber) {
		return new DeviceDAO().getAssetInformation(serialNumber);
	}

	@Override
	public String getAssignedUserName(int userAssetId) {
		return new DeviceDAO().getAssignedUserName(userAssetId);
	}

	@Override
	public boolean setCheckOut(int userAssetId) {
		return new DeviceDAO().setCheckOut(userAssetId);
	}
	
}
