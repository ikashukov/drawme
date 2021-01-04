package org.albaslug.drawme.controller;

import lombok.RequiredArgsConstructor;
import org.albaslug.drawme.service.ActionService;
import org.albaslug.drawme.service.PokemonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PokemonController {

    private static final String BULBAPEDIA_LINK = "https://archives.bulbagarden.net/wiki/Category:%s";

    private final PokemonService pokemonService;
    private final ActionService actionService;

    @GetMapping("/pokemon")
    public String getPokemonAndAction(Model model) {
        String pokemonName = pokemonService.getRandomPokemon();
        model.addAttribute("link", String.format(BULBAPEDIA_LINK, pokemonName));
        model.addAttribute("task", pokemonName
                + " + "
                + actionService.getAction());
        return "pokemon-challenge";
    }

}
