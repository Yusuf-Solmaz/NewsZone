package com.yms.domain.usecase.news

import com.yms.domain.repository.news.NewsRepository

class SearchNews(val newsRepository: NewsRepository){
    operator fun invoke(query: String, sortBy: String?, page: Int, pageSize: Int) = newsRepository.searchNews(query, sortBy, page, pageSize)
}