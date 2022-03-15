package com.r3d1r4ph.mobile_lab2_itindr.utils

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.r3d1r4ph.mobile_lab2_itindr.database.AppDatabase
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.remotekey.RemoteKeyEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.UserService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: AppDatabase,
    private val networkService: UserService
) : RemoteMediator<Int, UserEntity>() {

    companion object {
        const val PAGE_SIZE = 12
        private const val DEFAULT_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            Log.d("LOAGDATA", "mediator START")
            val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
                is MediatorResult.Success -> {
                    Log.d("LOAGDATA", "mediator RETURN PAGEKEYDATA: $pageKeyData")
                    return pageKeyData
                }
                else -> {
                    pageKeyData as Int
                }
            }
            Log.d("LOAGDATA", "mediator page key data is int: $page")

            var networkResponse = networkService.user(
                limit = state.config.pageSize,
                offset = (page - 1) * state.config.pageSize
            )

            if (networkResponse is ResultWrapper.Error) {
                throw IOException("Data loading failed")
            } else {
                networkResponse = networkResponse as ResultWrapper.Success
            }

            networkResponse.value?.let { response ->

                Log.d(
                    "LOAGDATA",
                    "mediator RESPONSE COUNT: ${response.size}, OFFSET: ${(page - 1) * state.config.pageSize}, LIMIT: ${state.config.pageSize}"
                )
                val isEndOfList = response.isEmpty()
                Log.d("LOAGDATA", "mediator END OF LIST: $isEndOfList")
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.getUserDao().clearAll()
                        database.getRemoteKeyDao().clearAll()
                    }
                    val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val keys = response.map {
                        RemoteKeyEntity(
                            userId = it.userId,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    val users = response.map { it.toEntity() }
                    database.getRemoteKeyDao().insertAll(keys)
                    database.getUserDao().insertAll(users)
                }
                Log.d(
                    "LOAGDATA",
                    "mediator transaction completed, result: endOfPaginationReached = $isEndOfList"
                )
                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
            Log.d("LOAGDATA", "mediator RESPONSE ERROR")
            throw IOException("Data loading failed")
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, UserEntity>): Any? {
        Log.d("LOAGDATA", "mediator loadtype = $loadType")
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getClosestRemoteKey(state)
                remoteKey?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                //Log.d("LOAGDATA", "mediator state = ${state.pages}")
                val remoteKey = getLastRemoteKey(state)
                    ?: return MediatorResult.Success(endOfPaginationReached = false)// throw InvalidObjectException("Remote key should nor be null for $loadType")
                remoteKey.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state)
                // ?: throw InvalidObjectException("Invalid state, key should not be null")
                remoteKey?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKey.prevKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        val ret = state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user ->
                database.getRemoteKeyDao().remoteKeyById(user.userId)
            }
        Log.d("LOAGDATA", "mediator func ret = $ret")
        return ret
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        val ret = state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { user -> database.getRemoteKeyDao().remoteKeyById(user.userId) }
        Log.d("LOAGDATA", "mediator func ret = $ret")
        return ret
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        val ret = state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.userId?.let { userId ->
                database.getRemoteKeyDao().remoteKeyById(userId)
            }
        }
        Log.d("LOAGDATA", "mediator func ret = $ret")
        return ret
    }
}
//todo
//override suspend fun load(
//    loadType: LoadType,
//    state: PagingState<Int, UserEntity>
//): RemoteMediator.MediatorResult {
//    return try {
//        Log.d("LOAGDATA", "mediator CALLED with loadtype: $loadType")
//        val loadKey = when (loadType) {
//            LoadType.REFRESH -> null
//            LoadType.PREPEND ->
//                return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
//            LoadType.APPEND -> {
//                val lastItem = state.lastItemOrNull()
//                Log.d("LOAGDATA", "mediator LASTITEM: $lastItem")
//                lastItem ?: return RemoteMediator.MediatorResult.Success(
//                    endOfPaginationReached = true
//                )
//
//                offset
//            }
//        }
//        Log.d("LOAGDATA", "mediator LOADKEY: $loadKey")
//
//        val response = networkService.user(
//            limit = when (loadType) {
//                LoadType.REFRESH -> state.config.initialLoadSize
//                else -> state.config.pageSize
//            },
//            offset = when (loadType) {
//                LoadType.REFRESH -> 0
//                else -> loadKey ?: 0
//            },
//            {},
//            {}) //
//        offset += when (loadType) {
//            LoadType.REFRESH -> state.config.initialLoadSize
//            else -> state.config.pageSize
//        }
//
//        Log.d("LOAGDATA", "mediator RESPONSE COUNT: ${response.body()?.size}")
//
//        database.withTransaction {
//            response.body()?.let { list ->
//                list.map { it.toEntity() }.also { entityList ->
//                    if (loadType == LoadType.REFRESH) {
//                        userDao.deleteUserList(entityList)
//                    }
//                    userDao.addUserList(entityList)
//                }
//            }
//            Log.d("LOAGDATA", "CHECK")
//        }
//        Log.d("LOAGDATA", "mediator endOfPaginationReached: ${response.body()?.isEmpty() ?: true}")
//        RemoteMediator.MediatorResult.Success(
//            endOfPaginationReached = response.body()?.size ?: 0 < state.config.pageSize
//        )
//
//    } catch (e: IOException) {
//        RemoteMediator.MediatorResult.Error(e)
//    } catch (e: HttpException) {
//        RemoteMediator.MediatorResult.Error(e)
//    }
//
//
//}