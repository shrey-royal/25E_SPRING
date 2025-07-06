package com.company.rest.service;

import com.company.rest.entity.Medicine;
import com.company.rest.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicineService {

    private MedicineRepository repository;

    public Medicine saveMedicine(Medicine medicine) {
        return repository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    public Optional<Medicine> findMedicineById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Medicine update(Long id, Medicine medicine) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(medicine.getName());
                    existing.setPrice(medicine.getPrice());
                    existing.setExpiryDate(medicine.getExpiryDate());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Medicine with id" + id + " not found"));
    }
}
