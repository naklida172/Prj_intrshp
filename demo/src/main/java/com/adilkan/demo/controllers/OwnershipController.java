package com.adilkan.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.adilkan.demo.dtos.OwnershipDTO;
import com.adilkan.demo.entities.Ownership;
import com.adilkan.demo.services.OwnershipService;

@EnableWebMvc
@RestController
@RequestMapping("/api/ownerships")
public class OwnershipController {

    @Autowired
    private OwnershipService ownershipService;

    @PostMapping
    public Ownership createOwnership(@RequestBody OwnershipDTO ownershipDTO) {
        return ownershipService.createOwnership(ownershipDTO);
    }

    @GetMapping("/{id}")
    public Ownership getOwnershipById(@PathVariable Long id) {
        return ownershipService.getOwnershipById(id);
    }

    @GetMapping
    public List<Ownership> getAllOwnerships() {
        return ownershipService.getAllOwnerships();
    }

    @PutMapping("/{id}")
    public Ownership updateOwnership(@PathVariable Long id, @RequestBody OwnershipDTO ownershipDTO) {
        return ownershipService.updateOwnership(id, ownershipDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOwnership(@PathVariable Long id) {
        ownershipService.deleteOwnership(id);
    }
}
