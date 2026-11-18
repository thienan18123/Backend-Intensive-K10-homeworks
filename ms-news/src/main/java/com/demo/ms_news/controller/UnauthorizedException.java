package com.demo.ms_news.controller;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) { super(msg); }
}