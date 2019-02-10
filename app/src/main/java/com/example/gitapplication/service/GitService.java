package com.example.gitapplication.service;

import com.example.gitapplication.entity.Repository;
import com.example.gitapplication.exceptions.NotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GitService {

    private final String USER_AGENT = "Mozilla/5.0";

    public List<String> getAllRepositories(String nick) throws Exception {
        Type type = new TypeToken<Repository[]>(){}.getType();
        try {
            Repository[] repositories = new Gson().fromJson(sendGet(nick), type);
            return Arrays.stream(repositories).map(Repository::getName).collect(Collectors.toList());
        } catch (NotFoundException e) {
            return new ArrayList<>();
        }
    }

    private String sendGet(String nick) throws Exception {

        String url = String.format("https://api.github.com/users/%s/repos",nick);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        if (responseCode != 200)
            throw new NotFoundException();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}