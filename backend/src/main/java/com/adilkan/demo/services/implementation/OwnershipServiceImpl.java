package com.adilkan.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.OwnershipDTO;
import com.adilkan.demo.entities.Ownership;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.mappers.OwnershipMapper;
import com.adilkan.demo.repositories.OwnershipRepository;
import com.adilkan.demo.repositories.ProductRepository;
import com.adilkan.demo.repositories.UserRepository;
import com.adilkan.demo.services.OwnershipService;

@Service
public class OwnershipServiceImpl implements OwnershipService {

    @Autowired
    private OwnershipRepository ownershipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Ownership createOwnership(OwnershipDTO ownershipDTO) {
        if (ownershipDTO == null) {
            throw new IllegalArgumentException("OwnershipDTO cannot be null.");
        }

        List<User> users = new ArrayList<>();
        if (ownershipDTO.getUserIds() != null) {
            for (Long userId : ownershipDTO.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
        }

        List<Product> products = new ArrayList<>();
        if (ownershipDTO.getProductIds() != null) {
            for (Long productId : ownershipDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        Ownership ownership = OwnershipMapper.toEntity(ownershipDTO);
        ownership.setUser(users);
        ownership.setProduct(products);
        return ownershipRepository.save(ownership);
    }

    @Override
    public Ownership getOwnershipById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Ownership ID cannot be null.");
        }

        return ownershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ownership not found with id: " + id));
    }

    @Override
    public List<Ownership> getAllOwnerships() {
        List<Ownership> ownerships = ownershipRepository.findAll();
        if (ownerships.isEmpty()) {
            throw new ResourceNotFoundException("No ownerships found.");
        }
        return ownerships;
    }

    @Override
    public Ownership updateOwnership(Long id, OwnershipDTO ownershipDTO) {
        if (id == null || ownershipDTO == null) {
            throw new IllegalArgumentException("Ownership ID and OwnershipDTO cannot be null.");
        }

        Ownership existingOwnership = getOwnershipById(id);

        List<User> users = new ArrayList<>();
        if (ownershipDTO.getUserIds() != null) {
            for (Long userId : ownershipDTO.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
        }

        List<Product> products = new ArrayList<>();
        if (ownershipDTO.getProductIds() != null) {
            for (Long productId : ownershipDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        existingOwnership.setUser(users);
        existingOwnership.setProduct(products);
        return ownershipRepository.save(existingOwnership);
    }

    @Override
    public void deleteOwnership(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Ownership ID cannot be null.");
        }

        if (!ownershipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ownership not found with id: " + id);
        }

        ownershipRepository.deleteById(id);
    }
}
