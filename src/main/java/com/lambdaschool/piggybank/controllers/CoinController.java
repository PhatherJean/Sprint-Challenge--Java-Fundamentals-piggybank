package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{
    @Autowired
    CoinRepository coinrepos;

    private List<Coin> findCoins(List<Coin> myCoin, CheckCoin tester)
    {
        List<Coin> tempList = new ArrayList<>();
        for (Coin c : myCoin) {
            if (tester.test(c)) {
                tempList.add(c);
            }
        }
        return tempList;
    }
    @GetMapping(value ="/total", produces = {"application/json"})
    public ResponseEntity<?> piggyBankTotal()
    {
        List<Coin> myCoin = new ArrayList<>();
        coinrepos.findAll()
                .iterator()
                .forEachRemaining(myCoin::add);
        double coinTotal = 0.0;
        List<String> changeList = new ArrayList<>();
        for (Coin c : myCoin)
        {
            coinTotal = coinTotal + (c.getQuantity() * c.getValue());
            if(c.getQuantity() > 1)
            {
                changeList.add(c.getQuantity() + " " + c.getNameplural() + " \n");
            } else
            {
                changeList.add(c.getQuantity() + " " + c.getName() + "\n");
            }
        }
        System.out.println(changeList+ "\n The piggy bank holds "+ coinTotal);
        return new ResponseEntity<>(coinTotal, HttpStatus.OK);
    }
}
