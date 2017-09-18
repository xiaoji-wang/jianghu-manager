package application.exception;

import com.google.inject.Inject;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by wxji on 2017-01-05.
 */
public class CommonExceptionHandler extends DefaultHttpErrorHandler {

    @Inject
    public CommonExceptionHandler(Configuration configuration, Environment environment, OptionalSourceMapper optionalSourceMapper, Provider<Router> provider) {
        super(configuration, environment, optionalSourceMapper, provider);
    }

    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader requestHeader, Throwable throwable) {
        if (throwable instanceof CommonException) {
            return CompletableFuture.completedFuture(Results.badRequest(throwable.getMessage()));
        }
        return super.onServerError(requestHeader, throwable);
    }
}
