# Java RPG

This is a console-based RPG for engineer education.

## Features

*   Console-based RPG
*   Built with Java and Gradle
*   Uses Gson for data handling

## Requirements

*   Java 21.0.5 or higher

## Installation and Build

1.  Clone the repository:
    ```sh
    git clone <repository-url>
    cd java-rpg
    ```

2.  Build the project:
    ```sh
    ./gradlew build
    ```

3.  Set the language option:
    ```sh
    file: app/main/resouces/game.properties
    ...
    language=[ja|en]
    ...
    ```

## How to Run

Execute the following command to run the application:

```sh
./gradlew run

let's start!
冒険の書・第一章を開始します。

M-ennc--ci---i--i--i---------------------------n---i---n---*
|      c  ici    i n i i            i                      |
iine n i   n  i ii |   n *---eE-eiei-Eiei-i-e              i
|    eii in  e    en     eEiiee eeiin    EEEEn          n  |
ice-i--ii---c-ien--*     | eiie  en i e  e  |i             |
|                  i     |eie  e   e i    ieE    n        i|
|      ii                eeEi---i-i--i-eeiei*              |
|  n                          n    i   i                   |
| i            n       n      Ei--c--i-Ee-i--eeE-i       i |
|           i                 | c cie EE e iiie iE         |
|  i         n              n | i n  i    en i  E|         |
|     i  in    n         in   E  ie   e i en e Eei         |
|i         i             i    *---i--e-eec-c--iiie n       |
|n                   n            n   niin                 |
*---------------------n----nn----------------i-------------*
M:勇者パーティ、i:アイテム、c:キャラクター、n:NPキャラクター、e:モンスター、E:モンスターパーティ

勇者パーティ(0,0) Money:10000
Items:薬草x 1
メンバー：
勇者[Gladiator] LEVEL:1 EX:0/50
HP:100/100 MP:100/100 AK:10 DF:10 MA:10 MD:10 AG:1 DX:1 EV:1 CR:1

どちらへ行きますか？
4:西  8:北  6:東  2:南  0:終了
勇者>
```

## License

This project is licensed under the Mozilla Public License Version 2.0.
*   US: https://www.mozilla.org/en-US/MPL/2.0/
*   JP: https://www.mozilla.jp/documents/mpl/2.0/

