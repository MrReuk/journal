INSERT INTO Role_Users (name)
SELECT 'Администратор' WHERE NOT EXISTS (SELECT 1 FROM Role_Users WHERE name = 'Администратор')
UNION ALL
SELECT 'Пользователь' WHERE NOT EXISTS (SELECT 1 FROM Role_Users WHERE name = 'Пользователь');

INSERT INTO Users (surname, name, middle_name, login, password, role_ID)
SELECT 'Иванов', 'Иван', 'Иванович', 'admin', '$2a$10$PmKMaLjc3gT1M19uQ3b6m.jthQN.5nkvfgQGx8c4bHeyAnLlSpTkS', 1
    WHERE NOT EXISTS (SELECT 1 FROM Users WHERE login = 'admin')
UNION ALL
SELECT 'Петров', 'Петр', 'Петрович', 'user', '$2a$10$f1hLXApm8qv6gQzVGKijB.YoQMgL5lZLGfJYctsWOkbSXM3W5f4.u', 2
    WHERE NOT EXISTS (SELECT 1 FROM Users WHERE login = 'user');

INSERT INTO Type_pay (name)
SELECT 'Наличные' WHERE NOT EXISTS (SELECT 1 FROM Type_pay WHERE name = 'Наличные')
UNION ALL
SELECT 'Безналичные' WHERE NOT EXISTS (SELECT 1 FROM Type_pay WHERE name = 'Безналичные')
UNION ALL
SELECT 'Кредитная карта' WHERE NOT EXISTS (SELECT 1 FROM Type_pay WHERE name = 'Кредитная карта');

INSERT INTO Tree_species (name, characteristics)
SELECT 'Сосна', 'Мягкая древесина, светлый цвет' WHERE NOT EXISTS (SELECT 1 FROM Tree_species WHERE name = 'Сосна')
UNION ALL
SELECT 'Дуб', 'Твердая древесина, устойчива к влаге' WHERE NOT EXISTS (SELECT 1 FROM Tree_species WHERE name = 'Дуб')
UNION ALL
SELECT 'Береза', 'Средней твердости, белый цвет' WHERE NOT EXISTS (SELECT 1 FROM Tree_species WHERE name = 'Береза');

INSERT INTO Type_of_lumber (name, area_of_speciesion)
SELECT 'Доска обрезная', 'Строительство' WHERE NOT EXISTS (SELECT 1 FROM Type_of_lumber WHERE name = 'Доска обрезная')
UNION ALL
SELECT 'Брус', 'Несущие конструкции' WHERE NOT EXISTS (SELECT 1 FROM Type_of_lumber WHERE name = 'Брус')
UNION ALL
SELECT 'Вагонка', 'Отделка' WHERE NOT EXISTS (SELECT 1 FROM Type_of_lumber WHERE name = 'Вагонка');