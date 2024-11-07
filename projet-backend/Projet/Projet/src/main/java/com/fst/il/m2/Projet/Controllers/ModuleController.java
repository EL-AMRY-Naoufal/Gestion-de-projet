package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.ModuleServiceDefault;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final UserService userService;
    private final ModuleServiceDefault moduleService;

    @Autowired
    public ModuleController(ModuleServiceDefault moduleService, UserService userService) {
        this.moduleService = moduleService;
        this.userService = userService;
    }

    @PutMapping("/{currentUserId}/assignedHours")
    public void modifyAssignedHours(@PathVariable Long currentUserId, @Valid @RequestBody ModuleDto moduleDto) {
        if(!this.userService.getUserRole(currentUserId).equals(Role.ADMINISTRATEUR)) {
            throw new RuntimeException("You do not have the required permissions to make these changes");
        }
        this.moduleService.modifyAssignedHours(moduleDto.getId(), moduleDto.getHeuresParType());
    }
}
