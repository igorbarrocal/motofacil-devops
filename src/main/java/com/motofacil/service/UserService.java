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
public class UserService {

    @Autowired
    private AdministradorRepository adminRepo;

    @Autowired
    private FuncionarioRepository funcRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Buscar Admin por email
    public Optional<Administrador> getAdminByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    // Buscar Funcionario por email
    public Optional<Funcionario> getFuncionarioByEmail(String email) {
        return funcRepo.findByEmail(email);
    }

    // Atualizar email Admin
    public void updateAdminEmail(String emailAtual, String novoEmail) throws Exception {
        Administrador admin = adminRepo.findByEmail(emailAtual)
                .orElseThrow(() -> new Exception("Administrador não encontrado"));
        admin.setEmail(novoEmail);
        adminRepo.save(admin);
    }

    // Atualizar senha Admin
    public void updateAdminSenha(String email, String novaSenha) throws Exception {
        Administrador admin = adminRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("Administrador não encontrado"));
        admin.setSenha(passwordEncoder.encode(novaSenha));
        adminRepo.save(admin);
    }

    // Atualizar email Funcionario
    public void updateFuncionarioEmail(String emailAtual, String novoEmail) throws Exception {
        Funcionario func = funcRepo.findByEmail(emailAtual)
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));
        func.setEmail(novoEmail);
        funcRepo.save(func);
    }

    // Atualizar senha Funcionario
    public void updateFuncionarioSenha(String email, String novaSenha) throws Exception {
        Funcionario func = funcRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));
        func.setSenha(passwordEncoder.encode(novaSenha));
        funcRepo.save(func);
    }
}
