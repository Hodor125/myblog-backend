package com.hodor.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客
 * @author ：hodor007
 * @date ：Created in 2021/1/13
 * @description ：
 * @version: 1.0
 */
@Entity    //和数据库对应
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //自动生成
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;    //是否原创
    private Integer view;    //浏览次数
    private boolean appreciation;    //赞赏是否开启
    private boolean shareStatement;    //转载声明是否开启
    private boolean commentable;    //评论是否开启
    private boolean published;    //是否发布
    private boolean recommed;    //是否推荐
    @Temporal(TemporalType.TIMESTAMP)    //包括日期和事件
    private Date createTimel;    //创建事件
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;    //更新事件

    //处理表之间的关系
    //博客和类型是多对一，多的一方是关系的维护方
    @ManyToOne
    private Type type;

    //级联新增，新增博客中添加了新的标签会被保存到数据库
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommed() {
        return recommed;
    }

    public void setRecommed(boolean recommed) {
        this.recommed = recommed;
    }

    public Date getCreateTimel() {
        return createTimel;
    }

    public void setCreateTimel(Date createTimel) {
        this.createTimel = createTimel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", view=" + view +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommed=" + recommed +
                ", createTimel=" + createTimel +
                ", updateTime=" + updateTime +
                '}';
    }
}
