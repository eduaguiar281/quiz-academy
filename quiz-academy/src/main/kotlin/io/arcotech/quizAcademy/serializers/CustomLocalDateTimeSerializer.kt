package io.arcotech.quizAcademy.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomLocalDateTimeSerializer(private val pattern: String = "yyyy-MM-dd HH:mm") : JsonSerializer<LocalDateTime>() {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, serializers: com.fasterxml.jackson.databind.SerializerProvider?) {
        gen?.writeString(value?.format(DateTimeFormatter.ofPattern(pattern)))
    }
}