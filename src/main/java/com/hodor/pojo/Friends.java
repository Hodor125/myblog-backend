package com.hodor.pojo;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/29
 * @description ：
 * @version: 1.0
 */
@Entity
@Table(name="t_friend")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "头像不能为空")
    private String avatar;
    @NotBlank(message = "昵称不能为空")
    private String name;
    @NotBlank(message = "链接不能为空")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
