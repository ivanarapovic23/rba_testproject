package com.rba.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TecajniKalkulator {
    private WebDriver chromeDriver;

    @BeforeClass
    public void Initialization() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        chromeDriver = new ChromeDriver(options);
        chromeDriver.get("https://www.rba.hr/alati/tecajni-kalkulator");
    }

    @Test
    public void eurToGbpTest() {
        double[] amount_list = { 100.00 , 200.00, 300.00 };
        for (double amount : amount_list) {
            setRate("0");
            performConversion("EUR", "GBP", amount);
            }
        }

    @Test
    public void usdToEurTest() {
        double[] amount_list = { 250.00, 450.00, 600.00 };
        for (double amount : amount_list) {
            setRate("2");
            performConversion("USD", "EUR", amount);

        }
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    private void performConversion(String startingCurrency, String convertedCurrency, double amount) {
        WebElement starting_currency = chromeDriver.findElement(By.name("valuta1"));
        starting_currency.sendKeys(startingCurrency);

        WebElement converted_currency = chromeDriver.findElement(By.name("valuta2"));
        converted_currency.sendKeys(convertedCurrency);

        WebElement transactionAmount = chromeDriver.findElement(By.name("suma1"));

        transactionAmount.clear();
        String amountString = String.valueOf(amount);
        transactionAmount.sendKeys(amountString);

        sleepFor(2000);

        WebElement exchangeRate = chromeDriver.findElement(By.xpath("//*[@id='rateExch']/font/font"));

        WebElement finalAmount = chromeDriver.findElement(By.xpath("//*[@id='toHouseExch']/font/font"));

        System.out.println("Tečaj preračunavanja iz " + startingCurrency + " u " + convertedCurrency + " je " + exchangeRate.getText() + " a konačni izračun za zadani iznos je " + finalAmount.getText());

        transactionAmount.clear();
    }

    private void sleepFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    private void setRate(String rateType) {
        WebElement rateTypeDropdownMenu = chromeDriver.findElement(By.id("kurs"));
        Select rateTypeSelection = new Select(rateTypeDropdownMenu);
        rateTypeSelection.selectByValue(rateType);
        sleepFor(2000);
    }
}
