package com.example.myapplication.data.repository

import com.example.myapplication.data.appdetails.AppDetailsDao
import com.example.myapplication.data.appdetails.AppDetailsEntity
import com.example.myapplication.data.appdetails.AppDetailsEntityMapper
import com.example.myapplication.network.api.ApiService
import com.example.myapplication.network.dto.CatalogItemDto
import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.appdetails.Category
import com.example.myapplication.domain.repository.AppDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val dao: AppDetailsDao,
    private val apiService: ApiService,
    private val entityMapper: AppDetailsEntityMapper
) : AppDetailsRepository {

    override suspend fun getAppDetails(id: String): AppDetails? = withContext(Dispatchers.IO) {
        val entity = dao.getAppDetails(id).firstOrNull()
        if (entity != null) {
            return@withContext entityMapper.toDomain(entity)
        } else {
            val dto = try {
                apiService.getAppDetails(id)
            } catch (e: Exception) {
                return@withContext null
            }
            val newEntity = mapDtoToEntity(dto)
            dao.insertAppDetails(newEntity)
            return@withContext entityMapper.toDomain(newEntity)
        }
    }

    override fun observeAppDetails(id: String): Flow<AppDetails> = channelFlow {
        withContext(Dispatchers.IO) {
            var entity = dao.getAppDetails(id).firstOrNull()
            if (entity == null) {
                val dto = try {
                    apiService.getAppDetails(id)
                } catch (e: Exception) {
                    close(e)
                    return@withContext
                }
                entity = mapDtoToEntity(dto)
                dao.insertAppDetails(entity)
            }

            send(entityMapper.toDomain(entity))

            dao.getAppDetails(id).collect { updatedEntity ->
                updatedEntity?.let { send(entityMapper.toDomain(it)) }
            }
        }
    }

    override suspend fun toggleWishlist(id: String) = withContext(Dispatchers.IO) {
        var entity = dao.getAppDetails(id).firstOrNull()
        if (entity == null) {
            val dto = try {
                apiService.getAppDetails(id)
            } catch (e: Exception) {
                return@withContext
            }
            entity = mapDtoToEntity(dto)
            dao.insertAppDetails(entity)
        }
        dao.updateWishlistStatus(id, !entity.isInWishlist)
    }

    private fun mapDtoToEntity(dto: CatalogItemDto): AppDetailsEntity {
        return AppDetailsEntity(
            id = dto.id,
            name = dto.name,
            developer = dto.developer ?: "Неизвестный разработчик",
            category = mapCategory(dto.category),
            ageRating = dto.ageRating ?: 0,
            size = dto.size ?: 0f,
            iconUrl = dto.iconUrl,
            screenshots = dto.screenshotUrls?.joinToString(","),
            description = dto.description,
            lastUpdated = System.currentTimeMillis(),
            isInWishlist = false
        )
    }

    private fun mapCategory(categoryString: String): Category {
        return when (categoryString) {
            "Производительность" -> Category.PRODUCTIVITY
            "Здоровье и фитнес" -> Category.HEALTH
            "Фото и видео" -> Category.PHOTOGRAPHY
            "Еда и напитки" -> Category.FOOD
            "Образование" -> Category.EDUCATION
            "Образ жизни" -> Category.OTHER
            "Шопинг" -> Category.SHOPPING
            "Новости" -> Category.NEWS
            "Музыка" -> Category.MUSIC
            "Игры" -> Category.GAME
            "Финансы" -> Category.FINANCE
            "Утилиты" -> Category.UTILITIES
            "Навигация" -> Category.MAPS
            "Общение" -> Category.SOCIAL
            "Бизнес" -> Category.BUSINESS
            "Погода" -> Category.OTHER
            "Развлечения" -> Category.ENTERTAINMENT
            "Книги и справочники" -> Category.BOOKS
            else -> Category.OTHER
        }
    }
}