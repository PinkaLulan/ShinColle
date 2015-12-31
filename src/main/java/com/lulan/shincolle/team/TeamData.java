package com.lulan.shincolle.team;

import java.util.List;

import com.lulan.shincolle.utility.LogHelper;

public class TeamData {
	
	protected int teamID;					//team id
	protected int teamLeaderUID;			//leader PlayerUID
	protected String teamName;				//team name
	protected List<Integer> teamMemberUID;	//member PlayerUID list
	protected List<Integer> teamAllyID;		//team ally id list
	
	
	public TeamData() {	
	}
	
	/** getter */
	public String getTeamName() {
		return this.teamName;
	}
	
	public int getTeamLeaderUID() {
		return this.teamLeaderUID;
	}
	
	public int getTeamID() {
		return this.teamID;
	}
	
	public List<Integer> getTeamMemberUID() {
		return this.teamMemberUID;
	}
	
	public List<Integer> getTeamAlly() {
		return this.teamAllyID;
	}
	
	/** setter */
	public void setTeamName(String par1) {
		this.teamName = par1;
	}
	
	public void setTeamLeaderUID(int par1) {
		this.teamLeaderUID = par1;
	}
	
	public void setTeamID(int par1) {
		this.teamID = par1;
	}
	
	public void setTeamMemberUID(List<Integer> par1) {
		this.teamMemberUID = par1;
	}

	public void setTeamAlly(List<Integer> par1) {
		this.teamAllyID = par1;
	}
	
	public void addTeamAlly(int par1) {  //add ally or remove (if existed)
		if(par1 > 0) {
			if(this.teamAllyID != null) {
				if(this.teamAllyID.contains(par1)) {  //ally existed, remove ally
					this.teamAllyID.remove(par1);
					LogHelper.info("DEBUG : team data: remove ally: team "+this.teamName+" ally "+par1);
				}
				else {
					this.teamAllyID.add(par1);
					LogHelper.info("DEBUG : team data: add ally: team "+this.teamName+" ally "+par1);
				}
			}
		}
	}
	
	//check team is in ally list
	public boolean isTeamAlly(int par1) {
		//id 0 = always friendly
		if(par1 == 0) return true;
		
		if(par1 > 0) {
			for(int geti : this.teamAllyID) {
				if(par1 == geti) return true;
			}
		}
		
		return false;
	}
	
	

}
