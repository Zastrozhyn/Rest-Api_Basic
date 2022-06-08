package com.epam.esm.controller;

import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.impl.TagLinkBuilder;
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
    private final TagLinkBuilder linkBuilder;

    @Autowired
    public TagController(TagService tagService, TagLinkBuilder linkBuilder) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tag) {
        return tagService.create(tag);
    }

    @GetMapping
    public CustomPage<TagDto> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                   @RequestParam(required = false, defaultValue = "1", name = "page") Integer page) {
        List<TagDto> tags = tagService.findAll(pageSize, page);
        tags.forEach(linkBuilder::buildLinks);
        CustomPage<TagDto> customPage = new CustomPage<>(tags, page,pageSize);
        linkBuilder.buildAllLinks(customPage);
        return customPage;
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        TagDto tag = tagService.findTag(id);
        linkBuilder.buildLinks(tag);
        return tag;
    }

    @DeleteMapping(value = "/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "tagId") Long id) {
        tagService.delete(id);
    }
}
