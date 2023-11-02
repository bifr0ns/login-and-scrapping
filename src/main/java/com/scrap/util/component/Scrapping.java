package com.scrap.util.component;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class Scrapping {

  public HashMap<String, String> getLinks(String url) throws IOException {
    HashMap<String, String> urls = new HashMap<>();
    log.info("Fetching {}...", url);

    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a[href]");

    urls.put("title", doc.title());
    for (Element link : links) {
      log.info(" * a: <{}>  ({})", link.attr("abs:href"), trim(link.text(), 35));
      urls.put(link.attr("abs:href"), trim(link.text(), 35));
    }

    return urls;
  }

  private static String trim(String s, int width) {
    if (s.length() > width)
      return s.substring(0, width - 1) + ".";
    else
      return s;
  }
}
