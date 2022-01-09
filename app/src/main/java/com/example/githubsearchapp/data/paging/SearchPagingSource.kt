package com.example.githubsearchapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearchapp.data.remote.SearchRemoteDataSource
import com.example.githubsearchapp.data.vo.Repo
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX= 1

class SearchPagingSource(
    private val query:String,
    private val remote: SearchRemoteDataSource,
) : PagingSource<Int, Repo>() {

    companion object {
        private const val TAG = "SearchPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?:STARTING_PAGE_INDEX
        return try {
            val repos = remote.getRepoList(query, position, params.loadSize).items
            Log.d("결과값쿼리2", query)
            Log.d("결과값체크", repos.toString())
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            Log.e(TAG, "IOException occurred", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTPException occurred", e)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load repositories", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let{anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}