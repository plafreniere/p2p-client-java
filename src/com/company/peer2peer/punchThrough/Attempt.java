package com.company.peer2peer.punchThrough;

import com.company.Main;
import com.company.peer2peer.Peer;
import com.company.peer2peer.Peer2PeerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.UUID;

public class Attempt implements Runnable {
    private InetSocketAddress address;
    private PunchThrough punch;

    public Attempt(PunchThrough punch, InetSocketAddress address) {
        this.address = address;
        this.punch = punch;
    }

    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(address, 100);
            System.out.println("Socket connected on port : " + address.getPort());
            if(socket.isConnected()) {



                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                UUID uuid = punch.getClient().getUuid();

                System.out.println("Attempting connection...");
                out.println(uuid.toString());

                System.out.println("Remote uuid : " + in.readLine());
                Peer peer = punch.getPeer();
                peer.setSocket(socket);
                punch.getClient().addPeer(peer);
                punch.setConnected(true);
            }
        } catch(IOException e) {
//            System.err.println(e);
        }
    }
}
