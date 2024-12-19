package com.yms.data.repository.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yms.data.mapper.NewsMapper.toArticle
import com.yms.data.remote.NewsApi
import com.yms.domain.model.news.ArticleData

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val category: String?,
    private val source: String?,
    private val query: String?
) : PagingSource<Int, ArticleData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        val currentPage = params.key ?: 1
        return try {
            val response = if (query.isNullOrBlank()) {
                newsApi.getNewsByCategory(
                    category = category,
                    source = source,
                    page = currentPage,
                    pageSize = params.loadSize
                )
            } else {
                newsApi.searchNews(
                    query = query,
                    page = currentPage,
                    pageSize = params.loadSize
                )
            }

            val articles = response.articleDtos?.map { it.toArticle() } ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (articles.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
