package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorDetailRes;
import com.fa20se28.vma.response.ContributorPageRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.service.ContributorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{contributor-id}")
    public ContributorDetailRes getContributorById(@PathVariable("contributor-id") String userId) {
        return contributorService.getContributorById(userId);
    }

    @GetMapping()
    public ContributorPageRes getContributors(@RequestParam(required = false) String userId,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String phoneNumber,
                                              @RequestParam(required = false) UserStatus userStatus,
                                              @RequestParam(defaultValue = "0") Long min,
                                              @RequestParam(required = false) Long max,
                                              @RequestParam(required = false, defaultValue = "0") int page) {
        return contributorService.getContributors(new ContributorPageReq(userId, name, phoneNumber, userStatus, min, max, page * 15));
    }

    @GetMapping("count")
    public int getTotalContributor(@RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phoneNumber,
                                   @RequestParam(required = false) UserStatus userStatus,
                                   @RequestParam(defaultValue = "0") Long min,
                                   @RequestParam(required = false) Long max) {
        return contributorService.getTotalContributorsOrTotalFilteredContributors(
                new ContributorPageReq(userId, name, phoneNumber, userStatus, min, max, 0));
    }

    @GetMapping("total-vehicle")
    public int getTheHighestOrLowestTotalVehicleInAllContributors(@RequestParam int option) {
        return contributorService.getTheHighestOrLowestTotalVehicleInAllContributors(option);
    }

    @GetMapping("/{contributor-id}/drivers")
    public UserPageRes getDriversDriveIssuedVehicleOfContributor(@PathVariable("contributor-id") String contributorId,
                                  @RequestParam(required = false) String userId,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String phoneNumber,
                                  @RequestParam(required = false) UserStatus userStatus,
                                  @RequestParam(required = false, defaultValue = "0") int page) {
        return contributorService.getDriversDriveIssuedVehicleOfContributor(
                contributorId,
                new UserPageReq(userId, name, phoneNumber, userStatus, page * 15));
    }

    @GetMapping("/{contributor-id}/drivers/count")
    public int getTotalDriversDriveIssuedVehicleOfContributor(@PathVariable("contributor-id") String contributorId,
                               @RequestParam(required = false) String userId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) UserStatus userStatus) {
        return contributorService.getTotalDriversDriveIssuedVehicleOfContributor(
                contributorId,
                new UserPageReq(
                        userId, name, phoneNumber, userStatus, 0));
    }
}
