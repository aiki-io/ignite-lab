package com.tibco;

import java.io.IOException;
import java.util.Arrays;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

public class App {

  private static final String CACHE_NAME = "myCache";

  public static void main(String[] args) throws IOException {
    {
      TcpDiscoverySpi spi = new TcpDiscoverySpi();
      spi.setLocalPort(48500);
      TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

      ipFinder.setAddresses(Arrays.asList(args));

      spi.setIpFinder(ipFinder);


      IgniteConfiguration clusterConfig = new IgniteConfiguration();
      CacheConfiguration cacheConfig = new CacheConfiguration();
      clusterConfig.setDiscoverySpi(spi);
      cacheConfig.setCacheMode(CacheMode.REPLICATED);
      clusterConfig.setCacheConfiguration(cacheConfig);

      Ignite ignite = Ignition.start(clusterConfig);
      IgniteCache<String, String> cache = ignite.getOrCreateCache(CACHE_NAME);

      cache.put("Hello", "World");

      IgniteCache<String, String> c = ignite.getOrCreateCache(CACHE_NAME);
      System.out.println(c.get("Hello"));
      System.in.read();
    }
  }
}
