package com.inscripts.ins_armman.npdsf.userProfile;

import com.inscripts.ins_armman.npdsf.data.model.syncing.beneficiaries;
import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

public interface IuserProfilePresenter<v> extends IBasePresenter<v> {

    public beneficiaries fetchUserDetails();

}
