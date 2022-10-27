package com.github.starship.dog.concurrency.threads.towns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.starship.dog.concurrency.threads.api.ExpeditionRequest;
import com.github.starship.dog.concurrency.threads.api.HttpRequest;
import com.github.starship.dog.concurrency.threads.toolbox.GopherException;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class AnyGopherTown implements GopherTown {

    // обработчик запросов на потребности экспедиции в формате JSON
    private final static ObjectMapper MAPPER = new ObjectMapper();

    protected HttpRequest readBody(InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String protocol = reader.readLine();
        Map<String, String> headers = new HashMap<>();

        while (true) {
            String header = reader.readLine();

            if("".equalsIgnoreCase(header))
                break;

            String[] split = header.split(": ");
            headers.put(split[0].toLowerCase(), split[1]);
        }

        String[] protocolParts = protocol.split(" ");
        final HttpRequest httpRequest = new HttpRequest(protocolParts[0],protocolParts[1],protocolParts[2]);
        httpRequest.setHeaders(headers);

        if(headers.containsKey("content-length")){
            int contentLength = Integer.parseInt(headers.get("content-length"));

            char[] body = new char[contentLength];
            reader.read(body);

            httpRequest.setBody(body);
        }

        return httpRequest;
    }

    protected ExpeditionRequest handleConnection(Socket connection) {
        try {
            final HttpRequest request = readBody(connection.getInputStream());
            final ExpeditionRequest expeditionRequest = MAPPER.readValue(request.getBody(), ExpeditionRequest.class);

            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(request.getProtocol() + " 200 OK\r\n");
            writer.write("Server: Gopher Expedition\r\n\r\n");
            writer.flush();
            writer.close();

            return expeditionRequest;
        } catch (Exception exception) {
            throw new GopherException("Произошла неполадка, вызывайте бригаду!", exception);
        }
    }
}
