# Веб прилоджение для бургерной

Передо мной была следующая задание(курсовая работа по базам данных), нужно было по случайно выпавшей базе данных, составленной студентами с моего потока, с условным описание и возможностями сделать приложение, веб, десктопное, на выбор.  
Мой выбор, как java-developer`а, остановился на веб приложении с использование Spring Framework.<br>

# Описание задачи и предъявляемые требования
**Техническое описание выпавшей мне ситуации:**

![image](https://github.com/user-attachments/assets/93481a4a-a3ee-4215-b268-75e5aa751477)

*Естественно, мне пришлось перебаотать базу данных под себя,чтобы было комфортнее работать*

**В работе мне предъявлялись следующие требования от преподавателя:**

![бд1](https://github.com/user-attachments/assets/cb769e28-189c-46b1-b448-6423b725af1d)

![image](https://github.com/user-attachments/assets/fb86a446-1f2b-4ba8-aa2f-b49ccbf3ad83)

**ER-диаграмма базы данных**

![image](https://github.com/user-attachments/assets/66da3262-7835-4f31-8097-ca82af4ca0fc)


**Физическая модель базы данных**
![image](https://github.com/user-attachments/assets/f1f3cc65-5399-4e99-8011-cd6e5a244f21)


## Технические возможности 

### Эндпоинты
![image](https://github.com/user-attachments/assets/48cba65b-c95d-4df7-b0f5-671a9bae5214)

### Дизайн сайта

 Дизайн написан мной самостоятельно, только лишь с применение HTML + CSS
 Также была использавана адаптивная верстка с применением JS скриптов, что позволяет при фетч запросах не перезагружать страницу, для отображения новых или обновленных данных.
 
### Главная страничка, регистарция и авторизация.

![image](https://github.com/user-attachments/assets/df35e46c-be7a-4644-a108-edf0f678510a)
![image](https://github.com/user-attachments/assets/a4cd6d1d-202a-4af3-aa9b-95948d67a034)
![image](https://github.com/user-attachments/assets/d911baa1-28f7-4254-9d83-34a89d929ffc)


### Если зашёл клиент, к примеру, после регистрации, возможности:

![image](https://github.com/user-attachments/assets/76981d62-1ca5-4b89-acf0-140b683e0d17)
![image](https://github.com/user-attachments/assets/6a50b9bc-e768-4c20-9c43-b29101765eca)


### Если зашёл админ(поставлять ингредиенты, добавлять новый ингредиент для бургера):

![image](https://github.com/user-attachments/assets/5a3e82af-9423-4d6a-b202-9871a12b580f)
![image](https://github.com/user-attachments/assets/51dff513-5e84-48cf-aa27-5e847071cf84)


### Админ также может делать заказ

![image](https://github.com/user-attachments/assets/07bc494a-a515-4b1b-a0dc-51cb06c0132a)


### Может добавить новый бургер

![image](https://github.com/user-attachments/assets/dcdd6d23-cfe2-4103-831f-1f6f4a4334b5)


### Может назначить сотрудника на заказ, посчитать обшую выручку и удалить заказ, в случае отмены

![image](https://github.com/user-attachments/assets/4c1945a0-5ef1-468f-a58f-89b5bb028abd)


### На всех этапах предусмотрена валидация, с последующим уведомление пользователя, если что-то сделано не так, созданы свои собственные классы ошибок для некоторых ситуаций.
