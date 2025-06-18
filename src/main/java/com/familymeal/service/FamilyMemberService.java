package com.familymeal.service;

import com.familymeal.entity.FamilyMember;
import com.familymeal.exception.ResourceNotFoundException;
import com.familymeal.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;

    @Autowired
    public FamilyMemberService(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    public FamilyMember addFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    public List<FamilyMember> getFamilyMembersByUserId(Long userId) {
        return familyMemberRepository.findByUserId(userId);
    }

    public Optional<FamilyMember> findById(Long id) {
        return familyMemberRepository.findById(id);
    }

    public FamilyMember updateFamilyMember(FamilyMember familyMember) {
        if (!familyMemberRepository.existsById(familyMember.getId())) {
            throw new ResourceNotFoundException("Family member not found");
        }
        return familyMemberRepository.save(familyMember);
    }

    public void deleteFamilyMember(Long id) {
        if (!familyMemberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Family member not found");
        }
        familyMemberRepository.deleteById(id);
    }
} 