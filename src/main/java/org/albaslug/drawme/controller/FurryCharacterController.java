package org.albaslug.drawme.controller;

import lombok.RequiredArgsConstructor;
import org.albaslug.drawme.service.ActionService;
import org.albaslug.drawme.service.E621IntegrationService;
import org.albaslug.drawme.service.FurryCharacterService;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FurryCharacterController {

    private static final String E621_LINK = "https://e621.net/posts?tags=%s";

    private final FurryCharacterService furryCharacterService;
    private final ActionService actionService;
    private final E621IntegrationService e621IntegrationService;

    @GetMapping("/furry/disney")
    @ResponseBody
    public List<String> disneyChars() {
        return e621IntegrationService.getDisneyCharacters();
    }

    @GetMapping("/furry")
    public String getChallenge(Model model) {
        String character = furryCharacterService.getRandomFurryCharacter();
        model.addAttribute("link", String.format(E621_LINK, character));
        model.addAttribute("task", WordUtils.capitalize(character.replaceAll("_", " "))
                + " + "
                + actionService.getAction());
        return "furry-challenge";

    }

}
