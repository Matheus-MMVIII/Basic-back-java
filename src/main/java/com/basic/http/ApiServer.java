package com.basic.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.basic.config.AppConfig;
import com.basic.http.handler.HealthHandler;
import com.basic.http.handler.ProductsHandler;
import com.basic.repository.ProductRepository;
import com.basic.service.ProductService;
import com.sun.net.httpserver.HttpServer;

public class ApiServer {
    private final HttpServer server;
    private final ExecutorService executor;

    public ApiServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(AppConfig.getPort()), AppConfig.getBacklog());
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        registerContexts();
        server.setExecutor(executor);
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void start() {
        server.start();
        System.out.println("Server started on port " + AppConfig.getPort());
    }

    public void stop() {
        server.stop(0);
        executor.shutdown();
    }

    private void registerContexts() {
        ProductService productService = new ProductService(new ProductRepository());

        server.createContext("/health", new HealthHandler());
        server.createContext("/api/products", new ProductsHandler(productService));
    }
}
