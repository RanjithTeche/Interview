package com.c4l.rewardservice.service.impl;

import static com.c4l.rewardservice.common.ApplicationConstant.NOCARD_FOUND_CODE;
import static com.c4l.rewardservice.common.ApplicationConstant.NOCARD_FOUND_DESC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.c4l.rewardservice.client.CreditCardServiceClient;
import com.c4l.rewardservice.common.ApplicationConstant;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.entity.RewardsFailedReport;
import com.c4l.rewardservice.exception.CardMissMatchException;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.model.VerificationResponse;
import com.c4l.rewardservice.repository.RewardRepository;
import com.c4l.rewardservice.repository.RewardsFailedReportRepository;
import com.c4l.rewardservice.service.RewardService;



@Service
public class RewardServiceImpl implements RewardService {

	@Autowired
	RewardRepository rewardRepository;
	
	@Autowired
	private CreditCardServiceClient cardServiceProxy;
	 
	@Autowired
    private RewardsFailedReportRepository rewardFailureRepository;
	
	@Override
	public Rewards processRewards(Reward reward) {

		// check for valid credit card
		VerificationResponse verficationResponse = cardServiceProxy.checkCard(reward.getPseudoCard()).getBody();
		if (!VerificationResponse.Status.VERIFICATION_PASSED
				.equals(verficationResponse.status)) {
			
			RewardsFailedReport rewardFailRep =prepareInputDataForFailure(reward);
			
			/* dumping the failed responses to RewardsFailedReport for tracking purpose. */
		
			rewardFailureRepository.save(rewardFailRep);
			
			//Exception if card not found
			throw new CardMissMatchException(NOCARD_FOUND_CODE,NOCARD_FOUND_DESC,HttpStatus.NOT_FOUND.value());
		}
		// insert into DB
		// replace code with Map struct to replace pojos
		Rewards rew =prepareInputDataForRewards(reward);
		rewardRepository.save(rew);
		return rew;
	}
	
	private Rewards prepareInputDataForRewards(Reward reward) {
		Rewards rew = new Rewards();
		rew.setAmount(reward.getAmount());
		rew.setBinNo(reward.getBinNo());
		rew.setCardType(rew.getCardType());
		rew.setCifid(reward.getCifid());
		rew.setPrimary(reward.isPrimary());
		rew.setPseudoCard(reward.getPseudoCard());
		rew.setTranAmount(reward.getTranAmount());
		return rew;

	}

	
	private RewardsFailedReport prepareInputDataForFailure(Reward reward) {
		RewardsFailedReport rewardFailRep = new RewardsFailedReport();
		rewardFailRep.setAmount(rewardFailRep.getAmount());
		rewardFailRep.setBinNo(rewardFailRep.getBinNo());
		rewardFailRep.setCardType(rewardFailRep.getCardType());
		rewardFailRep.setCifid(rewardFailRep.getCifid());
		rewardFailRep.setPrimary(rewardFailRep.isPrimary());
		rewardFailRep.setPseudoCard(rewardFailRep.getPseudoCard());
		rewardFailRep.setTranAmount(rewardFailRep.getTranAmount());
		rewardFailRep.setDesc(ApplicationConstant.PSEUDOCARD_FAILURE_DESC);
		return rewardFailRep;
 
	}
}
