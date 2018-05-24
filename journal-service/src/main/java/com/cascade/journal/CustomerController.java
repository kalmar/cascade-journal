package com.cascade.journal;

import com.cascade.journal.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody com.cascade.journal.api.dto.Customer customer) {
        if (log.isDebugEnabled()) {
            log.debug("Asked to add " + customer);
        }

        Customer c = DtoHelper.toOrigin(customer);
        em.persist(c);

        return ResponseEntity.ok(DtoHelper.toDto(c));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> get(@PathVariable Long customerId) {
        Customer customer = em.find(Customer.class, customerId);

        if (null == customer) {
            log.warn("Failed to find customer with id=" + customerId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DtoHelper.toDto(customer));
    }

    @PostMapping("/{customerId}/update")
    public ResponseEntity<?> update(
            @PathVariable Long customerId,
            @RequestBody com.cascade.journal.api.dto.Customer customer) {

        Customer old = em.find(Customer.class, customerId);

        if (null == old) {
            log.warn("Failed to find customer with id=" + customerId + " to update");
            return ResponseEntity.notFound().build();
        }

        if (log.isDebugEnabled()) {
            log.debug("Asked to update " + old + " with " + customer);
        }

        old.setFirstName(customer.firstName);
        old.setLastName(customer.lastName);
        old.setMobilePhone(customer.mobilePhone);
        old.setEmail(customer.email);
        old.setBirthDate(new GregorianCalendar(
                customer.year, customer.month, customer.day));

        em.merge(old);

        return ResponseEntity.ok(DtoHelper.toDto(old));
    }

    @PostMapping("/{customerId}/remove")
    public ResponseEntity<?> update(@PathVariable Long customerId) {
        Customer old = em.find(Customer.class, customerId);

        if (null == old) {
            log.warn("Failed to find customer with id=" + customerId + " to remove");
            return ResponseEntity.notFound().build();
        }

        if (log.isDebugEnabled()) {
            log.debug("Asked to remove " + old);
        }

        em.remove(old);

        return ResponseEntity.ok(DtoHelper.toDto(old));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer max) {

        if (log.isDebugEnabled()) {
            log.debug("Asked to list offset=" + offset + ", max=" + max);
        }

        CriteriaQuery<Customer> cq = em.getCriteriaBuilder().createQuery(Customer.class);
        cq.select(cq.from(Customer.class));

        List<Customer> rawResult = em
                .createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(max)
                .getResultList();

        final ArrayList<com.cascade.journal.api.dto.Customer> result = new ArrayList<>();
        rawResult.forEach(v -> result.add(DtoHelper.toDto(v)));

        if (log.isDebugEnabled()) {
            log.debug("List is going to return " + result);
        }

        return ResponseEntity.ok(result);
    }
}
