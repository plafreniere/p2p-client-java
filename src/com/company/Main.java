package com.company;

import com.company.peer2peer.Peer2PeerClient;
import com.company.peer2peer.punchThrough.PunchThrough;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    private static String relayHost = "127.0.0.1";
    private static int port = 6666;


    public static void main(String[] args) {
        if(args.length > 0) {
            relayHost = args[0];
        }
        if(args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        Peer2PeerClient client = new Peer2PeerClient(relayHost, port);
        client.connect();

        while(client.getPeers().isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("we have a client connection!");
    }

}

