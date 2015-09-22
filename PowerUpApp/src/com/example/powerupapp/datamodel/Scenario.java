package com.example.powerupapp.datamodel;

public class Scenario {

	private int id;
	private String scenarioName;
	private String timestamp;
	private String asker;
	private int avatar;
	private int firstQId;
	private int completed;
	private int nextScenarioId;
	private int replayed;
	
	public int getId() {
		return id;
	}
	public void setId(int iD) {
		id = iD;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAsker() {
		return asker;
	}
	public void setAsker(String asker) {
		this.asker = asker;
	}
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public int getFirstQId() {
		return firstQId;
	}
	public void setFirstQId(int firstQId) {
		this.firstQId = firstQId;
	}
	public int getNextScenarioId() {
		return nextScenarioId;
	}
	public void setNextScenarioId(int nextScenarioId) {
		this.nextScenarioId = nextScenarioId;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public int getReplayed() {
		return replayed;
	}
	public void setReplayed(int replayed) {
		this.replayed = replayed;
	}
}
