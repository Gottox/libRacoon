package de.gottox.racoon.repositories;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.gottox.racoon.Query;
import de.gottox.racoon.sources.ISource;
import de.gottox.racoon.sources.PlainSource;

public class GrepCodeRepository implements IRepository {
	private static String getUrl(String query) {
		try {
			return "http://grepcode.com/search?query="
					+ URLEncoder.encode(query, "UTF-8") + "&n=";
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should never happen", e);
		}
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public ISource[] findClasses(Query query) {
		LinkedList<ISource> sources = new LinkedList<ISource>();
		try {
			String queryStr = query.getSearchQuery();
			Document document = Jsoup.connect(getUrl(queryStr)).get();
			Elements resultItems = document.select(".search-result-item");
			for(Element item : resultItems) {
				if(item.select(".entity-name").first().text().equals(queryStr))
					foundClass(item, sources);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sources.toArray(new ISource[sources.size()]);
	}

	private void foundClass(Element item, LinkedList<ISource> sources) {
		Elements versions = item.select("a.container-name");
		for(Element version : versions) {
			if(version.hasAttr("href") == false || version.attr("href").equals("#"))
				continue;
			String href = version.attr("abs:href");
			href = href.replaceAll("#.*", "") + "?v=source";
			sources.add(new PlainSource(this, href));
		}
	}

}
