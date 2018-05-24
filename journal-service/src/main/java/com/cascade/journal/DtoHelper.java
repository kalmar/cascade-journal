package com.cascade.journal;

import com.cascade.journal.model.Customer;

import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

public final class DtoHelper {
    public static com.cascade.journal.api.dto.Customer toDto(
            @NotNull Customer origin) {
        com.cascade.journal.api.dto.Customer result
                = new com.cascade.journal.api.dto.Customer();

        result.id = origin.getId();
        result.firstName = origin.getFirstName();
        result.lastName = origin.getLastName();
        result.mobilePhone = origin.getMobilePhone();
        result.email = origin.getEmail();
        result.year = origin.getBirthDate().get(GregorianCalendar.YEAR);
        result.month = origin.getBirthDate().get(GregorianCalendar.MONTH);
        result.day = origin.getBirthDate().get(GregorianCalendar.DAY_OF_MONTH);

        return result;
    }

    public static Customer toOrigin(
            @NotNull com.cascade.journal.api.dto.Customer dto) {
        Customer origin = new Customer();
        origin.setId(dto.id);
        origin.setFirstName(dto.firstName);
        origin.setLastName(dto.lastName);
        origin.setMobilePhone(dto.mobilePhone);
        origin.setEmail(dto.email);
        origin.setBirthDate(new GregorianCalendar(
                dto.year, dto.month, dto.day));
        return origin;
    }

}
