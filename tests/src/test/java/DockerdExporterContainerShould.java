import helpers.DockerdExporterImageTagResolver;
import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.HttpResponse;
import io.homecentr.testcontainers.containers.wait.strategy.WaitEx;
import io.homecentr.testcontainers.images.PullPolicyEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DockerdExporterContainerShould {
    private static GenericContainerEx _exporterContainer;
    private static final Logger logger = LoggerFactory.getLogger(DockerdExporterContainerShould.class);

    @BeforeClass
    public static void before() {
        _exporterContainer = new GenericContainerEx<>(new DockerdExporterImageTagResolver())
                .withImagePullPolicy(PullPolicyEx.never())
                .waitingFor(WaitEx.forS6OverlayStart());

        _exporterContainer.start();
        _exporterContainer.followOutput(new Slf4jLogConsumer(logger));
    }

    @AfterClass
    public static void after() {
        _exporterContainer.close();
    }

    @Test
    public void exposeMetrics() throws IOException {
        HttpResponse response = _exporterContainer.makeHttpRequest(9323, "/metrics");

        assertEquals(200, response.getResponseCode());
    }
}