package io.arcotech.quizAcademy.services.featuretoggles.defaultfilter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.arcotech.quizAcademy.serializers.CustomLocalDateTimeDeserializer
import io.arcotech.quizAcademy.serializers.CustomLocalDateTimeSerializer
import java.time.LocalDateTime

open class DefaultFeatureFilter constructor (
    private val featureName: String,
    private var configurationJson: String){


    private var configuration: DefaultFeatureConfiguration? = null

    open fun evaluate(): Boolean{
        configuration = readConfiguration(DefaultFeatureConfiguration::class.java)
        return if (configuration == null)
            false
        else
            configuration!!.enabled
    }

    fun setConfigurationJson(json: String){
        configurationJson = json
    }
    protected open fun <T:DefaultFeatureConfiguration> readConfiguration(featureClass: Class<T>): T?{
        val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        objectMapper.registerModule(
            SimpleModule()
                .addSerializer(LocalDateTime::class.java, CustomLocalDateTimeSerializer())
                .addDeserializer(LocalDateTime::class.java, CustomLocalDateTimeDeserializer())
        )

        val root = objectMapper.readTree(configurationJson)
        val jsonNode = root.get(featureName)
        if (jsonNode != null)
            return objectMapper.readValue(jsonNode.toString(), featureClass)
        return null
    }
}
