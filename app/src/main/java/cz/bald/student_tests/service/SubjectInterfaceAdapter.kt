package cz.bald.student_tests.service

import com.google.gson.*
import cz.bald.student_tests.enums.CzechSubject
import cz.bald.student_tests.enums.SlovakSubject
import cz.bald.student_tests.enums.Subject
import java.lang.reflect.Type

class SubjectInterfaceAdapter : JsonSerializer<Subject>, JsonDeserializer<Subject> {

    private val prefix = "PREFIX"
    private val data = "DATA"

    override fun serialize(src: Subject, typeOfSrc: Type?, context: JsonSerializationContext)
            : JsonElement {
        val jsonObject = JsonObject()
        val pref = if (src.getAllSubjects().contains("SLOVAK")) "SK" else "CZ"
        jsonObject.addProperty(prefix, pref)
        jsonObject.addProperty(data, src.getSubjectName())
        return jsonObject
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?,
            context: JsonDeserializationContext): Subject {
        val jsonObject = json.asJsonObject
        var prim = jsonObject.get(prefix) as JsonPrimitive
        val pref = prim.asString
        prim = jsonObject.get(data) as JsonPrimitive
        val value = prim.asString
        return if (pref == "SK") {
            SlovakSubject.valueOf(value)
        } else {
            CzechSubject.valueOf(value)
        }
    }
}