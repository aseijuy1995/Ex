package edu.yujie.roomex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author YuJie on 2020/11/16
 * @describe 說明
 * @param 參數
 */
@Dao
interface MyDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUser(vararg users: User)
//
//    @Insert
//    fun insertBothUser(user1: User, user2: User)
//
//    @Insert
//    fun insertUserAndFriends(user: User, friends: List<User>)
//
//    @Update
//    fun updateUsers(vararg users: User)
//
//    @Delete
//    fun deleteUsers(vararg users: User)
//
//    @Query("select * from user")
//    fun loadAllUser(): Array<User>
//
//    @Query("select * from user where user.age > :minAge")
//    fun loadAllUsersOlderThen(minAge: Int): Array<User>
//
//    @Query("select * from user where age between :minAge and :maxAge")
//    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>
//
//    @Query("select * from user where first_name like :search or last_name like :search")
//    fun findUserWithName(search: String): Array<User>

//    @Query("select first_name, last_name from name_tuple")
//    fun loadFullName(): List<NameTuple>
//
//    @Query("select * from name_tuple where region in (:resions)")
//    fun loadUserFromRegions(resions: List<String>): List<NameTuple>
//
//    @Query("select * from user where age > :minAge")
//    fun loadRawUsersOlderThan(minAge: Int): Cursor
//
//    @Query("select * from book inner join loan on loan.id = book.id inner join user on user.userId = loan.user_id")
//    fun findBooksBorrowedByNameSync(userName: String): List<Book>
//
//    @Query("select user.first_name as userName, pet.petName as petName from user_pet pet, user  where user.userId = pet.id")
//    fun loadUserAndPetNames(): LiveData<List<UserPet>>

//    @Query("select * from user where first_name = :userName")
//    abstract fun getUser(userName: String): Flow<User>
//
//    fun getUserDistinctUntilChanged(username: String) = getUser(username).distinctUntilChanged()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUsers(vararg users: User)
//
//    @Update
//    suspend fun updateUsers2(vararg users: User)
//
//    @Delete
//    suspend fun deleteUsers2(vararg users: User)
//
//    @Query("select * from user")
//    suspend fun loadAllUsers(): Array<User>
//
//    @Query("select first_name,last_name from user where region in (:rejions)")
//    fun loadUsersFromRegionsSync(rejions: List<S|tring>): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFirst(vararg first: First)

    @Query("select * from first")
    fun queryFirst(): Flow<List<First>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSecond(vararg second: Second)

    @Query("select * from second")
    fun querySecond(): Flow<List<Second>>

    @Query("select * from firstSecond limit :count")
    fun queryFirstSecond(count: Int): Flow<List<FirstSecond>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThird(vararg third: Third)

    @Query("select * from third")
    fun queryThird(): Flow<List<Third>>

}