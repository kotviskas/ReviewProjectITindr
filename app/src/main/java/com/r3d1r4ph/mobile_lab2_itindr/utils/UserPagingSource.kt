//package com.r3d1r4ph.mobile_lab2_itindr.utils
//
//import android.util.Log
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.r3d1r4ph.mobile_lab2_itindr.database.AppDatabase
//import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity
//import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
//import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.UserController
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import retrofit2.HttpException
//
//todo
//class UserPagingSource(
//    val database: AppDatabase,
//    val mediator: UserRemoteMediator
//) : PagingSource<Int, UserEntity>() {
//    companion object {
//        private const val INITIAL_LOAD_SIZE = 1
//        const val PAGE_SIZE = 9
//    }
//    private val userDao = database.getUserDao()
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
//        try {
//            val position = params.key ?: INITIAL_LOAD_SIZE
//            val offset =
//                if (params.key != null) ((position - 1) * PAGE_SIZE) else 0
//
////            val response = userController.user(
////                limit = params.loadSize,
////                offset = offset,
////                onSuccess = {},
////                onFailure = {}
////            )
//            val response = withContext(Dispatchers.IO) {
//                 userDao.getUserWithParameters(params.loadSize, offset)
//            }
//            mediator.offset = offset
//
////            if (offset >= 0 && position > 0)
////            Log.d("LOAGDATA", "${response.body()?.size},$offset, $position")
////            return LoadResult.Page(
////                data = response.body() ?: listOf(),
////                prevKey = if (response.body()
////                        ?.isEmpty() == true
////                ) null else position - 1,
////                nextKey = if (response.body()
////                        ?.isEmpty() == true
////                ) null else position + (params.loadSize / PAGE_SIZE)
//            if (offset >= 0 && position > 0)
//            Log.d("LOAGDATA", "${response.size},$offset, $position")
//            return LoadResult.Page(
//                data = response,
//                prevKey = null,
//                nextKey = if (response.isEmpty()
//                ) null else position + (params.loadSize / PAGE_SIZE)
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        } catch (e: HttpException) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
//        Log.d("LOAGDATA", "ref ${state.anchorPosition}")
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}