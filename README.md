# ValidadorDoc 📄

Um validador de documentos brasileiro (CPF e CNPJ) moderno, desenvolvido em **Java 25**. O projeto valida a estrutura matemática dos documentos e identifica a origem (UF) no caso de CPFs.

## ✨ Novidades da Versão (Java 25)

- **Switch Expressions:** Lógica de identificação de UF e fluxo de validação mais limpos e sem a necessidade de múltiplos `break`.
- **Refatoração de Regex:** Melhor detecção de sequências repetidas e caracteres inválidos.
- **Pattern Matching:** Implementação de padrões modernos para seleção de lógica baseada no tamanho do documento.
- **Melhor UX:** Interface Swing ajustada para respostas em tempo real e tratamento de campos vazios com `.isBlank()`.

## 🛠️ Tecnologias e Recursos

*   **Java 25 JDK** (LTS)
*   **Swing** (Interface Gráfica)
*   **Paradigma:** MVC (Model-View-Controller) simplificado.

## 🚀 Como Executar

### Pré-requisitos
*   **JDK 25** configurado no seu sistema.
*   **Eclipse IDE** (versão 2025-03 ou superior recomendada).

### Passo a Passo
1.  Importe o projeto no Eclipse: `File > Import > General > Existing Projects into Workspace`.
2.  Certifique-se de que o **Compiler Compliance Level** nas propriedades do projeto está definido como **25**.
3.  **Remova** o arquivo `module-info.java` se estiver enfrentando erros de visibilidade de módulos.
4.  Execute a classe: `br.alelvis.validadorDoc.PrncipalView`.

## 📁 Estrutura do Projeto

```text
src/
 └── br/alelvis/validadorDoc/
      ├── ValidadorPfPj.java      # Lógica de validação e cálculos (Model)
      ├── PrncipalController.java # Intermediário entre View e Lógica (Controller)
      └── PrncipalView.java       # Interface Gráfica e ponto de entrada (View)
```

## 📝 Exemplo de Uso

1. Insira um número como `12345678909` (apenas números ou com pontuação).
2. Clique em **Validar**.
3. O sistema retornará:
   - Se é **Válido** ou **Inválido**.
   - O número **formatado** (ex: `123.456.789-09`).
   - A **Região Fiscal** de origem do CPF.

## ✒️ Autor

*   **Alexandre Marques** - *Desenvolvedor Principal* - https://github.com/alelvis3/ValidadorDoc

---