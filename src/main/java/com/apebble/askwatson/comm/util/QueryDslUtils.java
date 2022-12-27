package com.apebble.askwatson.comm.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class QueryDslUtils {

    public static BooleanExpression alwaysTrue = Expressions.asBoolean(true).isTrue();

}
