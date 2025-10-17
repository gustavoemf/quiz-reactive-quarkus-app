package io.gustavoemf.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * HealthCheck to ping the Quiz Service
 */
@Liveness
public record PingQuizResourceHealthCheck() implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return null;
    }
}
