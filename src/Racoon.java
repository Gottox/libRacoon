import org.jsoup.Jsoup;

import de.gottox.racoon.Query;
import de.gottox.racoon.sources.ISource;


public class Racoon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Query query = new Query(String.class);
		for(ISource source : query.getSources()) {
			System.out.println("Loading source for " + source.getRepository().getName());
			source.loadCode();
			System.out.println("--------------------\n"+source.getCode());
			
		}
	}
}
