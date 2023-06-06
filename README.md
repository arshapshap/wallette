# Wallette
  Мобильное приложение для управления финансами, позволяющее вести учёт и контролировать свои расходы и доходы.
  
## Функционал

* Сохранение информации о доходах, расходах и переводах;
* Просмотр статистики по сохраненным данным;
* Создание, редактирование и удаление счетов, категорий и меток;
* Сохранение данных в БД приложения;
* Синхронизация данных с сервером;

## Используемые технологии

* Kotlin
* Android SDK
* Clean Architecture
* Многомодульность
* Корутины
* [MVVM, LiveData](https://github.com/arshapshap/wallette/blob/master/feature-statistics-impl/src/main/java/com/example/feature_statistics_impl/presentation/screen/transactionsList/TransactionsFragment.kt)
* [Dagger 2](https://github.com/arshapshap/wallette/blob/master/app/src/main/java/com/example/wallette/di/app/AppComponent.kt)
* [Room](https://github.com/arshapshap/wallette/blob/master/core-db/src/main/java/com/example/core_db/AppDatabase.kt)
* [Retrofit](https://github.com/arshapshap/wallette/blob/master/core-network/src/main/java/com/example/core_network/di/NetworkModule.kt)
* [Navigation Component](https://github.com/arshapshap/wallette/blob/master/app/src/main/java/com/example/wallette/navigation/Navigator.kt)
* [SharedPreferences](https://github.com/arshapshap/wallette/blob/master/core-network/src/main/java/com/example/core_network/data/managers/TokenManagerImpl.kt)
* [Gson](https://github.com/arshapshap/wallette/blob/master/data/src/main/java/com/example/data/mappers/SyncRequestMapper.kt)
