package com.example.cinestream.common.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalGraphQlExceptionHandler {

    @GraphQlExceptionHandler(RuntimeException.class)
    public GraphQLError handleRuntimeException(RuntimeException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .message(ex.getMessage())
                .build();
    }
}