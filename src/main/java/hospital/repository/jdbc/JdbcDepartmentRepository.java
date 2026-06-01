package hospital.repository.jdbc;

import hospital.model.Department;
import hospital.repository.DepartmentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDepartmentRepository implements DepartmentRepository {

    private static final String INSERT_DEPARTMENT =
            "INSERT INTO departments (name, patient_count) VALUES (?, ?)";

    private static final String UPDATE_DEPARTMENT =
            "UPDATE departments SET name = ?, patient_count = ? WHERE id = ?";

    private static final String SELECT_DEPARTMENT =
            "SELECT id, name, patient_count FROM departments WHERE id = ?";

    private static final String SELECT_ALL_DEPARTMENTS =
            "SELECT id, name, patient_count FROM departments ORDER BY id";

    private static final String DELETE_DEPARTMENT =
            "DELETE FROM departments WHERE id = ?";

    private static final String EXIST_DEPARTMENT =
            "SELECT 1 FROM departments WHERE id = ?";

    private static final String UPDATE_PATIENT_COUNT =
            "UPDATE departments SET patient_count = GREATEST(0, patient_count + ?) WHERE id = ?";

    private final JdbcConnectionFactory connectionFactory;

    public JdbcDepartmentRepository(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Department save(Department entity) {
        try (Connection conn = connectionFactory.getConnection()) {
            if (entity.getId() == null) {
                try (PreparedStatement ps = conn.prepareStatement(
                        INSERT_DEPARTMENT, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, entity.getName());
                    ps.setInt(2, entity.getPatientCount());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            entity.setId(keys.getLong(1));
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(UPDATE_DEPARTMENT)) {
                    ps.setString(1, entity.getName());
                    ps.setInt(2, entity.getPatientCount());
                    ps.setLong(3, entity.getId());
                    ps.executeUpdate();
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_DEPARTMENT)) {
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
    public List<Department> findAll() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_DEPARTMENTS);
             ResultSet rs = ps.executeQuery()) {
            List<Department> list = new ArrayList<>();
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
             PreparedStatement ps = conn.prepareStatement(DELETE_DEPARTMENT)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(EXIST_DEPARTMENT)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePatientCount(Long departmentId, int delta) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PATIENT_COUNT)) {
            ps.setInt(1, delta);
            ps.setLong(2, departmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static Department mapRow(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getLong("id"));
        d.setName(rs.getString("name"));
        d.setPatientCount(rs.getInt("patient_count"));
        return d;
    }
}