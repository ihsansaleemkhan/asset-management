package com.beans;

import java.sql.Date;
import java.util.List;

public class AssetInfo {
	
	private int asset_id;
	private String s_no;
	private String country;
	private String city;
	private String site;
	private AssetType assetTypesObj;
	public String asset_type;
	private String tag_name;
	private String fa_code;
	private EmpInfo ownerInfoObj;
	private TechInfo techInfoObj;
	private Date created_at;

	public int getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(int asset_id) {
		this.asset_id = asset_id;
	}
	public String getS_no() {
		return s_no;
	}
	public void setS_no(String s_no) {
		this.s_no = s_no;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getFa_code() {
		return fa_code;
	}
	public void setFa_code(String fa_code) {
		this.fa_code = fa_code;
	}
	public AssetType getAssetTypesObj() {
		return assetTypesObj;
	}
	public void setAssetTypesObj(AssetType assetTypesObj) {
		this.assetTypesObj = assetTypesObj;
	}
	public EmpInfo getOwnerInfoObj() {
		return ownerInfoObj;
	}
	public void setOwnerInfoObj(EmpInfo ownerInfoObj) {
		this.ownerInfoObj = ownerInfoObj;
	}
	public TechInfo getTechInfoObj() {
		return techInfoObj;
	}
	public void setTechInfoObj(TechInfo techInfoObj) {
		this.techInfoObj = techInfoObj;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getAsset_type() {
		return asset_type;
	}
	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}
}
