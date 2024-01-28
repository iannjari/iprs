package dev.njari.config;

import dev.njari.common_utils.config.GrpcClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author njari_mathenge
 * on 28/01/2023.
 * github.com/iannjari
 */
@Configuration
@Import({GrpcClientAutoConfiguration.class})
class GrpcConfigs {}
