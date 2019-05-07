package com.itxiaox.xview.addressDialog;


public class ChooseCityInfo {
	private String	name;			// 显示的数据
	private String	sortLetters;	// 显示数据拼音的首字母
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	@Override
	public String toString() {
		return "ChooseCityInfo [name=" + name + ", sortLetters=" + sortLetters
				+ "]";
	}
	
}
