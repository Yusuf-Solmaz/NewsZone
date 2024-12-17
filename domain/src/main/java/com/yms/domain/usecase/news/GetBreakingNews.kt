package com.yms.domain.usecase.news


import com.yms.domain.repository.news.NewsRepository

class GetBreakingNews (val newsRepository: NewsRepository){
    operator fun invoke(page: Int, pageSize: Int) = newsRepository.getNewsByCategory(category = null ,page = 1, pageSize, source = "cnn")
}