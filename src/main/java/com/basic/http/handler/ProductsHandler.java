package com.basic.http.handler;

import java.util.List;
import java.util.Map;

import com.basic.http.util.HttpExchangeHelper;
import com.basic.http.util.JsonUtil;
import com.basic.dto.PageResult;
import com.basic.model.Product;
import com.basic.service.ProductService;
import com.sun.net.httpserver.HttpExchange;

public class ProductsHandler extends BaseHandler {
    private static final String BASE_PATH = "/api/products";

    private final ProductService productService;

    public ProductsHandler(ProductService productService) {
        this.productService = productService;
    }
    @Override
    protected void handleRequest(HttpExchange exchange) throws Exception {
        String method = exchange.getRequestMethod();
        String id = extractIdFromPath(exchange, BASE_PATH);
        if ("GET".equalsIgnoreCase(method) && id.equalsIgnoreCase("-1")) {
            Map<String, String> queryParams = parseQuery(exchange.getRequestURI().getQuery());

            String cursor = queryParams.getOrDefault("cursor", "0");

            int limit = queryParams.containsKey("limit")
                    ? Math.min(Integer.parseInt(queryParams.get("limit")), 100)
                    : 20;

            PageResult<Product> page = productService.listPage(cursor, limit);

            HttpExchangeHelper.sendJson(exchange, 200, JsonUtil.pageProducts(page));
            return;
        }
        if ("GET".equalsIgnoreCase(method)) {
            Product product = productService.findById(id);
            HttpExchangeHelper.sendJson(exchange, 200, JsonUtil.product(product));
            return;
        }
        if ("POST".equalsIgnoreCase(method) && id.equalsIgnoreCase("-1")) {
            Map<String, String> payload = JsonUtil.parseFlatObject(requireJsonBody(exchange));
            Product createdProduct = productService.create(payload);
            HttpExchangeHelper.sendJson(exchange, 201, JsonUtil.product(createdProduct));
            return;
        }
        if ("PUT".equalsIgnoreCase(method) && !(id.equalsIgnoreCase("-1"))) {
            Map<String, String> payload = JsonUtil.parseFlatObject(requireJsonBody(exchange));
            Product createdProduct = productService.update(id, payload);
            HttpExchangeHelper.sendJson(exchange, 200, JsonUtil.product(createdProduct));
            return;
        }
        if ("DELETE".equalsIgnoreCase(method) && !(id.equalsIgnoreCase("-1"))) {
            productService.delete(id);
            HttpExchangeHelper.sendNoContent(exchange);
            return;
        }

        HttpExchangeHelper.sendMethodNotAllowed(exchange, "GET, POST, PUT, DELETE, OPTIONS");
    }
}
