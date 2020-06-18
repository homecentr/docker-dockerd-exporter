package helpers;

import io.homecentr.testcontainers.images.EnvironmentImageTagResolver;

public class DockerdExporterImageTagResolver extends EnvironmentImageTagResolver {
    public DockerdExporterImageTagResolver() {
        super("homecentr/dockerd-exporter:local");
    }
}
