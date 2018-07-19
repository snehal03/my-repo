package com.generate.letter.generateLetter.model;

import javax.validation.constraints.NotNull;

public class ExperienceLetter {
	@NotNull(message = "name cannot be null")
	private String name;
	@NotNull(message = "Joining Date cannot be null")
	private String joiningData;
	@NotNull(message = "Release Date cannot be null")
	private String releaseDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJoiningData() {
		return joiningData;
	}

	public void setJoiningData(String joiningData) {
		this.joiningData = joiningData;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

}
