package com.cascade.journal;

import com.cascade.journal.model.Customer;
import com.cascade.journal.model.Visit;

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
        result.mobile = origin.getMobilePhone();
        result.email = origin.getEmail();
        result.bdYear = origin.getBirthDate().get(GregorianCalendar.YEAR);
        result.bdMonth = origin.getBirthDate().get(GregorianCalendar.MONTH);
        result.bdDay = origin.getBirthDate().get(GregorianCalendar.DAY_OF_MONTH);

        return result;
    }

    public static Customer toOrigin(
            @NotNull com.cascade.journal.api.dto.Customer dto) {
        Customer origin = new Customer();
        origin.setId(dto.id);
        origin.setFirstName(dto.firstName);
        origin.setLastName(dto.lastName);
        origin.setMobilePhone(dto.mobile);
        origin.setEmail(dto.email);
        origin.setBirthDate(new GregorianCalendar(
                dto.bdYear, dto.bdMonth, dto.bdDay));
        return origin;
    }

    public static com.cascade.journal.api.dto.Visit toDto(
            @NotNull Visit origin) {
        com.cascade.journal.api.dto.Visit dto
                = new com.cascade.journal.api.dto.Visit();

        dto.id = origin.getId();
        dto.customerId = origin.getCustomer().getId();
        dto.vYear = origin.getDate().get(GregorianCalendar.YEAR);
        dto.vMonth = origin.getDate().get(GregorianCalendar.MONTH);
        dto.vDay = origin.getDate().get(GregorianCalendar.DAY_OF_MONTH);
        dto.vHour = origin.getDate().get(GregorianCalendar.HOUR_OF_DAY);
        dto.vMinute = origin.getDate().get(GregorianCalendar.MINUTE);

        return dto;
    }

    public static Visit toOrigin(
            @NotNull com.cascade.journal.api.dto.Visit dto,
            @NotNull Customer customer) {
        Visit origin = new Visit();
        origin.setId(dto.id);
        origin.setCustomer(customer);
        origin.setDate(new GregorianCalendar(
                dto.vYear, dto.vMonth, dto.vDay,
                dto.vHour, dto.vMinute));
        return origin;
    }
}
