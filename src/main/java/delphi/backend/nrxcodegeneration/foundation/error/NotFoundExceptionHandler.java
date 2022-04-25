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
import delphi.backend.nrxcodegeneration.foundation.error.BadRequestExceptionHandler;
import delphi.backend.nrxcodegeneration.foundation.error.exception.NotFoundException;

@Produces
@Singleton
@Requires(classes = {NotFoundException.class, ExceptionHandler.class})
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(BadRequestExceptionHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request, NotFoundException exception) {
        var exceptionMessage = exception.getMessage();
        var errorMessageObject = JsonUtil.getJsonObject(FoundationConstant.MESSAGE, exceptionMessage);

        LOG.error(exceptionMessage);

        return HttpResponse.notFound(errorMessageObject.toString());
    }
}
