package com.creact.steve.retrofitsample;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Repo {

    public long id;
    public String name;

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
