package com.company.peer2peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Peer2PeerClient {

    private String serverHost;
    private int serverPort;
    private Socket socket;
    ArrayList<Peer> peers = new ArrayList<>();

    private UUID uuid;

    public Peer2PeerClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverHost, serverPort);

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            Runtime.getRuntime().addShutdownHook(
                    new Thread() {
                        public void run() {
                            System.out.println("Notifying the server");
                            out.println("exit");
                        }
                    });
            System.out.println("Connected to server using " + socket.getLocalAddress().getHostAddress() + " : " + socket.getLocalPort());
            out.println(socket.getLocalSocketAddress());
            UUID uuid = UUID.fromString(in.readLine());
            System.out.println("My uuid : " + uuid.toString());
            this.uuid = uuid;
            new Thread(new ResponseHandler(in, this)).start();
            while (socket.isConnected()) {
                Thread.sleep(100);
            }
        } catch(Exception e) {
            System.out.println("Could not connect, relay off?");
            System.exit(1);
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void addPeer(Peer peer) {
        peers.add(peer);
    }

    public void removePeer(Peer peer) {

    }

    public List<Peer> getPeers() {
        return this.peers;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Socket getSocket() {
        return socket;
    }

    public UUID getUuid() {
        return uuid;
    }
}
