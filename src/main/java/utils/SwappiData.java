/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author claudia
 */
public class SwappiData implements Callable<String>
{

    private String url;

    public SwappiData(String url) {
        this.url = url;
    }
    public String getSwappiData() throws MalformedURLException, IOException
    {
        URL url = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    @Override
    public String call() throws Exception 
    {
        return getSwappiData();

    }
    
    //ParallelPinger

    public static List<String> getJsonFromAllServers(String param,int id) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Future<String>> futures = new ArrayList();

        for (int i = 1; i <= id; i++) {
           
            Callable<String> pingUrlCallable = new SwappiData("https://swapi.co/api/" + param + "/" + i);
            Future<String> future = executor.submit(pingUrlCallable);
            futures.add(future);
        }

        List<String> results = new ArrayList();
        for (Future<String> future : futures) {
            String result = future.get(2, TimeUnit.MINUTES);
            results.add(result);
        }
        executor.shutdown();
        return results;
    }

}
