package com.rm;

import com.rm.entity.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    void testDynamic() {
        User user = new User();
        Proxy.newProxyInstance(user.getClass().getClassLoader(), user.getClass().getInterfaces(),
                (proxy, method, args) -> method.invoke(user, args));
    }
}
