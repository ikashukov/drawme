package org.albaslug.drawme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FurryCharacterService {

    public final E621IntegrationService e621IntegrationService;
    private final Random random = new Random();

    public String getRandomFurryCharacter() {
        List<String> dc = e621IntegrationService.getDisneyCharacters();
        return dc.get(random.nextInt(dc.size()));
    }

}
