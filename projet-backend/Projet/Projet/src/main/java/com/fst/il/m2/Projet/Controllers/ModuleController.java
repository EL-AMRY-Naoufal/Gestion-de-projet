package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.ModuleService;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final UserService userService;
    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService, UserService userService) {
        this.moduleService = moduleService;
        this.userService = userService;
    }
}
