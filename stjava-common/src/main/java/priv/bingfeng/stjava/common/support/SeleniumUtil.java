package priv.bingfeng.stjava.common.support;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

public class SeleniumUtil {

    public static WebElement findElement(SearchContext tag, String selector) {
        return findElement(tag, selector, 20);
    }
    public static WebElement findElement(SearchContext tag, String selector, int loopTimes) {
        WebElement element;
        while (--loopTimes != 0) {
            try {
                element = tag.findElement(By.cssSelector(selector));
                if (element.isEnabled() && element.isDisplayed()) {
                    return element;
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void clickWithMoveOn(WebDriver driver, WebElement tag) {
        Actions action = new Actions(driver);
        action.moveToElement(tag).click(tag).perform();
    }

    public static String getCookies(WebDriver driver) {
        return JsonUtil.toString(driver.manage().getCookies());
    }

    public static void setCookie(WebDriver driver, String cookieJson) {
        JsonUtil.parseToList(cookieJson, Cookie.class).forEach(item -> driver.manage().addCookie(item));
    }

    public static Object runJs(WebDriver driver, String script) {
        return ((JavascriptExecutor)driver).executeScript(script);
    }

    public static WebDriver openFirefox() {
        return openFirefox(0);
    }
    public static WebDriver openFirefox(int proxyPort) {
        FirefoxProfile profile = new FirefoxProfile();
        closePush(profile);
        if (proxyPort != 0)
            setProxy(profile, proxyPort);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        return new FirefoxDriver(options);
    }

    private static void closePush(FirefoxProfile profile) {
        profile.setPreference("dom.push.enabled", false);
        profile.setPreference("dom.webnotifications.enabled", false);
        profile.setPreference("browser.tabs.remote.autostart", false);
        profile.setPreference("browser.tabs.remote.autostart.1", false);
        profile.setPreference("browser.tabs.remote.autostart.2", false);
    }

    private static void setProxy(FirefoxProfile profile, int port) {
        profile.setPreference("network.proxy.type", 1);
        profile.setPreference("network.proxy.socks_remote_dns", true);
        profile.setPreference("network.proxy.socks", "127.0.0.1");
        profile.setPreference("network.proxy.socks_port", port);
        profile.setPreference("intl.accept_languages", "zh-cn,en-us,en");
    }

    public static void main(String[] args) {
        WebDriver driver = openFirefox(8118);
        Object o = runJs(driver, "return navigator.userAgent;");
        System.out.println(o.toString());

        System.out.println(getCookies(driver));
        driver.get("https://www.facebook.com/");
        System.out.println(getCookies(driver));

        WebElement tag = SeleniumUtil.findElement(driver, "#email");
        SeleniumUtil.clickWithMoveOn(driver, tag);
        tag.sendKeys("mn2655483@protonmail.com");

        tag = SeleniumUtil.findElement(driver, "#pass");
        SeleniumUtil.clickWithMoveOn(driver, tag);
        tag.sendKeys("Mrs123");

        tag = SeleniumUtil.findElement(driver, "#loginbutton");
        SeleniumUtil.clickWithMoveOn(driver, tag);
    }

}
