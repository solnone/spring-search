package com.sipios.springsearch.strategies

import com.sipios.springsearch.SearchOperation
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import java.time.ZonedDateTime
import kotlin.reflect.KClass

class ZonedDateTimeStrategy : ParsingStrategy {
    override fun buildPredicate(
        builder: CriteriaBuilder,
        path: Path<*>,
        fieldName: String,
        ops: SearchOperation?,
        value: Any?
    ): Predicate? {
        return when (ops) {
            SearchOperation.GREATER_THAN -> builder.greaterThan(path.get(fieldName), value as ZonedDateTime)
            SearchOperation.LESS_THAN -> builder.lessThan(path.get(fieldName), value as ZonedDateTime)
            SearchOperation.CONTAINS -> builder.like(path.get(fieldName), "%$value%")
            else -> super.buildPredicate(builder, path, fieldName, ops, value)
        }
    }

    override fun parse(value: String?, fieldClass: KClass<out Any>): Any? {
        if (value != null && value.length > 18) {
            val v = value
            return ZonedDateTime.parse(v.replace("_", ":"))
        }
        return value
    }
}
