package com.company.peer2peer;

import com.company.peer2peer.punchThrough.PunchThrough;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.UUID;

public class ResponseHandler implements Runnable {
    private BufferedReader in;
    private Peer2PeerClient client;
    public ResponseHandler(BufferedReader in, Peer2PeerClient client) {
        this.in = in;
        this.client = client;
    }

    public void run() {
        try {
            while(true) {
                String line = in.readLine();
                URL url = new URL("http:/" + line);
                InetSocketAddress address = new InetSocketAddress(url.getHost(), url.getPort());
                line = in.readLine();
                url = new URL("http:/" + line);
                InetSocketAddress privateAddress = new InetSocketAddress(url.getHost(), url.getPort());
                line = in.readLine();
                UUID uuid = UUID.fromString(line);

                Peer peer = new Peer(address, privateAddress, uuid);
                new Thread(new PunchThrough(peer,client)).start();
            }
        } catch (IOException e) {
        } finally {
            try {
                in.close();
                System.out.println("Disconnected from server");
                System.exit(2);
            } catch (IOException e) {
                System.err.println(e);
            }

            }
    }
}
