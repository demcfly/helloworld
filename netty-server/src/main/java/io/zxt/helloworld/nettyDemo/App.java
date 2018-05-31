package io.zxt.helloworld.nettyDemo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        EndpointConfig endpointConfig = new EndpointConfig();
        endpointConfig.setHost("localhost");
        endpointConfig.setPort(8099);
        Server server = new NettyServer();
        try {
            server.startService(endpointConfig);
        } catch(final Exception ex) {
            ex.printStackTrace();
        }
    }

}
