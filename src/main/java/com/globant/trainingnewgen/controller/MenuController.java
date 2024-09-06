package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.factory.MenuFactory;
import com.globant.trainingnewgen.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuFactory menuFactory;
    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping
    public ResponseEntity<byte[]> getMenu(HttpServletRequest request) {
        String contentType = request.getHeader("Content-Type");
        logger.info("Requesting menu with Content-Type: {}", contentType);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        MenuService menuService = menuFactory.getMenuService(contentType);
        menuService.generateDocument(outputStream);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(outputStream.toByteArray());
    }
}