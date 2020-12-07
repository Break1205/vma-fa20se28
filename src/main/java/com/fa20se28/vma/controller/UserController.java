package com.fa20se28.vma.controller;

import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.response.UserRoleRes;
import com.fa20se28.vma.response.UserStatusesRes;
import com.fa20se28.vma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createUser(@RequestBody UserReq userReq,
                          @RequestParam Long roleId) {
        return userService.createUser(userReq, roleId);
    }

    @PutMapping
    public void updateUser(@RequestBody UserReq userReq) {
        userService.updateUser(userReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(String userId) {
        userService.deleteUserByUserId(userId);
    }

    @PatchMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserStatusByUserId(@RequestParam UserStatus userStatus,
                                         @PathVariable("user-id") String userid) {
        userService.updateUserStatusByUserId(userStatus, userid);
    }

    @PatchMapping("/{user-id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewRoleForUserId(@RequestParam Long roleId,
                                    @PathVariable("user-id") String userId) {
        if (roleId != null) {
            userService.addNewRoleForUser(roleId, userId);
        }
    }

    @PostMapping("/role-token")
    public UserRoleRes getUserRoles(@RequestBody UserTokenReq token) {
        return userService.getUserRoles(token);
    }

    @GetMapping("/roles/{role-id}")
    public UserPageRes getUsersWithOneRoleByRoleId(@PathVariable("role-id") String roleId,
                                                   @RequestParam(required = false) String userId,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String phoneNumber,
                                                   @RequestParam(required = false) UserStatus userStatus,
                                                   @RequestParam(required = false, defaultValue = "0") int page) {
        if (roleId.equals("1")) {
            throw new InvalidParamException("Does not support role with id: " + roleId);
        } else {
            return userService.getUsersWithOneRoleByRoleId(
                    roleId, new UserPageReq(userId, name, phoneNumber, userStatus, page * 15));
        }
    }

    @GetMapping("/roles/{role-id}/count")
    public int getTotalUsersWithOneRoleByRoleId(@PathVariable("role-id") String roleId,
                                                @RequestParam(required = false) String userId,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String phoneNumber,
                                                @RequestParam(required = false) UserStatus userStatus) {
        if (roleId.equals("1")) {
            throw new InvalidParamException("Does not support role with id: " + roleId);
        } else {
            return userService.getTotalUserWithOneRoleByRoleId(
                    roleId, new UserPageReq(userId, name, phoneNumber, userStatus, 0));
        }
    }

    @GetMapping("/user-status")
    public UserStatusesRes getUserStatuses() {
        return new UserStatusesRes();
    }

    @PostMapping("/registration-token")
    public int createClientRegistrationToken(@RequestBody ClientRegistrationToken clientRegistrationToken) {
        return userService.updateClientRegistrationToken(clientRegistrationToken);
    }
}
