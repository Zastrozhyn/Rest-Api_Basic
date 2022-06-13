package com.epam.esm.controller;

import com.epam.esm.converter.impl.TagModelAssembler;
import com.epam.esm.dto.TagModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.impl.TagLinkBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagLinkBuilder linkBuilder;
    private final TagModelAssembler assembler;

    @Autowired
    public TagController(TagService tagService, TagLinkBuilder linkBuilder, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
        this.assembler = assembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagModel create(@RequestBody Tag tag) {
        TagModel tagModel = assembler.toModel(tagService.create(tag));
        linkBuilder.buildLinks(tagModel);
        return tagModel;
    }

    @GetMapping
    public CollectionModel<TagModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                             @RequestParam(required = false, defaultValue = "1", name = "page") Integer page) {
        CollectionModel<TagModel> tags = assembler.toCollectionModel(tagService.findAll(pageSize, page));
        tags.forEach(linkBuilder::buildSelfLink);
        linkBuilder.buildAllLinks(tags);
        return tags;
    }

    @GetMapping("/{id}")
    public TagModel findById(@PathVariable Long id) {
        TagModel tag = assembler.toModel(tagService.findTag(id));
        linkBuilder.buildLinks(tag);
        return tag;
    }

    @DeleteMapping(value = "/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "tagId") Long id) {
        tagService.delete(id);
    }
}
