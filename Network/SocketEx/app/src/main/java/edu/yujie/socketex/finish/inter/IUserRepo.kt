package edu.yujie.socketex.finish.inter

import androidx.datastore.core.DataStore
import edu.yujie.socketex.SignInStorage

interface IUserRepo {

    val signInDataStore: DataStore<SignInStorage>

}