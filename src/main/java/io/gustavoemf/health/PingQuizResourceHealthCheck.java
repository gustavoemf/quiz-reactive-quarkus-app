package io.gustavoemf.health;

import io.gustavoemf.rest.QuizResource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * HealthCheck to ping the Quiz Service
 */
@Liveness
public record PingQuizResourceHealthCheck(QuizResource quizResource) implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        var response = this.quizResource.ping();

        return HealthCheckResponse.named("Ping Quiz REST Endpoint")
                .withData("Response", response)
                .up()
                .build();
    }
}
