package com.demo.ms_news.controller;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) { super(msg); }
}