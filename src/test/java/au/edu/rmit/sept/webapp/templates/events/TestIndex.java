package au.edu.rmit.sept.webapp.templates.events;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

// events page renders title and search bar
public class TestIndex {

    @Test
    void events_renders() throws Exception {
        // Load raw HTML file (skip Thymeleaf processing)
        String html = Files.readString(
                Paths.get("src/main/resources/templates/events/index.html")
        );

        Document doc = Jsoup.parse(html);

        // simple checks
        assertThat(doc.title()).isEqualTo("EventHub - Events");
        assertThat(doc.select("form[action='/events']")).isNotEmpty();
        assertThat(doc.select("input#search")).isNotEmpty();
    }
}
