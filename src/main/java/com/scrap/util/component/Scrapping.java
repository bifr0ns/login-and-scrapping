package com.scrap.util.component;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Scrapping {

  public Map<String, String> getLinks(String url) throws IOException {
    HashMap<String, String> urls = new HashMap<>();

    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a[href]");

    urls.put("title", doc.title());
    for (Element link : links) {
      urls.put(link.attr("abs:href"), trim(link.text()));
    }

    return urls;
  }

  private static String trim(String s) {
    if (s.length() > 35)
      return s.substring(0, 35 - 1) + ".";
    else
      return s;
  }
}
