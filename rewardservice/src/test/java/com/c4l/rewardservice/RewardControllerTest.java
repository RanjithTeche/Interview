package com.c4l.rewardservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.repository.RewardRepository;
import com.c4l.rewardservice.service.RewardService;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(properties = "spring.cloud.config.enabled=false", webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class RewardControllerTest {

	MockMvc mockMvc;

	@Mock
	private com.c4l.rewardservice.controller.RewardController rewardController;

	@MockBean
	private RewardService rewardService;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
	}

	@Test
	public void testRewardShouldBeRegistered() throws Exception {
		String passCard = "CCE-fjhfirghhth";
		Rewards rewards = new Rewards();
		rewards.setPseudoCard(passCard);
		rewards.setAmount(BigDecimal.TEN);
		when(rewardService.processRewards(any(Reward.class))).thenReturn(rewards);
		Reward reward = new Reward();
		reward.setPseudoCard(passCard);
		reward.setAmount(BigDecimal.TEN);
		ResponseEntity<String> response = template.postForEntity("/rewards", getHttpEntity(reward), String.class);
		// Delete this user
		assertThat(201, is(response.getStatusCode().value()));
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
}