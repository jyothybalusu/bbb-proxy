package com.testdirect.app;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.auth.AuthType;


/**
 * Created by Linds on 31/08/2016.
 */
public class Proxy {
    public Proxy(){}
    private String browsermobPort;
    private String proxyPort;
    BrowserMobProxy proxy;


    public void shutDown()
    {
        try
        {
            System.out.println("Enter Q followed by the Return key to quit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            while(!input.toLowerCase().equals("q"))
            {
                input=scanner.next();
            }
            proxy.stop();
            System.out.println("Proxy stopped");
        }
        catch(Exception e)
        {
            System.out.println("Unable to shut down proxy\n" + e.toString());
        }
    }


    public void setupProxy(){
        try {

            FileReader fileReader = new FileReader("config/properties");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String property=null;
            while ((property= bufferedReader.readLine())!=null)
            {
                if (property.contains("proxyport")) {
                    proxyPort = property.substring(property.length() - 4, property.length());
                }
            }
            if (proxyPort==null)
            {
                System.out.println("The config/properties file must specify the proxyport");
                System.exit(1);
            }

            proxy = new BrowserMobProxyServer();
            proxy.setTrustAllServers(true);
            proxy.start(Integer.parseInt(proxyPort));
            int portNum = proxy.getPort();
            System.out.println("Proxy started on port " + portNum);
        }
        catch(Exception e)
        {
            System.out.println("Unable to setup proxy\n" + e.toString());
        }
    }

    public  void setupBasicAuthorization()
    {

        //read in the urls from the config file for which we need secure authentication
        System.out.println("Setting up basic authorization");

        try {
            FileReader fileReader = new FileReader("config/securePages");

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String secureUrl = null;
            while ((secureUrl= bufferedReader.readLine())!=null)
            {
                proxy.autoAuthorization(secureUrl, "what", "whit", AuthType.BASIC);
                System.out.println("Setting basic authorization for " + secureUrl);
            }
        }
        catch (Exception e)
        {
            System.out.println("Unable to setup basic authorization for secure pages\n" + e.toString());
        }
    }

    public void setupBlacklist()
    {
        //blacklist certain URLs that cause the proxy to hang
        //proxy.blacklistRequests(".*adobedtm.*",404);
        proxy.blacklistRequests(".*browser-sync-client.*",404);
    }
}
