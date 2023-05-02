package ru.vood.githubdowload.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import ru.vood.githubdowload.property.GitToken


@Configuration
open class RestTemplateConfiguration {

    @Bean
    fun restTemplate(b: RestTemplateBuilder,
                     gitToken: GitToken
    ): RestTemplate {
        return b
            .defaultHeader("Authorization", """Bearer ${gitToken.token}""")
            .build()
    }
}