package es.uvigo.esei.daa.tarde.iu.firefox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.uvigo.esei.daa.tarde.daos.BaseDAOTest;
import es.uvigo.esei.daa.tarde.daos.articles.BookDAO;
import es.uvigo.esei.daa.tarde.entities.articles.Book;

@RunWith(Parameterized.class)
public class IndexWebTest extends BaseDAOTest{
    private static final int DEFAULT_WAIT_TIME = 20;
    private WebDriver driver;
    private final StringBuffer verificationErrors = new StringBuffer();



    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createBookData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "asoiaf", new Book[ ] {
                new Book("A Game of Thrones", new LocalDate(1996,  8,  6)),
                new Book("A Clash of Kings",  new LocalDate(1998, 11, 16)),
                new Book("A Storm of Words",  new LocalDate(2000,  8,  8)),
                new Book("A Fest for Crows",  new LocalDate(2005, 10, 17))
            }},
            { "pkdick", new Book[ ] {
                new Book("The Man in the High Castle",           new LocalDate(1962, 1, 1)),
                new Book("Do Androids Dream of Electric Sheep?", new LocalDate(1968, 1, 1)),
                new Book("Ubik",                                 new LocalDate(1969, 1, 1)),
                new Book("A Scanner Darkly",                     new LocalDate(1977, 1, 1)),
                new Book("VALIS",                                new LocalDate(1981, 1, 1)),
                new Book("The Divine Invasion",                  new LocalDate(1981, 1, 1)),
                new Book("The Owl in Daylight",                  new LocalDate(1982, 1, 1))
            }},
            { "odyssey", new Book[ ] {
                new Book("2001: A Space Odyssey",   new LocalDate(1968, 1, 1)),
                new Book("2010: Odyssey Two",       new LocalDate(1982, 1, 1)),
                new Book("2061: Odyssey Three",     new LocalDate(1987, 1, 1)),
                new Book("3001: The Final Odyssey", new LocalDate(1997, 1, 1))
            }}
        });
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private BookDAO dao;
    private final List<Book> bookList;

    public IndexWebTest(final String _, final Book [ ] bookList) {
        this.bookList = Arrays.asList(bookList);
    }


    @Before
    public void createBookDAO( ) {
        dao = new BookDAO();
    }

    @Before
    public void insertBooks( ) {
        entityManager.getTransaction().begin();
        for (final Book b : bookList) {
            b.setVerified(true);
            if (b.getId() == null) entityManager.persist(b);
            else                   entityManager.merge(b);
        }
        entityManager.getTransaction().commit();
    }

    @Before
    public void setUp() throws Exception {


        final String baseUrl = "http://localhost:9080/DAATarde/";

        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().addCookie(new Cookie("token", "bXJqYXRvOm1yamF0bw=="));

        // Driver will wait DEFAULT_WAIT_TIME if it doesn't find and element.
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.id("footer"));
    }

    @After
    public void tearDown() throws Exception {

        driver.quit();
        final String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Test
    //este test solo será pasado si hay 10 articulos en la BD
    public void index_can_show_ten_articles() throws Exception {
        verifyXpathCount("//div[@ng-repeat='article in articles']",10);
    }

    @Test
    //pensado para 10 articles per page
    public void index_can_search_empty_category_todo() throws Exception {

        driver.findElements(By.xpath("//input[@id='buscador']")).clear();
        final WebElement input = driver.findElement(By.xpath("//input[@id='buscador']"));
        input.sendKeys(Keys.RETURN);
        verifyXpathCount("//div[@ng-repeat='article in articles']",10);


    }

   /* @Test
    //test funciona para 10 books en BD
    public void index_can_search_empty_category_books() throws Exception {

        driver.findElements(By.xpath("//input[@id='buscador']")).clear();
        driver.finElements(By.className()
        final WebElement input = driver.findElement(By.xpath("//input[@id='buscador']"));
        input.sendKeys(Keys.RETURN);
        verifyXpathCount("//div[@ng-repeat='article in articles']",10);


    }*/


    /*@Test
    public void testEdit() throws Exception {
        final String name = "Hol";
        final String surname = "Mund";

        final String trId = driver.findElement(By.xpath("//tr[last()]")).getAttribute("id");
        driver.findElement(By.xpath("//tr[@id='" + trId + "']//a[text()='Edit']")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys(name);
        driver.findElement(By.name("surname")).clear();
        driver.findElement(By.name("surname")).sendKeys(surname);
        driver.findElement(By.id("btnSubmit")).click();
        waitForTextInElement(By.name("name"), "");
        waitForTextInElement(By.name("surname"), "");

        assertEquals(name,
            driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='name']")).getText()
        );
        assertEquals(surname,
            driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='surname']")).getText()
        );
    }

    @Test
    public void testDelete() throws Exception {
        final String trId = driver.findElement(By.xpath("//tr[last()]")).getAttribute("id");
        driver.findElement(By.xpath("(//a[contains(text(),'Delete')])[last()]")).click();

        waitUntilNotFindElement(By.id(trId));
    }*/

    private boolean waitUntilNotFindElement(final By by) {
        return new WebDriverWait(driver, DEFAULT_WAIT_TIME)
            .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private boolean waitForTextInElement(final By by, final String text) {
        return new WebDriverWait(driver, DEFAULT_WAIT_TIME)
            .until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    private void verifyXpathCount(final String xpath, final int count) {
        try {
            assertEquals(count, driver.findElements(By.xpath(xpath)).size());
        } catch (final Error e) {
            verificationErrors.append(e.toString());
        }
    }
}
