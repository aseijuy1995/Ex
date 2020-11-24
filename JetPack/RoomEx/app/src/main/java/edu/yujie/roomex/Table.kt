package edu.yujie.roomex

import androidx.room.*

/**
 * @author YuJie on 2020/11/15
 * @describe 說明
 * @param 參數
 */
@Fts4
@Entity(tableName = "user")
//@Entity(primaryKeys = ["first_name", "last_name"])
//@Entity(ignoredColumns = ["picture"]).
//@Entity(indices = [Index(value = ["first_name", "last_name"], unique = true)])
data class User(
    @PrimaryKey @ColumnInfo(name = "rowid") val userId: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
//    ,
//    @Ignore val picture: Bitmap?,
//    @ColumnInfo(name = "age") val age: Int?,
//    @ColumnInfo(name = "region") val region: String?,
)

@Entity(tableName = "first")
data class First(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(tableName = "second")
data class Second(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)

@DatabaseView(viewName = "firstSecond",
    value = "select first.id, first.name as first_name,second.name as second_name,first.description as first_description,second.description as second_description from first inner join second on first.id = second.id")
data class FirstSecond(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "first_description") val firstDescription: String,
    @ColumnInfo(name = "second_description") val secondDescription: String,
)

@Entity
data class Third(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "description2") val description2: Int,
)

////define nested relationships
//data class UserPlaySong(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "userCreatorId"
//    )
//    val playSongs: List<PlaySong>,
//)


////multiple by multiple
//@Entity(tableName = "song")
//data class Song(
//    @PrimaryKey val songId: Int,
//    val songName: String,
//    val artist: String,
//)
//
//@Entity(primaryKeys = ["playId", "songId"])
//data class PlaySongCrossRef(
//    val playId: Int,
//    val songId: Int,
//)
//
//data class PlaySong(
//    @Embedded val play: Play,
//    @Relation(
//        parentColumn = "playId",
//        entityColumn = "songId",
//        associateBy = Junction(PlaySongCrossRef::class)
//    )
//    val songs: List<Song>,
//)
//
//data class SongPlay(
//    @Embedded val song: Song,
//    @Relation(
//        parentColumn = "songId",
//        entityColumn = "playId",
//        associateBy = Junction(PlaySongCrossRef::class)
//    )
//    val plays: List<Play>,
//
//    )

////1 by multiple
//@Entity(tableName = "play")
//data class Play(
//    @PrimaryKey val playId: Int,
//    val userCreatorId: Long,
//    val playListName: String,
//)
//
//data class UserPlayLists(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "userCreatorId"
//    )
//    val plays: List<Play>,
//)

////1 by 1
//@Entity(tableName = "library")
//data class Library(
//    @PrimaryKey val libraryId: Int,
//    val userOwnerId: Long,
//)
//
//data class UserLibrary(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "userOwnerId"
//    )
//    val library: Library,
//)

//open class User {
//    var picture: Bitmap? = null
//}
//
//@Entity(ignoredColumns = ["picture"])
//data class RemoteUser(
//    @PrimaryKey val id: Int,
//    val hasVpn: Boolean
//) : User()

//@Entity(tableName = "name_tuple")
//data class NameTuple(
//    @ColumnInfo(name = "first_name") val firstName: String,
//    @ColumnInfo(name = "last_name") val lastName: String,
//    @ColumnInfo(name = "region") val region: String,
//)
//
//@Entity(tableName = "book")
//data class Book(val id: Int)
//
//@Entity(tableName = "loan")
//data class Loan(
//    val id: Int,
//    @ColumnInfo(name = "user_id") val userId: Int,
//)
//
//@Entity(tableName = "user_pet")
//data class UserPet(val id: Int, val userName: String?, val petName: String?)
