package edu.fudanselab.trainticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.fudanselab.trainticket.service.ServiceResolver;

@Service
public class ServiceResolverImpl implements ServiceResolver {

    @Autowired
    Environment environment;

    @Override
    public String getServiceUrl(String serviceName) {
        return "http://localhost:" + environment.getProperty("local.server.port");
    }
}
