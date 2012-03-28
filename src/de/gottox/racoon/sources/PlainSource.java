package de.gottox.racoon.sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.gottox.racoon.repositories.IRepository;

public class PlainSource implements ISource {
	private IRepository repository;
	private String url;
	private String code = null;

	public PlainSource(IRepository repository, String url) {
		this.repository = repository;
		this.url = url;
	}

	@Override
	public IRepository getRepository() {
		return repository;
	}

	@Override
	public String getCode() {
		if (code == null)
			throw new RuntimeException("Must load before code is available");
		return code;
	}

	@Override
	public void loadCode() {
		try {
			StringWriter builder = new StringWriter();
			URLConnection connection = new URL(url).openConnection();
			InputStreamReader input = new InputStreamReader(connection.getInputStream());
			int bsize;
			char[] buffer = new char[1024];
			while((bsize = input.read(buffer)) > 0) {
				builder.write(buffer, 0, bsize);
			}
			synchronized (this) {
				this.code = builder.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
