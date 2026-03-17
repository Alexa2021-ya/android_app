package com.example.myapplication

object MockData {
    val appList = listOf(
        AppItem(
            id = "1",
            icon = "check_circle_unread_24px", // Имя ресурса как строка
            title = "СберБанк Онлайн",
            description = "Больше чем банк. Управляйте финансами...",
            category = "Финансы",
            screenshotUrls = listOf(
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg"
            )
        ),

        AppItem(
            id = "2",
            icon = "y_circle_24px", // Имя ресурса как строка
            title = "Яндекс.Браузер — с Алисой",
            description = "Быстрый и безопасный браузер...",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),

        AppItem(
            id = "3",
            icon = "alternate_email_24px",
            title = "Почта Mail.ru",
            description = "Почтовый клиент для любых ящиков...",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),

        AppItem(
            id = "4",
            icon = "assistant_navigation_24px",
            title = "Яндекс Навигатор",
            description = "Парковки и заправки – по пути...",
            category = "Транспорт",
            screenshotUrls = emptyList()
        ),

        AppItem(
            id = "5",
            icon = "mts_24px",
            title = "Мой МТС",
            description = "Мой МТС — центр экосистемы МТС...",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),

        AppItem(
            id = "6",
            icon = "assured_workload_24px",
            title = "Tinkoff",
            description = "Онлайн-банк, кредиты, инвестиции...",
            category = "Финансы",
            screenshotUrls = emptyList()
        ),

        AppItem(
            id = "7",
            icon = "avito_24px",
            title = "Avito",
            description = "Покупайте и продавайте товары рядом...",
            category = "Товары",
            screenshotUrls = emptyList()
        )
    )
}