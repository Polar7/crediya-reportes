package co.com.pragma.reports.sqs.listener.exception;

public class SQSProcessorException extends RuntimeException {
    public SQSProcessorException(String message) {
        super(message);
    }
}
