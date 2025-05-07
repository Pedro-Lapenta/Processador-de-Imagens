# 🖼️ Processador de Imagens em Java

Este projeto consiste em uma interface gráfica desenvolvida em Java que permite carregar imagens e aplicar uma série de filtros e transformações. Foi implementado como parte da disciplina de **Processamento Digital de Imagens**, ministrada pelo professor **Almir Olivette Artero**, no **5º semestre do curso de Ciência da Computação da FCT UNESP**.

---

## 🎯 Objetivo

Oferecer uma aplicação simples e interativa para o processamento digital de imagens, permitindo a visualização dos efeitos de filtros clássicos como média, mediana, Laplaciano, Sobel, ruído sal e pimenta, compressão dinâmica, entre outros.

---

## 🚀 Como Usar

1. **Execute o programa `ImageGUI.java`** com suporte à interface Swing.

    1.1. Os comandos são:

    ```bash
    cd src
    javac ImageGUI.java
    java ImageGUI
    ```

    1.2. Ou, via bash script:

    ```bash
    source ./run.sh
    ```

    1.3 Ou, via bat script (Windows):
    Executar `run.bat`.

    1.4 Ou, via `.jar`:
    Executar `ProcessadorDeImagens.jar` na pasta `bin`.

2. A interface será aberta com as seguintes opções:

   - **Carregar Imagem:** Abre um seletor de arquivos para escolher uma imagem do computador.
   - **Selecionar Filtro:** Escolha um dos filtros disponíveis no menu suspenso.
   - **Aplicar Filtro:** Processa a imagem original com o filtro selecionado.
   - **Salvar Imagem Processada:** Salva a imagem resultante em disco (formato PNG).

---

## ✨ Funcionalidades e Filtros Disponíveis

- `✅ Inverter Cores`  
  Inverte os valores RGB de cada pixel, criando uma versão negativa da imagem.

- `✅ Equalização de Histograma`  
  Realça o contraste da imagem ajustando a distribuição dos níveis de intensidade.

- `✅ Ruído Sal e Pimenta`  
  Adiciona ruído aleatório simulando pixels brancos (sal) e pretos (pimenta).

- `✅ Filtro de Média`  
  Suaviza a imagem aplicando a média dos pixels vizinhos (reduz ruído).

- `✅ Filtro de Mediana`  
  Remove ruído preservando bordas, substituindo o pixel central pela mediana dos vizinhos.

- `✅ Binarização`  
  Converte a imagem para preto e branco de acordo com um limiar `T` definido pelo usuário.

- `✅ Limiarização`  
  Similar à binarização, permite definir `T` manualmente para realçar regiões específicas da imagem.

- `✅ Filtro Laplaciano (Vizinhança 4)`  
  Detecta regiões de bordas com base em mudanças abruptas de intensidade usando derivadas de segunda ordem. A magnitude é elevada ao quadrado.

- `✅ Filtro Sobel`  
  Detecta arestas aplicando duas máscaras nas direções ortogonais (x e y), usando a magnitude do gradiente.

- `✅ Compressão de Escala Dinâmica (S = c·r^γ)`  
  Realça detalhes em regiões escuras ou claras da imagem aplicando a fórmula com `c` e `γ` definidos pelo usuário.

---

## 🧩 Estrutura do Projeto

- `src/`
  - `ImageGUI.java` — Interface gráfica principal com carregamento, seleção de filtros e exibição.
  - `InvertedImage.java`
  - `EqualizedImage.java`
  - `SalEPimenta.java`
  - `MediaFilter.java`
  - `MedianFilter.java`
  - `BinarizedImage.java`
  - `ThresholdImage.java`
  - `LaplacianFilter.java`
  - `SobelFilter.java`
  - `DynamicRangeCompression.java`

Cada classe encapsula seu próprio filtro, com os métodos:

- `apply<NomeDoFiltro>Filter(BufferedImage image)`
- `buildImageFromRGBMatrix(int[][][] matrix)`

---

## 🛠️ Requisitos

- Java 8 ou superior
- IDE recomendada: IntelliJ, Eclipse ou NetBeans

---

## 📘 Créditos

Desenvolvido como parte da disciplina **Processamento Digital de Imagens** — FCT UNESP  
**Professor:** Almir Olivette Artero  
**Curso:** Ciência da Computação — 5º Semestre

---

## 📝 Licença

Este projeto é apenas para fins acadêmicos.  
Sinta-se livre para explorar, modificar e aprender com o código!
