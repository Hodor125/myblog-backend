package com.hodor.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 * @author ：hodor007
 * @date ：Created in 2021/1/13
 * @description ：
 * @version: 1.0
 */
@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //被维护
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}