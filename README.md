# Sistema de Controle de Estoque

Atividade prática desenvolvida utilizando Java, Swing e MySQL.

## Aluno
Luiz Fernando Nicassio de Oliveira

---

# Tecnologias Utilizadas

- Java
- Java Swing
- MySQL
- JDBC

---

# Configuração do Banco de Dados

## Banco utilizado
MySQL

## Senha do banco : 123456

```text
123456
```

## Criação do banco de dados

```sql
CREATE DATABASE estoque_java;
```

---

# Tabela de Usuários

```sql
CREATE TABLE usuarios (

    id INT PRIMARY KEY AUTO_INCREMENT,

    usuario VARCHAR(100) NOT NULL,

    senha_hash VARCHAR(255) NOT NULL
);
```

---

# Tabela de Produtos

```sql
CREATE TABLE produtos (

    id INT PRIMARY KEY AUTO_INCREMENT,

    nome VARCHAR(200) NOT NULL,

    tipo VARCHAR(100) NOT NULL,

    valor DECIMAL(10,2) NOT NULL,

    quantidade INT NOT NULL
);
```

---

# Exemplo de Conexão

```java
String url = "jdbc:mysql://localhost:3306/estoque_java";

String usuario = "root";

String senha = "1011";
```

---

# Como Executar o Projeto

1. Instalar o MySQL
2. Criar o banco de dados
3. Executar os scripts SQL
4. Abrir o projeto na IDE
5. Executar a aplicação

---

# Funcionalidades

- Autenticação de usuários
- Cadastro de usuários
- Cadastro de produtos
- Controle de estoque
- Atualização de produtos
- Exclusão de produtos
- Dashboard com dados do estoque
- Sistema de baixa de produtos
- Pesquisa de produtos por nome
- Listagem de produtos com filtros de categoria e faixa de valor

---

# Observações

Projeto desenvolvido para fins acadêmicos.
