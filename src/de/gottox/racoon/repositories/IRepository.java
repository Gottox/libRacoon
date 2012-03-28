package de.gottox.racoon.repositories;

import de.gottox.racoon.Query;
import de.gottox.racoon.sources.ISource;

public interface IRepository {
	String getName();
	ISource[] findClasses(Query query);
}
