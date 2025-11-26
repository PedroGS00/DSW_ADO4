# 📚 Cadastro de Cursos

Sistema web para cadastro de cursos com duas visões distintas:
- **Visão Usuário (pública):** consulta a lista de cursos.
- **Visão Administrador (área logada):** cadastrar, editar, listar e remover cursos.

## ✨ Funcionalidades

- Listagem pública de cursos na página inicial
- Autenticação com login e cadastro de usuários
- Área administrativa protegida por `ROLE_ADMIN`
- CRUD de cursos via API e UI
- Proteção CSRF habilitada para operações de escrita

## 🛠 Tecnologias

- **Spring Boot** (MVC, Security, Data JPA)
- **Thymeleaf** (Templates)
- **Bootstrap 5** (Estilos)
- **H2 Database** (Banco em memória com console liberado)

## 📋 Requisitos

- Java 11+ instalado
- Maven instalado

## 🚀 Como executar

### 1. Compilar
```bash
mvn -DskipTests package
```
### 2. Executar (Modo Desenvolvimento)
```bash
mvn spring-boot:run
```
- Nota: Se a porta 8080 estiver ocupada, execute definindo uma nova porta:
```
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### 3. Executar via JAR 
```
java -jar target/cadastro-curso-*.jar
```

## 🔐 Acesso Administrativo 
- O sistema cria um administrador padrão na inicialização caso não exista.

##  🛡 Segurança e Rotas
- CSRF: Ativo. Todas as requisições POST/PUT/DELETE exigem token.

- Logout: Via POST /logout (Token CSRF embutido na navbar).

## 📝 Notas de Desenvolvimento
- Console H2: Acessível em /h2-console (CSRF ignorado nesta rota).

- UI: Utiliza Bootstrap 5 via CDN. A página Home consome a API via fetch
