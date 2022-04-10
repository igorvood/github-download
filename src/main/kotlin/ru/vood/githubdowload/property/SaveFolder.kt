package ru.vood.githubdowload.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "save")
class SaveFolder {
    lateinit var folder: String
}