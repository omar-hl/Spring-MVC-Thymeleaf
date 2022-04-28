package com.example.patientsmvc;

import com.example.patientsmvc.entities.patient;
import com.example.patientsmvc.repositories.patientRepo;
import com.example.patientsmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientsMvcApplication.class, args);}
        @Bean
        PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

   // @Bean
CommandLineRunner commandLineRunner(patientRepo patientRepo){
        return args -> {
patientRepo.save(new patient(null,"omar",new Date(),false,15));
            patientRepo.save(new patient(null,"mohamed",new Date(),false,11));
            patientRepo.save(new patient(null,"ghita",new Date(),true,15));
            patientRepo.save(new patient(null,"sara",new Date(),true,20));
       patientRepo.findAll().forEach(p ->{
           System.out.println(p.getNom());
       });
        };
}
@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("user1", "omar", "omar");
            securityService.saveNewUser("omar", "omar", "omar");


            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");


            securityService.addRoleToUser("mohamed", "ADMIN");
            securityService.addRoleToUser("mohamed", "USER");
            securityService.addRoleToUser("user1", "USER");

            securityService.saveNewUser("admin", "bouzri", "bouzri");

            securityService.addRoleToUser("admin", "ADMIN");
            securityService.addRoleToUser("admin", "USER");
        };
};

}
