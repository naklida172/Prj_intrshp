package com.adilkan.demo.services;

import java.util.List;

import com.adilkan.demo.dtos.OwnershipDTO;
import com.adilkan.demo.entities.Ownership;

public interface OwnershipService {
    Ownership createOwnership(OwnershipDTO ownershipDTO);
    Ownership getOwnershipById(Long id);
    List<Ownership> getAllOwnerships();
    Ownership updateOwnership(Long id, OwnershipDTO ownershipDTO);
    void deleteOwnership(Long id);
}
