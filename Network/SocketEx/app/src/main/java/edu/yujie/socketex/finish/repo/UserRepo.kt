package edu.yujie.socketex.finish.repo

import androidx.datastore.core.DataStore
import edu.yujie.socketex.SignInStorage
import edu.yujie.socketex.finish.data.IPreferenceDataStoreStorage
import edu.yujie.socketex.finish.inter.IUserRepo

class UserRepo(private val preferenceDataStoreStorage: IPreferenceDataStoreStorage) : IUserRepo {

    override val signInDataStore: DataStore<SignInStorage>
        get() = preferenceDataStoreStorage.signInDataStore

}