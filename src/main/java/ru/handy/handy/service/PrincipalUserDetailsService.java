package ru.handy.handy.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.repository.PrincipalRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PrincipalUserDetailsService implements UserDetailsService {

    private final PrincipalRepository principalRepository;

    public PrincipalUserDetailsService(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public PrincipalEntity findPrincipalEntityById(Long id){
        return principalRepository.findPrincipalEntityById(id);
    }

    @Transactional
    @ReadOnlyProperty
    public List<PrincipalEntity> findAllByRoleExpert(String role){
        return principalRepository.findAllByRole(role);
    }

    @Transactional
    @ReadOnlyProperty
    public List<String> findAllExpertNames(){
        List<String> allExperts = new ArrayList<>();

        findAllByRoleExpert("EXPERT").stream()
                .forEach(principalEntity -> allExperts.add(principalEntity.getName()));
        return allExperts;
    }
    @Transactional
    @ReadOnlyProperty
    public PrincipalEntity findPrincipalEntityByRealName(String name){
        return principalRepository.findPrincipalEntityByName(name);
    }

    @Transactional
    @ReadOnlyProperty
    public PrincipalEntity findPrincipalEntityByUsername(String username){
        return principalRepository.findPrincipalEntityByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PrincipalEntity principal = principalRepository.findPrincipalEntityByUsername(username);

        return new User(
                principal.getUsername(),
                principal.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + principal.getRole()))
        );
    }

    @Transactional
    @ReadOnlyProperty
    public Map<String, Long> findAllOfExpertNamesAndIdInfo(){

        Map<String, Long> allExpertsList = new HashMap<>();

        findAllExpertNames().stream()
                .forEach(expert -> {
                    Long id = principalRepository.findPrincipalEntityByName(expert).getId();
                    allExpertsList.put(expert, id);
                });

        return allExpertsList;
    }
}
