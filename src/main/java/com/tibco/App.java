package com.tibco;

import java.util.Arrays;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

public class App {

  public static void main(String[] args) {
    TcpDiscoverySpi spi = new TcpDiscoverySpi();
    TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
    ipFinder.setAddresses(Arrays.asList(args));
    spi.setIpFinder(ipFinder);

    IgniteConfiguration configuration = new IgniteConfiguration();
    configuration.setDiscoverySpi(spi);

    Ignite ignite = Ignition.start(configuration);
    IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
    for (int i = 0; i < 10; ++i) {
      cache.put(i, Integer.toString(i));
    }

    for (int i = 0; i < 10; ++i) {
      System.out.println(cache.get(i));
    }

  }
}
