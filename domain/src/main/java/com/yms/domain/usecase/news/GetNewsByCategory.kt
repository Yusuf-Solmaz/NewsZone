package com.yms.domain.usecase.news

import com.yms.domain.repository.news.NewsRepository

class GetNewsByCategory (val newsRepository: NewsRepository){
    operator fun invoke(category: String?, page: Int, pageSize: Int) = newsRepository.getNewsByCategory(category, page, pageSize, source = null)

}