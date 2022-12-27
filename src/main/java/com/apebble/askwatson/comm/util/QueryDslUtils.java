package com.apebble.askwatson.comm.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.lang.Nullable;

import static org.springframework.util.StringUtils.hasText;

public class QueryDslUtils {

    public static BooleanExpression alwaysTrue = Expressions.asBoolean(true).isTrue();

    public static BooleanExpression stringCheck(@Nullable String str, BooleanExpression booleanExpression) {
        return hasText(str)
                ? booleanExpression
                : alwaysTrue;
    }
}
