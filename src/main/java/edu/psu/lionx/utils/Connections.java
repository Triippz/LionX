package edu.psu.lionx.utils;

import org.stellar.sdk.Server;


public class Connections
{
    public static final String MAIN_NET = "https://horizon.stellar.org";
    public static final String TEST_NET = "https://horizon-testnet.stellar.org";

    public static Server getServer (boolean isMainNet )
    {
        Server server;
        if ( isMainNet )
        {
            /* Connect to the mainnet */
            server = new Server(MAIN_NET);
        } else {
            /* Connect to the testnet */
            server = new Server ( TEST_NET );
        }

        return server;
    }
}