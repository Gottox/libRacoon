package de.gottox.racoon.sources;

import de.gottox.racoon.repositories.IRepository;

public interface ISource {
	IRepository getRepository();
	String getCode();
	void loadCode();
}
