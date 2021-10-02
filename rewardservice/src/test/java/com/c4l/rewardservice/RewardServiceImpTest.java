package com.c4l.rewardservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.c4l.rewardservice.client.CreditCardServiceClient;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.exception.CardMissMatchException;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.model.VerificationResponse;
import com.c4l.rewardservice.repository.RewardRepository;
import com.c4l.rewardservice.repository.RewardsFailedReportRepository;
import com.c4l.rewardservice.service.RewardService;
import com.c4l.rewardservice.service.impl.RewardServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RewardServiceImpTest {

	@Mock
	RewardRepository rewardRepository;

	@Mock
	private CreditCardServiceClient cardServiceProxy;

	@Mock
	private RewardsFailedReportRepository rewardFailureRepository;

	@InjectMocks
	private RewardService rewardService = new RewardServiceImpl();

	@Test
	public void when_valid_card() {

		String passCard = "CCE-q2ejhfjkgdgf";
		ResponseEntity<VerificationResponse> verificationSuccess = new ResponseEntity<VerificationResponse>(
				VerificationResponse.passed(passCard), HttpStatus.OK);
		System.out.println("test body " + verificationSuccess.getBody());

		Rewards reward = new Rewards();
		when(cardServiceProxy.checkCard(passCard)).thenReturn(verificationSuccess);

		when(rewardRepository.save(any(Rewards.class))).thenReturn(reward);
		// test
		System.out.println(cardServiceProxy + "--> " + rewardRepository + "--> " + rewardService);
		Reward rewardRequest = new Reward();
		rewardRequest.setPseudoCard(passCard);
		Rewards returnedReward = rewardService.processRewards(rewardRequest);
		System.out.println("returnedReward ==" + returnedReward);
		assertThat(returnedReward.getPseudoCard(), is(passCard));

	}

	@Test
	public void when_In_valid_card() {

		Assertions.assertThrows(CardMissMatchException.class, () -> {
			String failedCard = "CCE-q2ejhfjkgdgf";
			ResponseEntity<VerificationResponse> verificationFailed = new ResponseEntity<VerificationResponse>(
					VerificationResponse.failed(failedCard), HttpStatus.OK);

			Rewards reward = new Rewards();
			when(cardServiceProxy.checkCard(failedCard)).thenReturn(verificationFailed);

			when(rewardRepository.save(any(Rewards.class))).thenReturn(reward);
			// test
			Reward rewardRequest = new Reward();
			rewardRequest.setPseudoCard(failedCard);
			rewardService.processRewards(rewardRequest);
		});

	}
}