package io.arcotech.quizAcademy.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomLocalDateTimeSerializer(private val pattern: String = "yyyy-MM-dd HH:mm") : JsonSerializer<LocalDateTime>() {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, serializers: com.fasterxml.jackson.databind.SerializerProvider?) {
        gen?.writeString(value?.format(DateTimeFormatter.ofPattern(pattern)))
    }
}


class CustomLocalDateTimeDeserializer(private val pattern: String = "yyyy-MM-dd HH:mm") : JsonDeserializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime {
        val dateAsString = parser.valueAsString
        return LocalDateTime.parse(dateAsString, DateTimeFormatter.ofPattern(pattern))
    }
}