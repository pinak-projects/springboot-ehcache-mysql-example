package com.pinakjakhr.ehcache.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@EnableCaching
@Configuration
public class EhCacheConfig {

	 @Autowired
     private ResourceLoader resourceLoader;

     @Bean
     public EhCacheCacheManager cacheManager() {
         EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
         try {
             ehCacheCacheManager.setCacheManager(ehcacheCacheManager().getObject());
         } catch (Exception e) {
             throw new IllegalStateException("Failed to create an EhCacheManagerFactoryBean", e);
         }
         return ehCacheCacheManager;
     }

     @Bean
     public EhCacheManagerFactoryBean ehcacheCacheManager() {
         EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
         bean.setConfigLocation(resourceLoader.getResource("classpath:ehcache.xml"));
         return bean;
     }
}
