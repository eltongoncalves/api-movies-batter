package br.com.letscode.web.rest;

import br.com.letscode.domain.Play;
import br.com.letscode.domain.Round;
import br.com.letscode.repository.PlayRepository;
import br.com.letscode.service.PlayService;
import br.com.letscode.service.RoundService;
import br.com.letscode.service.dto.PlayRankDTO;
import br.com.letscode.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link br.com.letscode.domain.Play}.
 */
@RestController
@RequestMapping("/api")
public class PlayResource {

    private final Logger log = LoggerFactory.getLogger(PlayResource.class);

    private static final String ENTITY_NAME = "play";

    private String applicationName =  "moviesbattleApp";

    private final PlayService playService;

    private final PlayRepository playRepository;

    private final RoundService roundService;

    public PlayResource(PlayService playService, PlayRepository playRepository, RoundService roundService) {
        this.playService = playService;
        this.playRepository = playRepository;
        this.roundService = roundService;
    }

    @PostMapping("/plays/start")
    public ResponseEntity<Round> startPlay(@Valid @RequestBody Play play) throws URISyntaxException {
        log.debug("REST request to start Play : {}", play);

        if (!playService.findOne(play.getId()).isEmpty()) {
            throw new BadRequestAlertException("A new play cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Round result = roundService.newRound(playService.start(play));

        return ResponseEntity
            .created(new URI("/api/plays/start" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/plays/end/{id}")
    public ResponseEntity<Play> endPlay(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody Play play)
        throws URISyntaxException {
        log.debug("REST request to end Play : {}, {}", id, play);
        if (play.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, play.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Play result = playService.end(play);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, play.getId().toString()))
            .body(result);
    }

    @GetMapping("/plays/rank")
    public List<PlayRankDTO> getRank() {
        return playService.rank();
    }
}
