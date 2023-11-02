package io.arcotech.quizAcademy.services.featuretoggles

import com.github.benmanes.caffeine.cache.Caffeine
import io.arcotech.quizAcademy.services.featuretoggles.defaultfilter.DefaultFeatureFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.appconfigdata.AppConfigDataClient
import software.amazon.awssdk.services.appconfigdata.model.GetLatestConfigurationRequest
import software.amazon.awssdk.services.appconfigdata.model.StartConfigurationSessionRequest
import java.util.concurrent.TimeUnit

@Service
@ConfigurationPropertiesScan
class FeatureToggleManagerService {

    private val cache = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build<String, String>()

    private lateinit var _appConfigClient: AppConfigDataClient

    @Autowired
    private lateinit var _featureToggleConfiguration: FeatureToggleConfiguration


    private val region = Region.US_EAST_2
    private val profile = "default"

    private fun getConfiguration(): String{
        val cacheKey = _featureToggleConfiguration.getKey()
        var cacheValue = cache.getIfPresent(cacheKey)
        if (cacheValue == null){
            cacheValue = getConfigurationFromService()
            cache.put(cacheKey, cacheValue)
        }
        return cacheValue
    }

    private fun getConfigurationFromService(): String{
        _appConfigClient = AppConfigDataClient.builder()
            .region(region)
            .credentialsProvider(DefaultCredentialsProvider.builder().profileName(profile).build())
            .build()

        val startSessionResponse = _appConfigClient.startConfigurationSession(StartConfigurationSessionRequest.builder()
            .applicationIdentifier(_featureToggleConfiguration.configuration.application)
            .environmentIdentifier(_featureToggleConfiguration.configuration.enviroment)
            .configurationProfileIdentifier(_featureToggleConfiguration.configuration.profile)
            .build())

        val response = _appConfigClient.getLatestConfiguration(GetLatestConfigurationRequest.builder()
            .configurationToken(startSessionResponse.initialConfigurationToken())
            .build()
        )
        return response.configuration().asUtf8String()
    }

    fun isEnabled(featureName: String):Boolean{
        val filter = DefaultFeatureFilter(featureName, getConfiguration())
        return filter.evaluate()
    }

    fun <T: DefaultFeatureFilter> isEnabled(filter: T): Boolean{
        filter.setConfigurationJson(getConfiguration())
        return filter.evaluate()
    }



}

