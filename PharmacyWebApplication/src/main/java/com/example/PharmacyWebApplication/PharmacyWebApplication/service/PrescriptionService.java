package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.configuration.JwtRequestFilter;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.UserDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.PrescriptionDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Prescription;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileService fileService;

    public void createPrescription(PrescriptionDto prescriptionDto, MultipartFile file) throws IOException {
        String imageUrl;
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).orElse(null);

        if (file != null && !file.isEmpty()) {
            imageUrl = fileService.uploadFile(file);
        } else {
            imageUrl = null; // or set a default image URL for prescriptions without an image
        }

        prescriptionDto.setUserName(user.getUserName()); // Set the username in the DTO

        Prescription prescription = new Prescription();
        prescription.setUser(user); // Set the user in the prescription
        prescription.setPrescriptionImageUrl(imageUrl);
        prescription.setFrequency(prescriptionDto.getFrequency());
        prescription.setFulfilment(prescriptionDto.getFulfilment());
        prescription.setSubstitute(prescriptionDto.getSubstitute());
        prescriptionRepository.save(prescription);
    }

    public PrescriptionDto getPrescriptionDto(Prescription prescription){
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setFrequency(prescription.getFrequency());
        prescriptionDto.setPrescriptionImageUrl(prescription.getPrescriptionImageUrl());
        prescriptionDto.setFulfilment(prescription.getFulfilment());
        prescriptionDto.setSubstitute(prescription.getSubstitute());
        return prescriptionDto;
    }
    public List<PrescriptionDto> getAllPrescriptions() {
        List<Prescription> allPrescriptions = prescriptionRepository.findAll();
        List<PrescriptionDto> prescriptionDtos = new ArrayList<>();
        for (Prescription prescription:allPrescriptions){
            prescriptionDtos.add(getPrescriptionDto(prescription));
        }
        return prescriptionDtos;
    }

    public List<Prescription> getPrescriptionDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return prescriptionRepository.findByUser(user);
    }
}
