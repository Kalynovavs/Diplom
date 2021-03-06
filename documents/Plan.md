# Планирование автоматизации

## 1. Перечень автоматизируемых сценариев
      
### Для вкладки "Купить" и "Купить в кредит"

**Предусловия:**
* Запустить приложение;
* Открыть браузер, например, Google Chrome, ввести следующий адрес:http://localhost:8080;
* Выбрать вкладку "Купить" или "Купить в кредит".

**1.	Заполнение формы валидными данными**
* В поле "Номер карты" ввести "4444 4444 4444 4441";
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Операция одобрена Банком".

**2.	В форме указан номер карты, статус которой - DECLINED**
* В поле "Номер карты" ввести 4444 4444 4444 4442;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Ошибка! Банк отказал в проведении операции".

**3.	Пустая форма**
* Оставить все поля формы пустыми;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полями появятся сообщение об ошибке: в поле "Номер карты" - "Неверный формат", в поле "Месяц" - "Неверный формат", в поле "Год" - "Неверный формат", в поле "Владелец" - "Поле обязательно для заполнения", в поле "CVC/CVV" - "Неверный формат"

**4.	Форма, где вместо данных - пробелы**
* В поле "Номер карты" поставить пробел;
* В поле "Месяц" поставить пробел;
* В поле "Год" поставить пробел;
* В поле "Владелец" поставить пробел;
* В поле "CVC/CVV" поставить пробел;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полями появятся сообщение об ошибке: в поле "Номер карты" - "Неверный формат", в поле "Месяц" - "Неверный формат", в поле "Год" - "Неверный формат", в поле "Владелец" - "Поле обязательно для заполнения", в поле "CVC/CVV" - "Неверный формат"

**5.	Форма, где номер карты состоит из одних нулей**
* В поле "Номер карты" ввести 0000 0000 0000 0000;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Ошибка! Банк отказал в проведении операции".

**6.	Форма с невалидным номером карты**
* В поле "Номер карты" ввести 4444 4444 4444 4443;
* В поле "Месяц" вввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Ошибка! Банк отказал в проведении операции".

**7.	Форма, где в номере карт цифр меньше 16**
* В поле "Номер карты" ввести 4444 4444 4444 444_;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Номер карты" появится сообщение об ошибке "Неверный формат".

**8.	Форма с валидным номером карты, где вместо номера месяца два нуля**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести 00;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Месяц" появится сообщение об ошибке "Неверно указан срок действия карты".

**9.	Форма с валидным номером карты, в поле месяц ввести одну цифру**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести 2;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под поле "Месяц" появится сообщение об ошибке "Неверный формат".

**10.	Форма с валидным номером карты и невозможным номером месяца**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести 15;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Месяц" появится сообщение об ошибке "Неверно указан срок действия карты".

**11.	Форма с валидным номером карты, в поле "Год" ввести два нуля**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести 00;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Год" появится сообщение об ошибке "Истёк срок действия карты".

**12.	Форма с валидным номером карты, в поле "Год" ввести одну цифру**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести 5;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Год" появится сообщение об ошибке "Неверный формат".

**13.	Форма с валидным номером карты и значением в поле "Год" больше допустимого**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести 99;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Год" появится сообщение об ошибке "Неверно указан срок действия карты".

**14. Форма с валидным номером карты и значением в поле "Год" меньше допустимого**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести 21;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Год" появится сообщение об ошибке "Истёк срок действия карты".

**15.	Форма с валидным номером карты, где в поле "Владелец" указано только имя**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Операция одобрена Банком".

**16.	Форма с валидным номером карты, где в поле "Владелец" указана только фамилия**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Операция одобрена Банком".

**17.	Форма с валидным номером карты, где в поле "Владелец" стоит одна буква**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" ввести L;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Владелец" появится сообщение об ошибке "Неверный формат".

**18.	Форма с валидным номером карты, где в поле "Владелец" стоит большое количество букв**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" ввести большое количество любых латинских букв, например, 50;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Владелец" появится сообщение об ошибке "Неверный формат".

**19.	Форма с валидным номером карты, где в поле "Владелец" все буквы строчные**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Появится сообщение "Операция одобрена Банком".

**20.	Форма с валидным номером карты, где в поле "Владелец" используются спец символы**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* В поле "Владелец" ввести любые спецсимволы;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Владелец" появится сообщение об ошибке"Неверный формат".

**21.	Форма с валидным номером карты, где в поле "Владелец" используются русские буквы**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на кириллице;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Владелец" появится сообщение об ошибке "Неверный формат".

**22.	Форма с валидным номером карты, где в поле "Владелец" используются цифры**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" ввести 1234567890;
* В поле "CVC/CVV" ввести любые цифры в диапазоне от 000 до 999;
* Нажать на кнопку "Продолжить".

**Ожидаемый результат:**
* Под полем "Владелец" появится сообщение об ошибке "Неверный формат".

**23.	Форма с валидным номером карты, где в поле "CVC/CVV" стоит две цифры**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести 12;
* Нажать на кнопку "Продолжить"

**Ожидаемый результат:**
* Под полем "CVC/CVV" появится сообщение об ошибке "Неверный формат".

**24. Форма с валидным номером карты, где в поле "CVC/CVV" стоит одна цифра**
* В поле "Номер карты" ввести 4444 4444 4444 4441;
* В поле "Месяц" ввести любые цифры в диапазоне от 01 до 12;
* В поле "Год" ввести любые цифры в диапазоне от 22 до 26;
* Поле "Владелец" заполнено с помощью библиотеки генерации данных "Faker" на латинице;
* В поле "CVC/CVV" ввести 1;
* Нажать на кнопку "Продолжить"

**Ожидаемый результат:**
* Под полем "CVC/CVV" появится сообщение об ошибке "Неверный формат".

## 2. Перечень используемых инструментов
### Тесты будут написаны на языке java, т.к. само приложение написано на java, соответственно и вся инфраструктура была выбрана для языка java.
### Весь инструментарий выбран исходя из компетентности разработчика тестов.
* IntelliJ IDEA. IDE для удобного написания кода, в том числе для тестов.
* JUnit. Библиотека для написания и запуска авто-тестов.
* Gradle. Система управления зависимости, которая позволит удобно ставить необходимые фреймворки без проблем с постоянной настройкой зависимостей.
* Selenide. Фреймворк для автоматизированного тестирования веб-приложений.
* Allure. Фреймворк, предназначенный для создания подробных отчетов о выполнении тестов.
* Docker -для развертывания БД.
* Git и Github. Система контроля версий, для хранения кодов автотестов и настроек окружения.
 
## 3. Перечень и описание возможных рисков при автоматизации
 
* Риск появления проблем с настройкой приложения и необходимых БД.
* Риск появления проблем с правильной идентификацией полей ввода. 
* Риск неработающего заявленного функционала приложения.

## 4. Интервальная оценка с учётом рисков (в часах)
* Подготовка окружения, развертывание БД - 8 часов.
* Написание автотестов, тестирование и отладка автотестов -  24 часа.
* Формирование и анализ отчетов - 4 часа. 
 
## 5. План сдачи работ (когда будут авто-тесты, результаты их прогона и отчёт по автоматизации)
1. До 18 апреля - планирование автоматизации тестирования.
1. С 19 по 30 апреля - настройка окружения, написание и отладка автотестов, тестирование.
1. C 1 по 4 мая - подготовка отчетных документов.
1. Отчетные документы по итогам автоматизации: до 06.05.2021
