package hospital.repository.jdbc;

import hospital.model.Patient;
import hospital.repository.PatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPatientRepository implements PatientRepository {
    private final JdbcConnectionFactory connectionFactory;

    public JdbcPatientRepository(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Patient save(Patient entity) {
        try (Connection conn = connectionFactory.getConnection()) {
            if (entity.getId() == null) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO patients (full_name, age, gender, department_id) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, entity.getFullName());
                    ps.setInt(2, entity.getAge());
                    ps.setString(3, entity.getGender());
                    ps.setLong(4, entity.getDepartmentId());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            entity.setId(keys.getLong(1));
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE patients SET full_name = ?, age = ?, gender = ?, department_id = ? WHERE id = ?")) {
                    ps.setString(1, entity.getFullName());
                    ps.setInt(2, entity.getAge());
                    ps.setString(3, entity.getGender());
                    ps.setLong(4, entity.getDepartmentId());
                    ps.setLong(5, entity.getId());
                    ps.executeUpdate();
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT id, full_name, age, gender, department_id FROM patients WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public List<Patient> findAll() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT id, full_name, age, gender, department_id FROM patients ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            List<Patient> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM patients WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM patients WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public List<Patient> findByDepartmentId(Long departmentId) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT id, full_name, age, gender, department_id FROM patients WHERE department_id = ? ORDER BY id")) {
            ps.setLong(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Patient> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public List<Patient> findAllWithDepartment() {
        return findAll();
    }

    private static Patient mapRow(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        p.setFullName(rs.getString("full_name"));
        p.setAge(rs.getInt("age"));
        p.setGender(rs.getString("gender"));
        p.setDepartmentId(rs.getLong("department_id"));
        return p;
    }
}
