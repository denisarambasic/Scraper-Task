package com.denis.vjezba35.controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class TestController {

    @Autowired private WebDriver driver;

    @GetMapping("/eurojackpot")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getEurojackpotNumbers() throws IOException {
        Document doc = Jsoup.connect("https://www.eurojackpot.org/hr/rezultati/").get();
        Elements lis = doc.selectXpath("//*[@id=\"resultsPage\"]/main/section[2]/div/div/ul[1]");
        lis.addAll(doc.selectXpath("//*[@id=\"resultsPage\"]/main/section[2]/div/div/ul[2]"));

        return getNumbersFromString(lis.text());
    }


    @GetMapping("/germanyLotto")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getGermanyLottoNumbers() throws IOException {
        Document doc = Jsoup.connect("https://www.lotto.net/german-lotto/results").get();
        Elements lis = doc.selectXpath("//*[@id=\"content\"]/div[1]/div[2]/ul");
        return getNumbersFromString(lis.text());

    }

    @GetMapping("/croLotto7")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getCroLotto7Numbers() throws IOException {

        driver.get("https://www.lutrija.hr/hl/rezultati/loto-7");

        WebElement element = driver.findElement(By.xpath("//div[@class='results-body']/div[1]/div[1]/div[3]/div[1]"));

        return getNumbersFromString(element.getText());
    }

    @GetMapping("/floridaLotto")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getFloridaLottoNumbers() throws IOException {
        Document doc = Jsoup.connect("https://flalottery.com/lotto").get();
        Elements elements = doc.selectXpath("//*[@id='gameContentLeft']/div[1]/div[2]");
        return getNumbersFromString(elements.text());
    }

    @GetMapping("/italyWinForLive")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getItalyWinForLiveNumbers() throws IOException {
        driver.get("https://lotostats.ro/rezultate-win-for-life-10-20");

        List<WebElement> numberElements = driver.findElements(By.xpath("//div[contains(@class,'blaresp')]"));

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            numbers.add(Integer.parseInt(numberElements.get(i).getText()));
        }

        return numbers;
    }

    @GetMapping("/lottoPlus")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getLottoPlusNumbers() throws IOException {
        driver.get("https://www.nationallottery.co.za/results/lotto-plus-1-results");
        WebElement element = driver.findElement(By.xpath("//div[@class='box']/div[1]/div[1]/div/div[2]"));
        return getNumbersFromString(element.getText());
    }

    public List<Integer> getNumbersFromString(String str) {
        String regex ="(\\d+)";
        Matcher matcher = Pattern.compile( regex ).matcher( str);
        List<Integer> brojevi = new ArrayList<>();
        while (matcher.find())
        {
            brojevi.add(Integer.parseInt(matcher.group()));
        }
        return brojevi;
    }
}
