package com.hodor.service;

import com.hodor.pojo.Tag;
import com.hodor.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/18
 * @description ：
 * @version: 1.0
 */
public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
