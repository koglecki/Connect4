# Connect4 (gra)

## Zasady gry
Celem gry jest ułożenie ciągu czterech swoich żetonów poziomo, pionowo lub na ukos. Wygrywa gracz, który ułoży je jako pierwszy.

<img src="https://github.com/koglecki/Connect4/assets/122780250/1a5b608d-ad74-44da-8293-4bdd8388dc30" width="500" />

Żetony kładzie się poprzez kliknięcie w odpowiednią kolumnę planszy. Żetony są umieszczane od dołu.
W grze występuje "reguła sufitu" tzn. jeżeli gracz położy swój żeton w ostatnim, najwyższym wierszu, to automatycznie zwycięża.
Przed rozpoczęciem rozgrywki można ustawić rozmiar planszy do gry, wybrać gracza rozpoczynającego, a także wybrać kolor gracza.

<img src="https://github.com/koglecki/Connect4/assets/122780250/ae4e4223-79e1-4ec2-8bb2-b3bb25ea914f" width="400" />


## Informacje o projekcie
Cały projekt był tworzony z wykorzystaniem środowiska IntelliJ IDEA w Javie (wersja 15).
Do stworzenia gry zostały wykorzystane biblioteki: [SaC](https://pklesk.github.io/sac/) (do grafów i drzew gier) oraz [Processing](https://processing.org/) (intefejs graficzny).
Plik wykonywalny .jar znajduje się w folderze _out/artifacts/SI_jar_.
Aby uruchomić grę z poziomu kodu, należy uruchomić _Controller.java_.
