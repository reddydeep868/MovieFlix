/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.exception.UserNotFoundException;
import com.koushik.movieflix.repositry.UserRepository;
import com.koushik.movieflix.service.UserRatingService;
import com.koushik.movieflix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author koushik
 */
@RestController
public class RatingController {

    @Autowired
    UserRatingService userRatingService;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/rating/{user}/{title}", method = RequestMethod.POST)
    public void rateAndComment(
            @RequestParam(value = "rate", required = false) Short rate,
            @PathVariable("user") int userId,
            @RequestParam(value = "comment", required = false) String comment,
            @PathVariable("title") int titleId,
            UriComponentsBuilder ucBuilder) throws UserNotFoundException {
        System.out.println("user with id: " + userId + " has just rated title with id: " + titleId + " with rate: " + rate + " and commented: " + comment);
        //check if this user has rating on this title
        UserRating retrievedUserRating = userRatingService.userhasRateOntitle(userId, titleId);
        User user = userRepo.findUser(userId);
        if (retrievedUserRating == null) {
            UserRating newRating = new UserRating();
            newRating.setUser(new User(userId));
            newRating.setTitle(new Title(titleId));
            newRating.setComment(comment);
            newRating.setRating(rate);
            newRating.setName(user.getFirstname() + " " + user.getLastname());
            userRatingService.rate(newRating);
        } else {
            if (comment != null) {
                retrievedUserRating.setComment(comment);
            }
            if (rate != null) {
                retrievedUserRating.setRating(rate);
            }
            userRatingService.updateRate(retrievedUserRating);
        }
    }

    @RequestMapping(value = "/getavgrating/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getAvgRating(@PathVariable("titleId") int titleId) {
        return userRatingService.getAvgRating(titleId);
    }
}
