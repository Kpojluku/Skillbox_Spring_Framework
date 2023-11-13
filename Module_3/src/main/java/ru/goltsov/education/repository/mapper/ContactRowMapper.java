package ru.goltsov.education.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.goltsov.education.dto.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();

        contact.setId(rs.getInt(Contact.Fields.id));
        contact.setFirstName(rs.getString(Contact.Fields.firstName));
        contact.setLastName(rs.getString(Contact.Fields.lastName));
        contact.setEmail(rs.getString(Contact.Fields.email));
        contact.setPhone(rs.getString(Contact.Fields.phone));

        return contact;
    }
}
