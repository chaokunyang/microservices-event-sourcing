package com.timeyang;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author chaokunyang
 */
@Component
@ConfigurationProperties(prefix = "spring.neo4j")
public class Neo4jProperties {
    private String host;
    private int port;
    private String password;
    private String username;
    private String uri;

    @PostConstruct
    public void setup() {
        Assert.hasText(this.host, "需要提供主机名");
        Assert.isTrue(this.port > 0, "需要提供端口"); // 基础int默认值为0

        if(!StringUtils.hasText(this.uri)) {
            this.uri = String.format("http://%s:%s@%s:%s", // http://user:password@localhost:7474
                    getUsername(), getPassword(), getHost(), getPort());
        }
        LogFactory.getLog(getClass()).info(String.format("host=%s, port=%s, uri=%s", getHost(), getPort(), getUri()));
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}