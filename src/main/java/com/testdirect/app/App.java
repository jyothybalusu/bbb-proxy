package com.testdirect.app;

public class App
{
    public static void main( String[] args )
    {
        Proxy proxy = new Proxy();
        proxy.setupProxy();
        proxy.setupBasicAuthorization();
        proxy.setupBlacklist();
        proxy.shutDown();
    }
}