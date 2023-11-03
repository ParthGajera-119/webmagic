package us.codecraft.webmagic.pipeline;

import org.junit.Before;
import org.junit.Test;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResultItemsCollectorPipelineTest {

    private ResultItemsCollectorPipeline collectorPipeline;
    private Task mockTask;

    @Before
    public void setUp() {
        collectorPipeline = new ResultItemsCollectorPipeline();
        mockTask = new MockTask();
    }

    @Test
    public void testProcessAndCollected() {
        ResultItems resultItems1 = new ResultItems();
        ResultItems resultItems2 = new ResultItems();

        collectorPipeline.process(resultItems1, mockTask);
        collectorPipeline.process(resultItems2, mockTask);

        List<ResultItems> collectedItems = collectorPipeline.getCollected();

        assertEquals(2, collectedItems.size());
        assertEquals(resultItems1, collectedItems.get(0));
        assertEquals(resultItems2, collectedItems.get(1));
    }

    // Mock Task implementation for testing
    private static class MockTask implements Task {
        @Override
        public String getUUID() {
            return "mock_task";
        }

        @Override
        public Site getSite() {
            return Site.me();
        }
    }
}

