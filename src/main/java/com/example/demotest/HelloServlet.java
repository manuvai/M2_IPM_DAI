package com.example.demotest;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends AbstractServlet {

    @Override
    protected void responseGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        view("home", request, response);
    }

    @Override
    protected void responsePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}