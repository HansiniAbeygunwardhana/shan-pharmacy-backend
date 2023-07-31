package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.common.ApiResponse;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.PrescriptionDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Prescription;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.FileService;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prescriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    FileService fileService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createPrescription(@ModelAttribute PrescriptionDto prescriptionDto, @RequestParam("file") MultipartFile file){

        try {
            String imageUrl = fileService.uploadFile(file);
            prescriptionDto.setPrescriptionImageUrl(imageUrl);

            prescriptionService.createPrescription(prescriptionDto, file);
            return new ResponseEntity<>(new ApiResponse(true, "Prescription has been uploaded"), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error uploading prescription details"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptions(){
        List<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions,HttpStatus.OK);
    }

    @GetMapping({"/getPrescriptionDetails"})
    public List<Prescription> getCartDetails() {
        return prescriptionService.getPrescriptionDetails();
    }

}
