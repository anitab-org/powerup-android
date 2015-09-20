package com.example.powerupapp.datamodel;

public class Question {
	
	private Integer qId;
	private String qDes;
	private Integer scenarioId;
	
	public Integer getQId() {
		return qId;
	}
	public void setQId(Integer qId) {
		this.qId = qId;
	}
	public String getQDes() {
		return qDes;
	}
	public void setQDes(String qDes) {
		this.qDes = qDes;
	}
	public Integer getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(Integer scenarioId) {
		this.scenarioId = scenarioId;
	}
}
