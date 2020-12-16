package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }

    @Override
    public User findByIdAndName() {
        return new User(1111, "no id, hello no params" + System.currentTimeMillis());
    }

    @Override
    public User findByIdAndName(Integer id, String name) {
        return new User(id, "Integer id, "+name + System.currentTimeMillis());
    }

    @Override
    public User findByIdAndName(int id, String name) {
        return new User(id, "int id"+name + System.currentTimeMillis());
    }

}


