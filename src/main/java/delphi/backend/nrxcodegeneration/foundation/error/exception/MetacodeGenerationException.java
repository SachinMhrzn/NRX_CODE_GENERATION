package delphi.backend.nrxcodegeneration.foundation.error.exception;

public class MetacodeGenerationException extends RuntimeException {
    public MetacodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetacodeGenerationException(String message) {
        super(message);
    }
}
