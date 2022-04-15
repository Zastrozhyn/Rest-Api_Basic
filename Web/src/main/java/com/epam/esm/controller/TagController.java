package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Tag tag) {
        tagService.create(tag);
    }

    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable Long id) {
        return tagService.findTag(id);
    }

    @DeleteMapping(value = "/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "tagId") Long id) {
        tagService.delete(id);
    }
}
