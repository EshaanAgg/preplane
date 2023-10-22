package com.preplane.dev.controllers.backend;

import com.preplane.dev.models.Tag;
import com.preplane.dev.repositories.Tag.JDBCTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private JDBCTagRepository tagRepository;

    @GetMapping("/")
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset) {

        try {
            int lim = limit.orElse(50);
            int off = offset.orElse(0);

            var response = tagRepository.findAll(lim, off);
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
