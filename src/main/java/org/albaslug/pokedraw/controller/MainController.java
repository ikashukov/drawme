package org.albaslug.pokedraw.controller;

import lombok.RequiredArgsConstructor;
import org.albaslug.pokedraw.service.ActionService;
import org.albaslug.pokedraw.service.PokemonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PokemonService pokemonService;
    private final ActionService actionService;

    @GetMapping
    public String getPokemonAndAction(Model model) {
        model.addAttribute("task", pokemonService.getRandomPokemon()
                + " + "
                + actionService.getAction());
        return "combo";
    }

}
