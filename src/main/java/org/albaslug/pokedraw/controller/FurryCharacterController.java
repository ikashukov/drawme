package org.albaslug.pokedraw.controller;

import lombok.RequiredArgsConstructor;
import org.albaslug.pokedraw.service.ActionService;
import org.albaslug.pokedraw.service.FurryCharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FurryCharacterController {

    private static final String E621_LINK = "https://e621.net/posts?tags=%s";

    private final FurryCharacterService furryCharacterService;
    private final ActionService actionService;

    @GetMapping("/furry")
    public String getChallenge(Model model) {
        String character = furryCharacterService.getRandomFurryCharacter();
        model.addAttribute("link", String.format(E621_LINK, character));
        model.addAttribute("task", character
                + " + "
                + actionService.getAction());
        return "furry-challenge";

    }

}
