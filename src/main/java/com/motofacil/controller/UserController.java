package com.motofacil.controller;

import com.motofacil.entity.Administrador;
import com.motofacil.entity.Funcionario;
import com.motofacil.repository.AdministradorRepository;
import com.motofacil.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AdministradorRepository adminRepo;

    @Autowired
    private FuncionarioRepository funcRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me/{email}")
    public ResponseEntity<?> getUsuario(@PathVariable String email) {
        Optional<Administrador> admin = adminRepo.findByEmail(email);
        if(admin.isPresent()) return ResponseEntity.ok(admin.get());

        Optional<Funcionario> func = funcRepo.findByEmail(email);
        if(func.isPresent()) return ResponseEntity.ok(func.get());

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update-nome")
    public ResponseEntity<?> updateNome(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String novoNome = body.get("nome");

        Optional<Administrador> admin = adminRepo.findByEmail(email);
        if(admin.isPresent()){
            admin.get().setNome(novoNome);
            adminRepo.save(admin.get());
            return ResponseEntity.ok("Nome atualizado");
        }

        Optional<Funcionario> func = funcRepo.findByEmail(email);
        if(func.isPresent()){
            func.get().setNome(novoNome);
            funcRepo.save(func.get());
            return ResponseEntity.ok("Nome atualizado");
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    @PutMapping("/update-email")
    public ResponseEntity<?> updateEmail(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String novoEmail = body.get("emailNovo");

        Optional<Administrador> admin = adminRepo.findByEmail(email);
        if(admin.isPresent()){
            admin.get().setEmail(novoEmail);
            adminRepo.save(admin.get());
            return ResponseEntity.ok("Email atualizado");
        }

        Optional<Funcionario> func = funcRepo.findByEmail(email);
        if(func.isPresent()){
            func.get().setEmail(novoEmail);
            funcRepo.save(func.get());
            return ResponseEntity.ok("Email atualizado");
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    @PutMapping("/update-senha")
    public ResponseEntity<?> updateSenha(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String senhaAntiga = body.get("senhaAntiga");
        String novaSenha = body.get("novaSenha");

        Optional<Administrador> admin = adminRepo.findByEmail(email);
        if(admin.isPresent()){
            if(!passwordEncoder.matches(senhaAntiga, admin.get().getSenha()))
                return ResponseEntity.badRequest().body("Senha antiga incorreta");
            admin.get().setSenha(passwordEncoder.encode(novaSenha));
            adminRepo.save(admin.get());
            return ResponseEntity.ok("Senha atualizada");
        }

        Optional<Funcionario> func = funcRepo.findByEmail(email);
        if(func.isPresent()){
            if(!passwordEncoder.matches(senhaAntiga, func.get().getSenha()))
                return ResponseEntity.badRequest().body("Senha antiga incorreta");
            func.get().setSenha(passwordEncoder.encode(novaSenha));
            funcRepo.save(func.get());
            return ResponseEntity.ok("Senha atualizada");
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }
}
