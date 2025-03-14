package io.quarkus.flyway.test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.arc.InactiveBeanException;
import io.quarkus.test.QuarkusUnitTest;

public class FlywayExtensionConfigUrlMissingDefaultDatasourceDynamicInjectionTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            // The URL won't be missing if dev services are enabled
            .overrideConfigKey("quarkus.devservices.enabled", "false");

    @Inject
    Instance<Flyway> flyway;

    @Test
    @DisplayName("If the URL is missing for the default datasource, the application should boot, but Flyway should be deactivated for that datasource")
    public void testBootSucceedsButFlywayDeactivated() {
        assertThatThrownBy(flyway::get)
                .isInstanceOf(InactiveBeanException.class)
                .hasMessageContainingAll(
                        "Flyway for datasource '<default>' was deactivated automatically because this datasource was deactivated",
                        "Datasource '<default>' was deactivated automatically because its URL is not set.",
                        "To avoid this exception while keeping the bean inactive", // Message from Arc with generic hints
                        "To activate the datasource, set configuration property 'quarkus.datasource.jdbc.url'.",
                        "Refer to https://quarkus.io/guides/datasource for guidance.");
    }

}
