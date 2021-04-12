package com.qapital.savings.rule;

import com.qapital.savings.event.SavingsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/savings/rule")
public class SavingsRulesController {

    private final StandardSavingsRulesService savingsRulesService;

    public SavingsRulesController(@Autowired StandardSavingsRulesService savingsRulesService1) {
        this.savingsRulesService = savingsRulesService1;
    }

    @GetMapping("/active/{userId}")
    public List<SavingsRule> activeRulesForUser(@PathVariable Long userId) {
        log.info("get request to activeRulesForUser");
        return savingsRulesService.activeRulesForUser(userId);
    }

    @PostMapping("/events")
    public List<SavingsEvent> retrieveEvents(@RequestBody SavingsRule savingsRule){
        log.info("retrieving all savings events from endpoint '/events'");

        savingsRulesService.executeRule(savingsRule);

        return savingsRulesService.getAll();
    }

}
