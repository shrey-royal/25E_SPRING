package com.company.rest.controller;

import com.company.rest.entity.Medicine;
import com.company.rest.service.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meds")
@AllArgsConstructor
public class MedicineController {
    private MedicineService service;

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return service.getAllMedicines();
    }

    @PostMapping("/save")
    public Medicine saveMedicine(@RequestBody Medicine medicine) {
        return service.saveMedicine(medicine);
    }
}
