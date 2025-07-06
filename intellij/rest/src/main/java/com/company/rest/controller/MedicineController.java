package com.company.rest.controller;

import com.company.rest.entity.Medicine;
import com.company.rest.service.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        return service.findMedicineById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        try {
            return ResponseEntity.ok(service.update(id, medicine));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
//getmedsbyname(string name)
//getmedsgreaterthanprice(float price)