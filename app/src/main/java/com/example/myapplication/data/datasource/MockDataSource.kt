package com.example.myapplication.data.datasource

import com.example.myapplication.data.dto.AppItemDto

object MockDataSource {
    val appListDto = listOf(
        AppItemDto(
            id = "1",
            icon = "check_circle_unread_24px",
            title = "СберБанк Онлайн",
            description = "Больше чем банк. Управляйте финансами, платите, копите и инвестируйте. Мгновенные переводы, оплата услуг без комиссии, вклады под высокий процент, кредиты за 2 минуты. Кэшбэк бонусами Спасибо и биометрическая авторизация.",
            category = "Финансы",
            screenshotUrls = listOf(
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg"
            )
        ),
        AppItemDto(
            id = "2",
            icon = "y_circle_24px",
            title = "Яндекс.Браузер — с Алисой",
            description = "Быстрый и безопасный браузер с голосовым помощником Алисой. Умная строка ищет ответы, а не ссылки. Защита от слежки и вредоносных сайтов. Режим Турбо ускоряет загрузку при медленном интернете. Синхронизация закладок и паролей между устройствами",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),
        AppItemDto(
            id = "3",
            icon = "alternate_email_24px",
            title = "Почта Mail.ru",
            description = "Почтовый клиент для любых ящиков: Mail.ru, Яндекс, Gmail, Outlook. Умная сортировка писем, быстрый поиск, push-уведомления, защита от спама и вирусов. Поддержка нескольких аккаунтов одновременно.",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),
        AppItemDto(
            id = "4",
            icon = "assistant_navigation_24px",
            title = "Яндекс Навигатор",
            description = "Парковки и заправки – по пути. Пробки, камеры, аварии. Голосовой помощник Алиса. Детальные карты с адресами и организациями. Маршруты с учётом пробок и перекрытий.",
            category = "Транспорт",
            screenshotUrls = emptyList()
        ),
        AppItemDto(
            id = "5",
            icon = "mts_24px",
            title = "Мой МТС",
            description = "Мой МТС — центр экосистемы МТС: тарифы, минуты, гигабайты, SMS, баланс, пополнение, детализация расходов, подключение услуг, управление подписками, бонусы МТС Premium.",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),
        AppItemDto(
            id = "6",
            icon = "assured_workload_24px",
            title = "Tinkoff",
            description = "Онлайн-банк, кредиты, инвестиции и страховые продукты. Бесплатное обслуживание, кэшбэк рублями, вклады под высокий процент, кредитные и дебетовые карты с доставкой на дом.",
            category = "Финансы",
            screenshotUrls = emptyList()
        ),
        AppItemDto(
            id = "7",
            icon = "avito_24px",
            title = "Avito",
            description = "Покупайте и продавайте товары рядом с домом. Огромная база объявлений: от электроники и одежды до автомобилей и недвижимости. Удобный поиск, безопасная сделка и доставка по всей России.",
            category = "Товары",
            screenshotUrls = emptyList()
        )
    )
}