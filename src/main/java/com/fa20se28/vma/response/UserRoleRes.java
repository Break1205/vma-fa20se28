package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Role;

import java.util.List;

public class UserRoleRes {
    private List<Role> roleList;

    public UserRoleRes(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
