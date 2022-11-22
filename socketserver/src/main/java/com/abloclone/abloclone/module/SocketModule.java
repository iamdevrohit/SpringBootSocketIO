package com.abloclone.abloclone.module;

import com.abloclone.abloclone.model.Message;
import com.abloclone.abloclone.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {

    private final SocketIOServer server;


    private final SocketService socketService;


    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;

        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());

        server.addConnectListener(onConnected2());
        server.addDisconnectListener(onDisconnected2());
        server.addEventListener("send_message_2", Message.class, onChatReceived2());

    }


    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {

            synchronized(this) {
                log.info(data.toString());
                socketService.sendMessage(data.getRoom(), "get_message", senderClient, data.getMessage());
            }
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {

            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };
    }


    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }


    private DataListener<Message> onChatReceived2() {
        return (senderClient, data, ackSender) -> {
            synchronized(this) {
                log.info(data.toString());
                socketService.sendMessage(data.getRoom(), "get_message_2", senderClient, data.getMessage());
            }
        };
    }


    private ConnectListener onConnected2() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };
    }


    private DisconnectListener onDisconnected2() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}
