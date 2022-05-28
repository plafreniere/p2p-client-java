package com.company.peer2peer;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.UUID;

public class Peer {
    UUID uuid;
    InetSocketAddress address;
    InetSocketAddress privateAddress;
    Socket socket;

    public Peer(Socket socket) {
        this.socket = socket;
        this.uuid = UUID.randomUUID();
    }
    public Peer(InetSocketAddress address, InetSocketAddress privateAddress, UUID uuid) {
        this.address = address;
        this.privateAddress = privateAddress;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public InetSocketAddress getPrivateAddress() {
        return privateAddress;
    }

    public void setPrivateAddress(InetSocketAddress privateAddress) {
        this.privateAddress = privateAddress;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
