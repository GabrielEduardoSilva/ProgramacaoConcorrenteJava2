
# AT2/N2 - ATIVIDADE PRÁTICA COLETIVA 2
> Experimento de uso de threads em Java.

## Visão geral

O experimento consiste em um projeto (em Java 17) que representa um sistema de busca distribuído utilizando sockets. O sistema é formado por dois servidores (A e B) cada um responsável por uma busca em metade de um dado de artigos científicos do arXiv (https://paperswithcode.com/dataset/arxiv-10).

O servidor A recebe a solicitação de busca do cliente, que contém uma substring de um possível título ou introdução de um artigo, envia ao servidor B e em seguida ambos realizam a busca no dado designado a cada um.

Após a busca, o servidor A envia ao cliente o resultado da busca.

Para a busca, o algoritmo escolhido foi o algoritmo de Knuth-Morris-Pratt (KMP). O KMP tem complexidade de tempo O(n + m), onde m é o tamanho do padrão e n é o tamanho do texto. Ele evita a reavaliação de caracteres já comparados. E no pior caso, o desempenho pode ser degradado para O(m * n), o que é previsível mesmo nos piores cenários. Este algoritmo se torna ideal para aplicações que o padrão é relativamente pequeno e o texto é extenso e requer várias verificações. 



## Configurações

Experimento realizado no IntelliJ IDEA 2024.1.1 (Ultimate Edition)

Configurações do sistema operacional:

<img width="272" alt="image" src="https://github.com/user-attachments/assets/817c14e5-ce91-4bfb-b1db-e698971a0e85">



## Arquivo PDF do relatório

