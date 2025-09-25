package com.motofacil.service;

import com.motofacil.entity.Administrador;
import com.motofacil.entity.Funcionario;
import com.motofacil.repository.AdministradorRepository;
import com.motofacil.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdministradorRepository adminRepo;

    @Autowired
    private FuncionarioRepository funcRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login - retorna token (simulado)
    public String login(String email, String senha) {
        Optional<Administrador> adminOpt = adminRepo.findByEmail(email);
        if(adminOpt.isPresent() && passwordEncoder.matches(senha, adminOpt.get().getSenha())) {
            return "jwt-token-exemplo";
        }

        Optional<Funcionario> funcOpt = funcRepo.findByEmail(email);
        if(funcOpt.isPresent() && passwordEncoder.matches(senha, funcOpt.get().getSenha())) {
            return "jwt-token-exemplo";
        }

        return null; // usuário ou senha inválidos
    }

    // Cadastro Admin
    public void registerAdmin(Administrador admin) throws Exception {
        if(adminRepo.existsByEmail(admin.getEmail())) {
            throw new Exception("Email já cadastrado!");
        }
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        adminRepo.save(admin);
    }

    // Cadastro Funcionario
    public void registerFuncionario(Funcionario func) throws Exception {
        if(funcRepo.existsByEmail(func.getEmail())) {
            throw new Exception("Email já cadastrado!");
        }
        func.setSenha(passwordEncoder.encode(func.getSenha()));
        funcRepo.save(func);
    }
}
