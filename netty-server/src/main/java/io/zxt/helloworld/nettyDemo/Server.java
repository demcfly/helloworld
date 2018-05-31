package io.zxt.helloworld.nettyDemo;

public interface Server {

    public void startService(EndpointConfig endpointConfig) throws Exception;

    public void stopService();

    public String inspectStatistics() throws Exception;

}
