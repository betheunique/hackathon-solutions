package com.adcash.trading.controller;

import com.adcash.trading.service.DatabaseSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@Controller
public class DatabaseSnapshotController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DatabaseSnapshotService databaseSnapshotService;


    @RequestMapping(value = "/databaseSnapshot", method = RequestMethod.GET)
    @Produces(MediaType.TEXT_HTML)
    public String databaseSnapshot(Model model) {
        model.addAttribute("dbSnapshot", databaseSnapshotService.getDBSnapshot());
        return "dbSnapshot";
    }

}
