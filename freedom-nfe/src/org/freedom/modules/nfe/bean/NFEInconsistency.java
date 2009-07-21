package org.freedom.modules.nfe.bean;

public class NFEInconsistency {
	private String description;
	private String correctiveAction;
	public NFEInconsistency(String description, String correctiveAction) {
		this.description = description;
		this.correctiveAction = correctiveAction;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}
	public String getCorrectiveAction() {
		return correctiveAction;
	}
}
