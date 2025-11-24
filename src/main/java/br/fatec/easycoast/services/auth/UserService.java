package br.fatec.easycoast.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.repositories.CustomerRepository;
import br.fatec.easycoast.repositories.EmployeeRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return employeeRepository.findByLogin(login) 
               .map(UserDetails.class::cast) 
                //If not found, tries with Customers
               .or(() -> customerRepository.findByEmail(login).map(UserDetails.class::cast)) 
               .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
    }
}
