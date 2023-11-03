package us.codecraft.webmagic.pipeline;

import org.junit.Test;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class ConsolePipelineTest {

    @Test
    public void testProcess() {
        // Redirect System.out to capture the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ConsolePipeline consolePipeline = new ConsolePipeline();
        ResultItems resultItems = new ResultItems();
        resultItems.setRequest(new Request("http://webmagic.com"));


        Map<String, Object> data = new HashMap<>();
        data.put("title", "Sample Title");
        data.put("content", "Sample Content");
        resultItems.getAll().putAll(data);

        Task task = new Task() {
            @Override
            public String getUUID() {
                return "test_task";
            }

            @Override
            public Site getSite() {
                return Site.me();
            }
        };

        consolePipeline.process(resultItems, task);


        System.setOut(System.out);


        String consoleOutput = outputStream.toString().trim();
        consoleOutput = normalizeLineSeparators(consoleOutput);


        String expectedOutput = "get page: http://webmagic.com\n" +
                "title:\tSample Title\n" +
                "content:\tSample Content";

        assertEquals(expectedOutput, consoleOutput);
    }

    private String normalizeLineSeparators(String text) {

        return text.replaceAll("\r\n", "\n");
    }
}
