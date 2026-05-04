package com.basic.http.handler;

import java.io.IOException;
import java.util.Locale;
import java.sql.SQLException;

import com.basic.exception.ApiException;
import com.basic.exception.BadRequestException;
import com.basic.http.util.HttpExchangeHelper;
import com.basic.http.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class BaseHandler implements HttpHandler {
    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        HttpExchangeHelper.applyDefaultHeaders(exchange);

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            HttpExchangeHelper.sendNoContent(exchange);
            return;
        }

        try {
            handleRequest(exchange);
        } catch (ApiException ex) {
            HttpExchangeHelper.sendJson(exchange, ex.getStatusCode(), JsonUtil.error(ex.getMessage()));
        } catch (SQLException ex) {
            HttpExchangeHelper.sendJson(exchange, 500, JsonUtil.error("Internal error accessing the database."));
        } catch (IllegalArgumentException ex) {
            HttpExchangeHelper.sendJson(exchange, 400, JsonUtil.error(ex.getMessage()));
        } catch (Exception ex) {
            HttpExchangeHelper.sendJson(exchange, 500, JsonUtil.error("Internal server error."));
        } finally {
            exchange.close();
        }
    }

    protected abstract void handleRequest(HttpExchange exchange) throws Exception;

    protected String requireJsonBody(HttpExchange exchange) throws IOException {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).contains("application/json")) {
            throw new BadRequestException("Content-Type must be application/json.");
        }
        return HttpExchangeHelper.readRequestBody(exchange);
    }

    protected int extractIdFromPath(HttpExchange exchange, String basePath) {
        String path = exchange.getRequestURI().getPath();
        if (path.equals(basePath) || path.equals(basePath + "/")) {
            return -1;
        }

        if (!path.startsWith(basePath + "/")) {
            throw new BadRequestException("Invalid route.");
        }

        String idSegment = path.substring(basePath.length() + 1);
        if (idSegment.contains("/")) {
            throw new BadRequestException("Invalid route.");
        }

        try {
            int id = Integer.parseInt(idSegment);
            if (id <= 0) {
                throw new NumberFormatException();
            }
            return id;
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Invalid identifier.");
        }
    }
}
