package delphi.backend.nrxcodegeneration.foundation.error;

import javax.inject.Singleton;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delphi.backend.nrxcodegeneration.foundation.utils.JsonUtil;
import delphi.backend.nrxcodegeneration.foundation.constant.FoundationConstant;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Produces
@Singleton
@Requires(classes = {BadRequestException.class, ExceptionHandler.class})
public class BadRequestExceptionHandler implements ExceptionHandler<BadRequestException, HttpResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(BadRequestExceptionHandler.class);

    /**
     * Handles bad request exception
     *
     * @param request   The http request
     * @param exception The bad request exception
     * @return HttpResponse
     */
    @Override
    public HttpResponse handle(HttpRequest request, BadRequestException exception) {
        var exceptionMessage = exception.getMessage();
        var errorMessageObject = JsonUtil.getJsonObject(FoundationConstant.MESSAGE, exceptionMessage);

        LOG.error(exceptionMessage);

        return HttpResponse.badRequest(errorMessageObject.toString());
    }
}
