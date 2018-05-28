package com.cascade.journal;

import com.cascade.journal.model.Customer;
import com.cascade.journal.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/visit")
public class VisitController {

    private static Logger log = LoggerFactory.getLogger(JournalApplication.class);

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody com.cascade.journal.api.dto.Visit visit) {
        if (log.isDebugEnabled()) {
            log.debug("Asked to add " + visit);
        }

        Customer customer = em.find(Customer.class, visit.customerId);
        if (null == customer) {
            log.warn("Failed to find customer with id = " + visit.customerId);
            return ResponseEntity.notFound().build();
        }

        Visit v = DtoHelper.toOrigin(visit, customer);
        em.persist(v);

        return ResponseEntity.ok(DtoHelper.toDto(v));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam(required = true) Long id) {
        Visit old = em.find(Visit.class, id);

        if (null == old) {
            log.warn("Failed to find visit with id=" + id + " to remove");
            return ResponseEntity.notFound().build();
        }

        em.remove(old);

        return ResponseEntity.ok(DtoHelper.toDto(old));
    }
}
