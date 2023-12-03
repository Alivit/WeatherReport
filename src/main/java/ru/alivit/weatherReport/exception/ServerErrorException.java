package ru.alivit.weatherReport.exception;

public class ServerErrorException extends RuntimeException{

    public ServerErrorException(String msg) {
        super(msg);
    }
}
