API Bancária Do Zero (2024)

Projeto Autodidata

2024-09-06_11-06

"API REST robusta, desenvolvida de forma autodidata, que simula um sistema bancário oferecendo funcionalidades avançadas como transações financeiras, geração de relatórios, envio de e-mails de notificação e cálculo de juros para contas de poupança. As principais funcionalidades incluem endpoints para upload/download de arquivos, autenticação JWT, armazenamento em cache, testes automatizados e integração com MySQL e APIs externas, tudo documentado no Swagger UI."
Funcionalidades

    API REST:
        Endpoints para upload/download de arquivos
        Validações
        Endpoints: GET, POST, PUT, DELETE
        Geração de relatórios
        Login/Autorização

    Dockerização:
        Dockerfile: cria imagem para a aplicação
        Docker Compose
        Utiliza contêineres MySQL e Redis

    Testes:
        Automação de testes com JUnit e Mockito

    Banco de Dados:
        Banco de dados MySQL
        Migrações (Flyway)

    Documentação:
        Swagger UI

    Cache:
        Armazenamento em cache

    Segurança:
        Autenticação JWT

    Outros:
        Notificações por e-mail
        Interação HTTP com APIs externas
        Tratamento de exceções
        Padrões de Design: Factory, Strategy

www.linkedin.com/in/pedro-leon-carvalho

2024-09-06_10-50 2024-09-06_10-50_1 2024-09-06_10-50_2
Como Executar

Para rodar o projeto API Bancária Do Zero, siga os passos abaixo:

    Clone o Repositório: Abra seu terminal e clone o repositório do projeto usando o Git:

    bash

git clone git@github.com:PedroLeonCarvalho/RESTful-banking-API-from-scratch--no-curses-or-tutorials-involved-.git

Navegue até o Diretório do Projeto: Mude para o diretório do projeto:

bash

cd RESTful-banking-API-from-scratch--no-curses-or-tutorials-involved-

Construa e Rode os Contêineres com Docker Compose: Certifique-se de que o Docker e o Docker Compose estejam instalados. Em seguida, no diretório do projeto, execute o seguinte comando para construir e iniciar a aplicação:

bash

docker-compose up --build

Acesse a Aplicação: A API estará acessível em http://localhost:8080. A documentação do Swagger UI pode ser acessada em http://localhost:8080/swagger-ui.html.

Desligue os Contêineres: Quando terminar, pare e remova os contêineres com:

bash

docker-compose down
