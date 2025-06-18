package com.familymeal.controller;

import com.familymeal.dto.ApiResponse;
import com.familymeal.dto.FamilyMemberDTO;
import com.familymeal.entity.FamilyMember;
import com.familymeal.entity.User;
import com.familymeal.service.FamilyMemberService;
import com.familymeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/family-members")
public class FamilyMemberController {
    private final FamilyMemberService familyMemberService;
    private final UserService userService;

    @Autowired
    public FamilyMemberController(FamilyMemberService familyMemberService, UserService userService) {
        this.familyMemberService = familyMemberService;
        this.userService = userService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<FamilyMember>> addFamilyMember(
            @PathVariable Long userId,
            @Valid @RequestBody FamilyMemberDTO familyMemberDTO) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FamilyMember familyMember = new FamilyMember();
        familyMember.setName(familyMemberDTO.getName());
        familyMember.setRelationship(familyMemberDTO.getRelationship());
        familyMember.setDietaryRestrictions(familyMemberDTO.getDietaryRestrictions());
        familyMember.setUser(user);

        FamilyMember savedMember = familyMemberService.addFamilyMember(familyMember);
        return ResponseEntity.ok(ApiResponse.success("Family member added successfully", savedMember));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FamilyMember>>> getFamilyMembersByUserId(@PathVariable Long userId) {
        List<FamilyMember> members = familyMemberService.getFamilyMembersByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(members));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FamilyMember>> getFamilyMemberById(@PathVariable Long id) {
        return familyMemberService.findById(id)
                .map(member -> ResponseEntity.ok(ApiResponse.success(member)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Family member not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FamilyMember>> updateFamilyMember(
            @PathVariable Long id,
            @Valid @RequestBody FamilyMemberDTO familyMemberDTO) {
        FamilyMember familyMember = familyMemberService.findById(id)
                .orElseThrow(() -> new RuntimeException("Family member not found"));

        familyMember.setName(familyMemberDTO.getName());
        familyMember.setRelationship(familyMemberDTO.getRelationship());
        familyMember.setDietaryRestrictions(familyMemberDTO.getDietaryRestrictions());

        FamilyMember updatedMember = familyMemberService.updateFamilyMember(familyMember);
        return ResponseEntity.ok(ApiResponse.success("Family member updated successfully", updatedMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFamilyMember(@PathVariable Long id) {
        familyMemberService.deleteFamilyMember(id);
        return ResponseEntity.ok(ApiResponse.success("Family member deleted successfully", null));
    }
} 