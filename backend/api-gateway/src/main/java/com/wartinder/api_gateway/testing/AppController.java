package com.wartinder.api_gateway.testing;



import java.util.Collections;
import java.util.List;
import java.util.Map;


public class AppController {


    public List<Message> List() {
        return Collections.singletonList(new Message("Test list"));
    }


    public Message create( Message message) {
        System.out.println("mensaie guardado: " + message);
        return message;
    }


    public Map<String, String> authorized( String code) {
        return Collections.singletonMap("code", code);
    }
}
