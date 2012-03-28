package de.gottox.racoon;

import java.util.Arrays;
import java.util.LinkedList;

import de.gottox.racoon.repositories.GrepCodeRepository;
import de.gottox.racoon.repositories.IRepository;
import de.gottox.racoon.sources.ISource;

public class Query {
	final static private IRepository[] DEFAULT_REPOSITORIES = new IRepository[] {
		new GrepCodeRepository()
	};
	private Class<?> clazz;
	private IRepository[] repositories;

	public Query(Class<?> clazz, IRepository... repositories) {
		this.clazz = clazz;
		this.repositories = repositories;
	}
	
	public Query(Class<?> clazz) {
		this(clazz, DEFAULT_REPOSITORIES);
	}
	
	public ISource[] getSources() {
		LinkedList<ISource> sources = new LinkedList<ISource>();
		for(IRepository repository : repositories) {
			sources.addAll(Arrays.asList(repository.findClasses(this)));
		}
		return sources.toArray(new ISource[sources.size()]);
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
	public String getSearchQuery() {
		return clazz.getName();
	}
}
