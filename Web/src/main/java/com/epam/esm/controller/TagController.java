package com.epam.esm.controller;

import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.exception.model.TagModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
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
    private final TagModelAssembler assembler;

    @Autowired
    public TagController(TagService tagService, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagModel create(@RequestBody Tag tag) {
        return assembler.toModelWithLinks(tagService.create(tag));
    }

    @GetMapping
    public CollectionModel<TagModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                               @RequestParam(required = false, defaultValue = "1", name = "page") Integer page) {
        return assembler.toCollectionModel(tagService.findAll(pageSize, page));
    }

    @GetMapping("/{id}")
    public TagModel findById(@PathVariable Long id) {
        return assembler.toModelWithLinks(tagService.findById(id));
    }

    @DeleteMapping(value = "/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "tagId") Long id) {
        tagService.delete(id);
    }
}
