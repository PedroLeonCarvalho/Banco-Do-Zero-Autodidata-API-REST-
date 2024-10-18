# API Bancária Do Zero (2024)

**Projeto Autodidata Desenvolvido por Pedro Leon ®**

![Imagem do Projeto](https://github.com/user-attachments/assets/38e5983d-c4c8-495d-8135-d1e023591da1)

"API REST robusta, desenvolvida de forma autodidata, que simula um sistema bancário oferecendo funcionalidades avançadas como transações financeiras, geração de relatórios, envio de e-mails de notificação e cálculo de juros para contas de poupança. As principais funcionalidades incluem endpoints para upload/download de arquivos, autenticação JWT, armazenamento em cache, testes automatizados e integração com MySQL e APIs externas, tudo documentado no Swagger UI."

## Funcionalidades

- **API REST**:
  - Endpoints para upload/download de arquivos
  - Validações
  - Endpoints: GET, POST, PUT, DELETE
  - Geração de relatórios
  - Login/Autorização

- **Dockerização**:
  - Api 100% Dockerizada 
  - Dockerfile: cria imagem para a aplicação
  - Docker Compose
  - Utiliza contêineres MySQL e Redis

- **Testes**:
  - Automação de testes com JUnit e Mockito

- **Banco de Dados**:
  - Banco de dados MySQL
  - Migrações (Flyway)

- **Documentação**:
  - Swagger UI

- **Cache**:
  - Armazenamento em cache

- **Segurança**:
  - Autenticação JWT

- **Outros**:
  - Notificações por e-mail
  - Interação HTTP com APIs externas
  - Tratamento de exceções
  - Padrões de Design: Factory, Strategy

### [LinkedIn](https://www.linkedin.com/in/pedro-leon-carvalho)

![Imagem do Projeto](https://github.com/user-attachments/assets/5cbec450-6a37-47a5-90ab-1d3fed7715a2)
![Imagem do Projeto](https://github.com/user-attachments/assets/26b94e48-2102-4f21-97ff-d9c74e2658ed)
![Imagem do Projeto](https://github.com/user-attachments/assets/d410656b-b84a-4980-b7a7-771012a4b9df)

## Como Executar

Para rodar o projeto API Bancária Do Zero, siga os passos abaixo:

1. **Clone o Repositório**:
   Abra seu terminal e clone o repositório do projeto usando o Git:
 
```git clone git@github.com:PedroLeonCarvalho/Banco-Do-Zero-Autodidata-API-REST-.git```

Navegue até o Diretório do Projeto:

```cd git@github.com:PedroLeonCarvalho/Banco-Do-Zero-Autodidata-API-REST-.git```

Certifique-se de que o Docker e o Docker Compose estejam instalados. Em seguida, no diretório do projeto, execute o seguinte comando para construir e iniciar a aplicação:

```docker-compose up --build```

Acesse a Aplicação: A API estará acessível em http://localhost:8080. A documentação do Swagger UI pode ser acessada em:

```http://localhost:8080/swagger-ui.html```

Desligue os Contêineres: Quando terminar, pare e remova os contêineres com:

```docker-compose down```



   
