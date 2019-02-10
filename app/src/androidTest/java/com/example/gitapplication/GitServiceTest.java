package com.example.gitapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class GitServiceTest {

    @Test
    public void getAllRepositories() throws Exception {
        GitService gitService = new GitService();
        System.out.println(gitService.getAllRepositories());
    }
}