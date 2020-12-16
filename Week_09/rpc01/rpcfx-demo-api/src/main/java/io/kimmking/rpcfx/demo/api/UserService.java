package io.kimmking.rpcfx.demo.api;

public interface UserService {

    User findById(int id);
    User findByIdAndName();
    User findByIdAndName(Integer id,String name);
    User findByIdAndName(int id,String name);

}
