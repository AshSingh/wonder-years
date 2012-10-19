package com.cs410.getfit.shared;


public interface Challenge {
	
	public long getGuid();
	public String getTitle();
	public void setTitle(String title);
	public boolean isPrivate();
	public void setIsPrivate(boolean isPrivate);
	public String getLocation();
	public void setLocation(String location);
	public long getStartDate();
	public void setStartDate(long startDate);
	public long getEndDate();
	public void setEndDate(long endDate);
}
