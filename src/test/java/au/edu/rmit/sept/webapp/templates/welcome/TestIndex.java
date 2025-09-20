package au.edu.rmit.sept.webapp.templates.welcome;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@SpringBootTest
@AutoConfigureMockMvc
class TestIndex {

  @Autowired MockMvc mvc;

  // Test home page renders and shows core content
  @Test
  void home_renders() throws Exception {
    MvcResult res = mvc.perform(get("/"))
          .andExpect(status().isOk())
          .andReturn();

    Document doc = Jsoup.parse(res.getResponse().getContentAsString());

    // Title (choose one of the assertions below)
    assertThat(doc.title()).isEqualTo("Home - EventHub");
    // assertThat(doc.title()).contains("EventHub");

    // H1
    assertThat(doc.selectFirst("h1.welcome-title").text())
          .isEqualTo("Welcome to EventHub");

    // Navbar links
    assertThat(doc.select("nav a[href='/']")).isNotEmpty();
    assertThat(doc.select("nav a[href='/events']")).isNotEmpty();
    assertThat(doc.select("nav a[href='/login']")).isNotEmpty();
    assertThat(doc.select("nav a[href='/signUp']")).isNotEmpty();

    // Carousel images
    assertThat(doc.select("#welcomeCarousel img.active")).hasSize(1);
    assertThat(doc.select("#welcomeCarousel img")).hasSize(3);
    assertThat(doc.select("#welcomeCarousel img[src^='/images/welcome/']")).isNotEmpty();

    // Buttons present
    assertThat(doc.select("#welcomeCarousel button.prev")).hasSize(1);
    assertThat(doc.select("#welcomeCarousel button.next")).hasSize(1);

    // Every carousel image has alt text
    doc.select("#welcomeCarousel img").forEach(img ->
    assertThat(img.hasAttr("alt")).as("img should have alt").isTrue()
      );

    // Footer present
    assertThat(doc.select("footer")).isNotEmpty();
  }

  // Test layout parent shows elements (navbar, footer)
  @Test
  void layout_decorates() throws Exception {
    // assert something the layout injects
    MvcResult res = mvc.perform(get("/")).andExpect(status().isOk()).andReturn();
    Document doc = Jsoup.parse(res.getResponse().getContentAsString());

    assertThat(doc.selectFirst("header, nav, footer")).isNotNull();
  }
}
