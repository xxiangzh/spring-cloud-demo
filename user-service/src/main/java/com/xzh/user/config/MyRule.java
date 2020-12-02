package com.xzh.user.config;

import cn.hutool.core.util.ReflectUtil;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 向振华
 * @date 2020/12/02 15:41
 */
public class MyRule extends AbstractLoadBalancerRule {

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;
            while (server == null) {
                if (Thread.interrupted()) {
                    return null;
                }
                List<Server> upList = lb.getReachableServers();
                List<Server> allList = lb.getAllServers();
                if (allList.size() == 0) {
                    return null;
                }
                List<Server> availableList = availableList(upList);
                if (availableList.size() == 0) {
                    return null;
                }
                int index = ThreadLocalRandom.current().nextInt(availableList.size());
                server = availableList.get(index);
                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }
                    server = null;
                    Thread.yield();
                }
            }
            return server;
        }
    }

    private List<Server> availableList(List<Server> upList) {
        // 请求者的tag
        String tag = "xzh";
        List<Server> availableList = new ArrayList<>();
        for (Server up : upList) {
            InstanceInfo instanceInfo = (InstanceInfo) ReflectUtil.getFieldValue(up, "instanceInfo");
            Map<String, String> metadata = instanceInfo.getMetadata();
            String routingTag = metadata.get("routingTag");
            if (tag.equals(routingTag)) {
                availableList.add(up);
            }
        }
        return availableList;
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
