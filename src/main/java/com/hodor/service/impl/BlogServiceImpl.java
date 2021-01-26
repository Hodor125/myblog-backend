package com.hodor.service.impl;

import com.hodor.dao.BlogRepository;
import com.hodor.exception.NotFoundException;
import com.hodor.pojo.Blog;
import com.hodor.pojo.Type;
import com.hodor.service.BlogService;
import com.hodor.util.MarkdownUtils;
import com.hodor.util.MyBeanUtils;
import com.hodor.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * 博客service层
 * @author ：hodor007
 * @date ：Created in 2021/1/19
 * @description ：
 * @version: 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 查询博客并且将content格式转为html
     * @return
     */
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        //处理content的格式
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    /**
     * 分页和条件查询
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * 条件查询
             * @param root 获取表的字段，属性值等等
             * @param criteriaQuery 查询的条件
             * @param criteriaBuilder 设置具体条件，模糊查询等等
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //条件封装
                List<Predicate> predicates = new ArrayList<>();
                if(blog.getTitle()!= null && !"".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if(blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommed"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listPage(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 查询带对应标签下的所有博客，查询两张表
     * @param tagId
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    /**
     * 查找各个年份的博客
     * @return
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    /**
     * 统计博客的数目
     * @return
     */
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    //统计博客浏览次数
    @Override
    public Long countView() {
        return Long.valueOf(blogRepository.countView());
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 根据title和content搜索博客
     * @param query
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null) {
            //初始化创建时间和更新时间和浏览次数
            blog.setCreateTimel(new Date());
            blog.setUpdateTime(new Date());
            blog.setView(0);
            return blogRepository.save(blog);
        } else {
            Blog old = blogRepository.getOne(blog.getId());
            if(blog == null) {
                throw new NotFoundException("该博客不存在");
            }
            //排除空的属性
            BeanUtils.copyProperties(blog, old, MyBeanUtils.getNullPropertyNames(blog));
            old.setUpdateTime(new Date());
            return blogRepository.save(old);
        }
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateViews(Long id) {
        blogRepository.updateViews(id);
    }

}
