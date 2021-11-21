package com.beans;

public class AssetData {
	
	private int asset_id;
	private String s_no;
	private String barcode;
	private String country;
	private String city;
	private String site;
	private int asset_type;
	private String tag_name;
	private String fa_code;
	
	private int emp_id;
	private String first_name;
	private String last_name;
	private String email;
	private String mobile;
	private String department;
	
	private int tech_info_id;
	private String make;
	private String model;
	private String serial_no;
	private String mac_address;
	private String processor;
	private String os;
	private String hard_disk;
	private String memory;
	private String procurred_date;
	private String warranty_exp_date;
	private String amc;
	private String remarks;
	
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public int getAsset_type() {
		return asset_type;
	}
	public void setAsset_type(int asset_type) {
		this.asset_type = asset_type;
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
	
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public int getTech_info_id() {
		return tech_info_id;
	}
	public void setTech_info_id(int tech_info_id) {
		this.tech_info_id = tech_info_id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getHard_disk() {
		return hard_disk;
	}
	public void setHard_disk(String hard_disk) {
		this.hard_disk = hard_disk;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getProcurred_date() {
		return procurred_date;
	}
	public void setProcurred_date(String procurred_date) {
		this.procurred_date = procurred_date;
	}
	public String getWarranty_exp_date() {
		return warranty_exp_date;
	}
	public void setWarranty_exp_date(String warranty_exp_date) {
		this.warranty_exp_date = warranty_exp_date;
	}
	public String getAmc() {
		return amc;
	}
	public void setAmc(String amc) {
		this.amc = amc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public String toString() {
		return "AssetData [asset_id=" + asset_id + ", s_no=" + s_no + ", barcode=" + barcode + ", country=" + country
				+ ", city=" + city + ", site=" + site + ", asset_type=" + asset_type + ", tag_name=" + tag_name
				+ ", fa_code=" + fa_code + ", emp_id=" + emp_id + ", first_name=" + first_name + ", last_name="
				+ last_name + ", email=" + email + ", mobile=" + mobile + ", department=" + department
				+ ", tech_info_id=" + tech_info_id + ", make=" + make + ", model=" + model + ", serial_no=" + serial_no
				+ ", mac_address=" + mac_address + ", processor=" + processor + ", os=" + os + ", hard_disk="
				+ hard_disk + ", memory=" + memory + ", procurred_date=" + procurred_date + ", warranty_exp_date="
				+ warranty_exp_date + ", amc=" + amc + ", remarks=" + remarks + "]";
	}

}
