# Diplom-3
## Задание 3: веб-приложение
Нужно протестировать веб-приложение [Stellar Burgers](https://stellarburgers.nomoreparties.site/).

## Использованые технологии
| Название    | Версия   |
|:------------|:---------|
| Java        | 11       |
| JUnit       | 4.13.2   |
| Maven       | 4.0      |
| RestAssured | 4.4.0    |
| Allure      | 2.15.0   |
| Lombok      | 1.18.20  |
| DataFaker   | 1.8.0    |
| GSon        | 2.10.1   |
| Owner       | 1.0.4    |
| Selenium    | 3.141.59 |

## Порядок запуска проекта
1) Перекомпиляция `mvn clean test`
2) Отчет Allure `mvn allure:serve`