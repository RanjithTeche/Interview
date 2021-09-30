package com.c4l.rewardservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4l.rewardservice.common.ResponseUtil;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.model.GenericResponse;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.service.RewardService;

@RequestMapping("/rewards")
@RestController
public class RewardController {

	@Autowired
	RewardService rewardService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	public ResponseEntity<GenericResponse> insertRewards(@Valid @RequestBody Reward reward) {
		logger.info("Entered checkCard input: {}", reward);
		
		//service call to process the rewards requests
		Rewards rewards=rewardService.processRewards(reward);
		
		return ResponseUtil.response201(ResponseUtil.createGenericResponse(),"" );
		
	}
}
