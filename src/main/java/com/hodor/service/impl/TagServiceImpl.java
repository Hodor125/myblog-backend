package com.hodor.service.impl;

import com.hodor.dao.TagRepository;
import com.hodor.exception.NotFoundException;
import com.hodor.pojo.Tag;
import com.hodor.pojo.Type;
import com.hodor.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签service层
 * @author ：hodor007
 * @date ：Created in 2021/1/18
 * @description ：
 * @version: 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        //传入的字符串 1,2,3
        return tagRepository.findAllById(convertLong(ids));
    }

    private List<Long> convertLong(String ids) {
        List<Long> list = new ArrayList<>();
        if(ids != null && !"".equals(ids)) {
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                list.add(new Long(s));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if(t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
