# 🌤 Прогноз погоды

Приложение для просмотра **текущей погоды** и **прогноза погоды** по выбранным городам с возможностью добавления городов в избранное.

## 📱 Функционал

* 🔍 **Поиск городов**
* 🌡 **Получение текущей погоды** по избранным городам
* 🕐 **Почасовой прогноз** на экране детализации
* 📅 **Прогноз по дням** на экране детализации
* ⭐ **Добавление городов в избранное**
* 🗑 **Удаление городов из избранного**
* 💾 **Хранение избранных городов в локальной БД**

## 🌐 API

Для получения данных о погоде используется **WeatherAPI**:

https://www.weatherapi.com

## 🛠 Технологический стек

| Категория            | Технологии                                      |
| -------------------- | ----------------------------------------------- |
| **Language**         | `Kotlin`                                        |
| **UI**               | `Jetpack Compose`, `Material 3`                 |
| **Architecture**     | `Clean Architecture` + `MVI`                    |
| **State management** | `MVIKotlin`, `StateFlow`                        |
| **Navigation**       | `Decompose`                                     |
| **DI**               | `Dagger 2`                                      |
| **Concurrency**      | `Kotlin Coroutines`, `Flow`                     |
| **Network**          | `Retrofit 3`, `OkHttp`                          |
| **Serialization**    | `kotlinx.serialization`                         |
| **Database**         | `Room`                                          |
| **Images**           | `Glide Compose`                                 |
| **Testing**          | `JUnit 4`, `Coroutines Test`, `Compose UI Test` |
| **Static analysis**  | `Detekt` + custom rules                         |
| **Modules**          | `Multi-module`                                  |


https://github.com/user-attachments/assets/f94e9c03-1273-4f9b-b474-e09611081c73



