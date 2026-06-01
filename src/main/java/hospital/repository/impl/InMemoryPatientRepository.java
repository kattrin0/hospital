package hospital.repository.impl;

import hospital.model.Patient;
import hospital.repository.PatientRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class InMemoryPatientRepository implements PatientRepository {
    private final Map<Long, Patient> storage = new HashMap<>();
    private long nextId = 1;

    @Override
    public Patient save(Patient entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public List<Patient> findByDepartmentId(Long departmentId) {
        return storage.values().stream()
                .filter(p -> departmentId.equals(p.getDepartmentId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> findAllWithDepartment() {
        return new ArrayList<>(storage.values());
    }
}