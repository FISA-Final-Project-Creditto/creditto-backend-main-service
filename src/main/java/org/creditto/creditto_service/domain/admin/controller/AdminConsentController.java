package org.creditto.creditto_service.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.admin.dto.ConsentDefinitionForm;
import org.creditto.creditto_service.domain.admin.service.AdminConsentService;
import org.creditto.creditto_service.domain.consent.entity.ConsentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.creditto.creditto_service.global.common.Constants.*;

@Controller
@RequestMapping("/admin/consents")
@RequiredArgsConstructor
public class AdminConsentController {

    private final AdminConsentService adminConsentService;
    private static final String REDIRECT_BASIC_URL = "redirect:/admin/consents";

    @GetMapping
    public String listConsents(Model model) {
        prepareListPage(model);
        return "admin/consents";
    }

    @PostMapping
    public String createConsent(
            @Valid @ModelAttribute("createForm") ConsentDefinitionForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            prepareListPage(model);
            return "admin/consents";
        }
        adminConsentService.createDefinition(form);
        redirectAttributes.addFlashAttribute(MESSAGE, "동의서 정의가 추가되었습니다.");
        return REDIRECT_BASIC_URL;
    }

    @GetMapping("/{consentId}/edit")
    public String editConsent(@PathVariable Long consentId, Model model) {
        if (!model.containsAttribute(FORM)) {
            model.addAttribute(FORM, adminConsentService.getDefinitionForm(consentId));
        }
        model.addAttribute("consentId", consentId);
        model.addAttribute(CATEGORIES, ConsentCategory.values());
        return "admin/consent-edit";
    }

    @PostMapping("/{consentId}/update")
    public String updateConsent(
            @PathVariable Long consentId,
            @Valid @ModelAttribute(FORM) ConsentDefinitionForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("consentId", consentId);
            model.addAttribute(CATEGORIES, ConsentCategory.values());
            return "admin/consent-edit";
        }
        adminConsentService.updateDefinition(consentId, form);
        redirectAttributes.addFlashAttribute(MESSAGE, "동의서 정의가 수정되었습니다.");
        return REDIRECT_BASIC_URL;
    }

    @PostMapping("/{consentId}/delete")
    public String deleteConsent(@PathVariable Long consentId, RedirectAttributes redirectAttributes) {
        adminConsentService.deleteDefinition(consentId);
        redirectAttributes.addFlashAttribute(MESSAGE, "동의서 정의가 삭제되었습니다.");
        return REDIRECT_BASIC_URL;
    }

    private void prepareListPage(Model model) {
        model.addAttribute("consents", adminConsentService.getAllDefinitions());
        model.addAttribute(CATEGORIES, ConsentCategory.values());
        if (!model.containsAttribute("createForm")) {
            model.addAttribute("createForm", new ConsentDefinitionForm());
        }
    }
}
