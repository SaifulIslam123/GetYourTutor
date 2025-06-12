package com.getyourtutor.security;

import com.getyourtutor.domain.entity.Privilege;
import com.getyourtutor.domain.entity.Role;
import com.getyourtutor.repository.PrivilegeRepository;
import com.getyourtutor.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name).orElseGet(() -> privilegeRepository.save(new Privilege(name)));
    }

    @Transactional
    private void createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role(name);
            role.setPrivileges(privileges);
            return roleRepository.save(role);
        });
    }
}
