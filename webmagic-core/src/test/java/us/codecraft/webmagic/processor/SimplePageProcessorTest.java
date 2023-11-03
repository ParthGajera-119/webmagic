package us.codecraft.webmagic.processor;

import org.junit.Before;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import static junit.framework.TestCase.assertEquals;

public class SimplePageProcessorTest {

    private SimplePageProcessor simplePageProcessor;

    @Before
    public void setUp() {
        // Create a SimplePageProcessor with a test URL pattern
        simplePageProcessor = new SimplePageProcessor("http://webmagic\\.com/.*");
    }

    @Test
    public void testProcess() {
        // Create a Page object and set its URL and content
        Page page = new Page();
        page.setRequest(new us.codecraft.webmagic.Request("http://webmagic.com/test"));
        page.setRawText("<html><head><title>Test Title</title></head><body>Test Content</body></html>");

        // Invoke the process method of the SimplePageProcessor
        simplePageProcessor.process(page);

        // Extract text content from the HTML elements and verify
        assertEquals("<title>Test Title</title>", page.getResultItems().get("title").toString());
        assertEquals("<html><head><title>TestTitle</title></head><body>TestContent</body></html>", page.getResultItems().get("html").toString().replaceAll("\\s+", ""));

    }



    @Test
    public void testGetSite() {
        // Test the getSite method of the SimplePageProcessor
        Site site = simplePageProcessor.getSite();

        // Verify the settings of the Site object
        assertEquals(5000, site.getTimeOut());
        assertEquals(0, site.getRetryTimes());
        // Add more assertions for other site settings.
    }
}

