package voting.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import voting.exception.CustomErrorType;
import voting.model.Theme;
import voting.model.Vote;
import voting.model.VotingForm;
import voting.service.ThemeService;
import voting.service.VoteService;

@Transactional
@Configuration
@RestController
@RequestMapping("/")
public class VotingController {

	public static final Logger logger = LoggerFactory.getLogger(VotingController.class);

	@Autowired
	private VoteService voteSer;

	@Autowired
	private ThemeService themeSer;

	@GetMapping("/show_themes")
	public List<Theme> showThemes() {
		return themeSer.showThemes();
	}

	@PostMapping("/register_theme")
	public synchronized @ResponseBody void registerTheme(@Valid @RequestBody VotingForm vf) {
		voteSer.addVotes(vf.getItems(), themeSer.addTheme(vf.getName()));
	}

	@PutMapping("/start_theme/{id}")
	public ResponseEntity<?> startTheme(@PathVariable(value = "id") long id) {
		logger.info("start voting : {}", id);
		if (!themeSer.checkStatus(id).equalsIgnoreCase("stopped")) {
			logger.error("Unable to start a voting? please check status", id);
			return new ResponseEntity(new CustomErrorType("Unable to start a voting? please check status"),
					HttpStatus.CONFLICT);
		}
		themeSer.startVoting(id);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@PutMapping("/stop_theme/{id}")
	public ResponseEntity<?> stopTheme(@PathVariable(value = "id") long id) {
		logger.info("finish voting : {}", id);
		if (!themeSer.checkStatus(id).equalsIgnoreCase("started")) {
			logger.error("Unable to finish a voting, please check status", id);
			return new ResponseEntity(new CustomErrorType("Unable to finish a voting, please check status"),
					HttpStatus.CONFLICT);
		}
		themeSer.stopVoting(id);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@DeleteMapping("/delete_theme/{id}")
	public synchronized void deleteTheme(@PathVariable(value = "id") long id) {
		voteSer.removeVote(id);
		themeSer.removeTheme(id);
	}

	@GetMapping("/show_theme/{id}")
	public List<Vote> showTheme(@PathVariable(value = "id") long themeId) {
		return voteSer.getThemeItems(themeId);
	}

	@PutMapping("/vote/{themeId}/{voteId}")
	public synchronized ResponseEntity<?> toVote(@PathVariable(value = "themeId") long themeId,
			@PathVariable(value = "voteId") long voteId) throws InterruptedException {
		logger.info("to vote : {}", themeId);
		if (!themeSer.checkStatus(themeId).equalsIgnoreCase("started")) {
			logger.error("Unable to vote, please check status", themeId);
			return new ResponseEntity(new CustomErrorType("Unable to vote, please check status"), HttpStatus.CONFLICT);
		}
		voteSer.setVote(themeId, voteId);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

}
