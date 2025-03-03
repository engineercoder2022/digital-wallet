package com.inghubs.wallet.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
public class SwaggerController {

  @GetMapping({"/", "/swagger", "/oas3"})
  public String redirectSwagger() {
    return "redirect:/swagger-ui.html";
  }
}
