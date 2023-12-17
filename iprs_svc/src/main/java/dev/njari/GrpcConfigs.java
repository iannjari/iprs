package dev.njari;

import dev.njari.common_utils.config.GrpcServerAutoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author njari_mathenge
 * on 17/12/2023.
 * github.com/iannjari
 */
@Configuration
@Import({GrpcServerAutoConfig.class})
class GrpcConfigs {}
