package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.response.ContributorPageRes;
import com.fa20se28.vma.service.ContributorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contributors")
public class ContributorController {
    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping()
    public ContributorPageRes getContributors(@RequestParam(required = false) String userId,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String phoneNumber,
                                              @RequestParam(required = false) Long totalVehicle,
                                              @RequestParam(required = false, defaultValue = "0") int page) {
        return contributorService.getContributors(new ContributorPageReq(userId, name, phoneNumber, totalVehicle, page));
    }

    @GetMapping("count")
    public int getTotalContributor(@RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phoneNumber,
                                   @RequestParam(required = false) Long totalVehicle) {
        return contributorService.getTotalContributorsOrTotalFilteredContributors(new ContributorPageReq(userId, name, phoneNumber, totalVehicle));
    }

    @GetMapping("total-vehicle")
    public int getTheHighestOrLowestTotalVehicleInAllContributors(@RequestParam int option) {
        return contributorService.getTheHighestOrLowestTotalVehicleInAllContributors(option);
    }
}
