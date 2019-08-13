package com.inscripts.ins_armman.muw.userProfile;

import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;
import com.inscripts.ins_armman.muw.utility.IBasePresenter;

public interface IuserProfilePresenter<v> extends IBasePresenter<v> {

    public beneficiaries fetchUserDetails();

}
