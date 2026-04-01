package hospital.repository.impl;

import hospital.model.Department;
import hospital.repository.DepartmentRepository;

import java.util.*;

public class InMemoryDepartmentRepository implements DepartmentRepository {
    private final Map<Long, Department> storage = new HashMap<>();
    private long nextId = 1;

    @Override
    public Department save(Department entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Department> findAll() {
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
    public void updatePatientCount(Long departmentId, int delta) {
        findById(departmentId).ifPresent(dept ->
                dept.setPatientCount(Math.max(0, dept.getPatientCount() + delta))
        );
    }
}