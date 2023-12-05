package dev.farneser.tasktracker.emailsender.config

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.rabbitmq.client.ConnectionFactory
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.RabbitMQContainer

@Profile("test")
@Configuration
class TestContainersConfig {
    @Bean
    fun rabbitMqContainer(): ConnectionFactory {

        val container = RabbitMQContainer("rabbitmq:3.8.9-management-alpine")
            .withUser("rabbitmq", "rabbitmq")
            .withExposedPorts(5672)
            .withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(
                    HostConfig().withPortBindings(
                        PortBinding(
                            Ports.Binding.bindPort(14253),
                            ExposedPort(5672)
                        )
                    )
                )
            }

        container.start()

        val connectionFactoryBean = RabbitConnectionFactoryBean()

        connectionFactoryBean.setHost(container.host)
        connectionFactoryBean.setPort(container.amqpPort)
        connectionFactoryBean.setUsername(container.adminUsername)
        connectionFactoryBean.setPassword(container.adminPassword)
        connectionFactoryBean.afterPropertiesSet()

        return connectionFactoryBean.getObject()
    }
}