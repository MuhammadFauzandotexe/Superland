package com.superland.controllers;

import com.superland.configuration.AppConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//@AllArgsConstructor
public class RedirectController {
    @Autowired
    private AppConfiguration configuration;
    @GetMapping("/redirect")
    public RedirectView redirectToAnotherPage() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(configuration.getRedirectLink()); // Ganti dengan URL tujuan
        return redirectView;
    }
}
