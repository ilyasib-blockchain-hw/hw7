## Docs

```
Usage: pure-cryptos [--file <path>] [--numbilets  <integer>] [--parameter <integer>]

Pure app to encode student task number by full name.

Options and flags:
    --help
        Display this help text.
    --version, -v
        Print the version number and exit.
    --file <path>, -f <path>
        Name of input file.
    --numbilets  <integer>, -n <integer>
        Number of tickets.
    --parameter <integer>, -p <integer>
        Distribution parameter (Salt).
```

## Example
```
pure-cryptos -f input.txt -n 20 -p 42
```

### Output
```s
Комаров Фёдор Александрович: 14
Макаров Олег Даниилович: 14
Евдокимов Дмитрий Дмитриевич: 4
Кузнецова София Глебовна: 13
Худякова Вероника Ивановна: 17
Бочаров Никита Альбертович: 5
Анисимова Валерия Николаевна: 17
Малышева Милана Кирилловна: 4
Петухов Роман Янович: 11
Иванов Максим Егорович: 16
Никольская Софья Максимовна: 2
Куликов Аркадий Иванович: 1
Прокофьев Тимур Алиевич: 3
Баранов Глеб Даниилович: 15
Глухова Есения Никитична: 7
Грачева Софья Андреевна: 6
Курочкин Алексей Ярославович: 10
Анисимов Артём Семёнович: 12
Попов Даниил Ярославович: 4
Евсеева Александра Максимовна: 15
```