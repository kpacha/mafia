package com.github.kpacha.mafia.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.repository.GangsterRepository;
import com.github.kpacha.mafia.service.GangsterService;
import com.github.kpacha.mafia.service.PopulatorService;

@Controller
public class GangsterController {
    private static final Logger log = LoggerFactory
	    .getLogger(GangsterController.class);

    @Autowired
    private GangsterRepository repo;
    @Autowired
    private GangsterService service;
    @Autowired
    private PopulatorService populator;

    @RequestMapping(value = "/gangsters/{gangsterId}/toJail", method = RequestMethod.GET)
    public String sendToJail(final Model model, @PathVariable Long gangsterId) {
	Gangster substitutor = service.sendToJail(repo.findOne(gangsterId));
	log.debug("Substitutor [" + substitutor + "] with "
		+ substitutor.getManaged().size() + " subordinates saved");
	return "redirect:/gangsters/" + gangsterId;
    }

    @RequestMapping(value = "/gangsters/{gangsterId}/release", method = RequestMethod.GET)
    public String release(final Model model, @PathVariable Long gangsterId) {
	service.release(repo.findOne(gangsterId));
	return "redirect:/gangsters/" + gangsterId;
    }

    @RequestMapping(value = "/gangsters/{gangsterId}", method = RequestMethod.GET)
    public String singleGangsterView(final Model model,
	    @PathVariable Long gangsterId) {
	Gangster gangster = repo.findOne(gangsterId);
	model.addAttribute("id", gangsterId);
	if (gangster != null) {
	    model.addAttribute("gangster", gangster);
	    model.addAttribute("boss", service.getBoss(gangster));
	    model.addAttribute("level", service.getLevel(gangster));
	    model.addAttribute("totalSubordinates",
		    service.countAllSubordinates(gangster));
	}
	return "/gangsters/show";
    }

    @RequestMapping(value = "/gangsters/page/{page}", method = RequestMethod.GET)
    public String listGangstersByPage(Model model, @PathVariable int page) {
	model = doList(model, page);
	return "/gangsters/list";
    }

    @RequestMapping(value = "/gangsters", method = RequestMethod.GET)
    public String listGangsters(Model model) {
	model = doList(model, 0);
	return "/gangsters/list";
    }

    private Model doList(Model model, int page) {
	Page<Gangster> gangsters = repo.findAll(new PageRequest(0, 20));
	model.addAttribute("gangsters", gangsters.getContent());
	model.addAttribute("page", page);
	return model;
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public String findGangstersFromUrl(Model model, @PathVariable String query) {
	return doFindGangsters(model, query);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String findGangsters(Model model, @RequestParam("query") String query) {
	return doFindGangsters(model, query);
    }

    private String doFindGangsters(Model model, String query) {
	log.info("received search query : " + query);
	if (query != null && !query.isEmpty()) {
	    Page<Gangster> gangsters = repo.findByNameLike(query,
		    new PageRequest(0, 20));
	    model.addAttribute("gangsters", gangsters.getContent());
	} else {
	    model.addAttribute("gangsters", Collections.emptyList());
	}
	model.addAttribute("query", query);
	return "/gangsters/list";
    }

    @RequestMapping(value = "/populate/{deep}", method = RequestMethod.GET)
    public String populateByParam(Model model, @PathVariable int deep) {
	return doPopulate(model, deep);
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public String populate(Model model) {
	return doPopulate(model, 3);
    }

    private String doPopulate(Model model, int deep) {
	log.info("Populating the db with deep=" + deep);
	int gangSize = populator.populate(deep);
	log.info("Created " + gangSize + " instances");
	return "forward:/search/sample";
    }
}
