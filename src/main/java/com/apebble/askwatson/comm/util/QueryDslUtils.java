package com.apebble.askwatson.comm.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import static org.springframework.util.StringUtils.hasText;

public class QueryDslUtils {

    public static BooleanExpression alwaysTrue = Expressions.asBoolean(true).isTrue();

    public static BooleanExpression containsNullCheck(String searchWord, BooleanExpression booleanExpression) {
        return hasText(searchWord)
                ? booleanExpression
                : alwaysTrue;
    }

}
