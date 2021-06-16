package com.ihsan.sona3.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihsan.sona3.data.db.entities.CURRENT_USER_ID
import com.ihsan.sona3.data.db.entities.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User): Completable

    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getuser(): Single<User>

    @Query("DELETE FROM user")
    fun delete()

}