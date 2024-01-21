package dev.njari.config;

import dev.njari.common_utils.config.KafkaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author njari_mathenge
 * on 21/01/2024.
 * github.com/iannjari
 */
@Configuration
@Import({KafkaConfiguration.class})
public class KafkaConfig {
}
