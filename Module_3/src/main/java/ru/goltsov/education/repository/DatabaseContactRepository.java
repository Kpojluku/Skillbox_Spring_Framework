package ru.goltsov.education.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import ru.goltsov.education.dto.Contact;
import ru.goltsov.education.repository.mapper.ContactRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class DatabaseContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addContact(Contact contact) {
        String sql = "INSERT INTO contact_schema.contacts (id, firstName, lastName, email, phone) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());
    }

    @Override
    public List<Contact> getContactList() {
        String sql = "SELECT * FROM contact_schema.contacts;";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> getContactById(int id) {

        String sql = "SELECT * FROM contact_schema.contacts WHERE id = ?;";

        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );

        return Optional.ofNullable(contact);
    }

    @Override
    public boolean editContact(Contact contact) {
        String sql = "UPDATE contact_schema.contacts SET firstName=?, lastName=?, email=?, phone=? WHERE id=?";
        int updatedRows = jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
        return updatedRows > 0;
    }


    @Override
    public boolean removeContact(int id) {
        String sql = "DELETE FROM contact_schema.contacts WHERE id=?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}
