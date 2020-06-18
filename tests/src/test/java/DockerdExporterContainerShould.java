import helpers.DockerdExporterImageTagResolver;
import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.HttpResponse;
import io.homecentr.testcontainers.containers.wait.strategy.WaitEx;
import io.homecentr.testcontainers.images.PullPolicyEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DockerdExporterContainerShould {
    private static GenericContainerEx _exporterContainer;

    @BeforeClass
    public static void before() {
        _exporterContainer = new GenericContainerEx<>(new DockerdExporterImageTagResolver())
                .withEnv("PUID", "7088")
                .withEnv("PGID", "7099")
                .withImagePullPolicy(PullPolicyEx.never())
                .waitingFor(WaitEx.forS6OverlayStart());

        _exporterContainer.start();
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