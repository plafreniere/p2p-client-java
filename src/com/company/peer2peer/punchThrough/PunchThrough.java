package com.company.peer2peer.punchThrough;

import com.company.peer2peer.Peer;
import com.company.peer2peer.Peer2PeerClient;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;

public class PunchThrough implements Runnable {

    private Peer peer;
    private final int portsToTest = 20;
    private boolean connected = false;
    private final Peer2PeerClient client;

    public PunchThrough(Peer peer, Peer2PeerClient client) {
        this.peer = peer;
        this.client = client;
    }

    public void run() {
        InetSocketAddress address = peer.getAddress();
        InetSocketAddress privateAddress = peer.getPrivateAddress();

        System.out.println("Connecting to : " +  address.getHostName() + ":" + address.getPort());
        System.out.println("From " + (address.getPort()) + " to " + (address.getPort() + portsToTest));

        for(int test = portsToTest; test > 0; test--) {
            InetSocketAddress tempAddr = new InetSocketAddress(address.getHostName(), (address.getPort() + test));
            try {
                new Thread(new Attempt(this, tempAddr)).start();

                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(connected) {
                break;
            }
        }

        if(!isConnected()) {
            System.out.println("Punchthrough failed for peer " + peer.getAddress().getHostString());
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Peer2PeerClient getClient() {
        return client;
    }

    public Peer getPeer() {
        return peer;
    }
}
