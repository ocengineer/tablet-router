package com.murrow.support;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import android.app.Activity;

public class NetworkConstants {
	/*
	 * Most of the variables are static and final. Some are only static since they are defined at run time.
	 */
	public static String IP_ADDRESS;		// the IP address of this system will be stored here in dotted decimal notation
	public static String IP_ADDRESS_PREFIX; // the prefix will be stored here

    //LL2P Stuff
    public static final String MY_LL2P_ADDR = "FACADE";
    public static final String TYPE_LL3P = "8001";
    public static final String TYPE_ARP = "8002";
    public static final String TYPE_LRP = "8003";
    public static final int LL2P_ADDR_LENGTH = 3;
    public static final int CRC_LENGTH = 2;
    public static final int LL2P_TYPE_LENGTH = 2;


    //  SOUNDS
	public static final int badPacketSound = 1;
	public static final int packetSentSound = 2;
	public static final int buttonPressSound = 3;
	public static final int receiveMessageSound = 4;
	public static final int sendMessageSound = 5;
	public static final int alertSound = 6;
	
	public NetworkConstants (Activity parentActivity){
		//IP_ADDRESS = this.getIPAddress(true);
		IP_ADDRESS = getLocalIpAddress(); // call the local method to get the IP address of this device.
		// This next part is here to be used later if you're working on many devices. You can build an "if" statement to 
		//   dynamically set your LL2P and LL3P addresses.
	    //String androidId = "" + android.provider.Settings.Secure.getString(parentActivity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	    int lastDot = IP_ADDRESS.lastIndexOf(".");
	    int secondDot = IP_ADDRESS.substring(0, lastDot-1).lastIndexOf(".");
	    IP_ADDRESS_PREFIX = IP_ADDRESS.substring(0, secondDot+1);
	}


        /**
         * getLocalIPAddress - this function goes through the network interfaces, looking for one that has a valid IP address.
         * Care must be taken to avoid a loopback address.
         * @return - a string containing the IP address in dotted decimal notation.
         */
        public String getLocalIpAddress() {
            String address= null;
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
            return null;
        }

}
