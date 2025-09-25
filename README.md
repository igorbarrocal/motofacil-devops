# MotoFacil DevOps & Cloud Computing - Azure App Service

## Descrição da Solução

MotoFacil é uma aplicação Java que realiza gestão e monitoramento de motos, pátios, localização e usuários, integrando funcionalidades de CRUD e rastreamento em tempo real. Nesta entrega, a aplicação é publicada no Azure App Service (PaaS), com banco de dados SQL na nuvem (Azure SQL), infraestrutura provisionada via Azure CLI.

---

## Descrição dos Benefícios para o Negócio

- **Centraliza e automatiza o controle de motos em diferentes pátios.**
- **Permite rastreamento e histórico de localização das motos.**
- **Facilita integração com IoT e ESP32 para monitoramento físico.**
- **Aumenta a segurança e agilidade no processo de cadastro, consulta, atualização e exclusão de registros.**
- **Reduz risco de erros e fraudes ao digitalizar todo o fluxo.**
---

## Passo a Passo para Deploy e Testes

### 1. **Provisionamento da Infraestrutura no Azure via CLI**

Cada código abaixo deve ser executado no Azure Cloud Shell (https://shell.azure.com) ou em terminal com Azure CLI autenticado.

#### 1.1 Registrar o provider Microsoft.Sql

> Permite que recursos de banco SQL sejam criados na sua conta Azure.
```sh
az provider register --namespace Microsoft.Sql
```

#### 1.2 Criar Grupo de Recurso na Região Brasil

> Agrupa todos os serviços da aplicação para melhor gestão e organização.
```sh
az group create --name motofacil-br-rg --location brazilsouth
```

#### 1.3 Criar Servidor SQL (Azure SQL)

> Cria o servidor do banco de dados onde ficará armazenada toda a base da aplicação.
```sh
az sql server create --name motofacil-sqlserver --resource-group motofacil-br-rg --location brazilsouth --admin-user myadmin --admin-password MyPassw0rd123
```

#### 1.4 Criar Banco de Dados SQL

> Cria o banco de dados real que a aplicação irá consumir.
```sh
az sql db create --resource-group motofacil-br-rg --server motofacil-sqlserver --name motofacil-db --service-objective S0
```

#### 1.5 Liberar IP para acesso ao banco

> Permite que a aplicação e testes acessem o banco via internet (libera todos IPs para facilitar o desenvolvimento).
```sh
az sql server firewall-rule create --resource-group motofacil-br-rg --server motofacil-sqlserver --name AllowAll --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0
```

#### 1.6 Criar Plano e App Service (Java 17)

> Define onde o backend Java vai rodar, usando Linux e Java 17.
```sh
az appservice plan create --name motofacil-plan --resource-group motofacil-br-rg --sku B1 --is-linux --location brazilsouth
az webapp create --resource-group motofacil-br-rg --plan motofacil-plan --name motofacil-app --runtime "JAVA|17-java17"
```

#### 1.7 Configurar a Connection String do Banco no App Service

> Permite que o backend Java acesse o Azure SQL com segurança.
```sh
az webapp config connection-string set --name motofacil-app --resource-group motofacil-br-rg \
  --settings DefaultConnection="Server=tcp:motofacil-sqlserver.database.windows.net,1433;Database=motofacil-db;User ID=myadmin@motofacil-sqlserver;Password=MyPassw0rd123;Encrypt=true;TrustServerCertificate=false;Connection Timeout=30;" \
  --connection-string-type=SQLAzure
```

---

### 2. **Build do Projeto Java**

> Gera o arquivo .jar da aplicação que será publicado no Azure.

```sh
mvn clean package
```
O jar gerado estará em `target/motofacil-backend-0.0.1-SNAPSHOT.jar`.

---

### 3. **Deploy do JAR para o App Service**

> Publica o backend Java no Azure, tornando os endpoints acessíveis via internet.

```sh
az webapp deploy --resource-group motofacil-br-rg --name motofacil-app --src-path target/motofacil-backend-0.0.1-SNAPSHOT.jar --type jar
```

---

### 4. **Testar a Aplicação**

> Verifique se a aplicação está disponível e explore a documentação e endpoints CRUD.

- URL principal:  
  `https://motofacil-app.azurewebsites.net/`
- Documentação Swagger:  
  `https://motofacil-app.azurewebsites.net/swagger-ui.html`

---

### 5. **Exemplos de Testes CRUD (JSON)**

> Os exemplos abaixo podem ser usados no Postman ou Swagger para inserir, atualizar, consultar e excluir registros reais.

#### a) Criar um Pátio

```json
POST /api/patios
{
  "nome": "Pátio Centro",
  "endereco": "Rua Central, 123",
  "codigoUnico": "CENTRO001",
  "esp32Central": "ESP32-XYZ",
  "coordenadasExtremidade": [0.0, 0.0, 10.0, 10.0]
}
```

#### b) Criar uma Moto vinculada ao pátio

```json
POST /api/motos
{
  "placa": "ABC1234",
  "modelo": "Honda CG",
  "categoria": "comum",
  "status": "patio",
  "descricao": "Moto do cliente A",
  "patio": { "id": 1 }
}
```

#### c) Atualizar localização da moto

```json
PUT /api/motos/1/location
{
  "x": 5.0,
  "y": 8.0,
  "patioId": 1,
  "tag": "patio"
}
```

#### d) Consultar motos

```http
GET /api/motos
```

#### e) Consultar pátios

```http
GET /api/patios
```

#### f) Consultar histórico de localização da moto

```http
GET /api/motos/1/history
```

---

## 6. **Estrutura do Repositório**

- `src/` - Código fonte Java
- `deploy_azure.sh` - Script completo de provisionamento Azure CLI
- `README.md` - Documentação detalhada e passo a passo
- `script_bd.sql` - DDL das tabelas do banco
- `equipe.pdf` - PDF com nomes/RM, link do repositório e vídeo
---

## 7. **DDL das Tabelas**

> O arquivo `script_bd.sql` deve conter toda a estrutura do banco, chaves primárias, tipos e comentários.

---

## 8. **PDF da Equipe**

> Crie o PDF com nome/RM dos integrantes, link do repositório e link do vídeo YouTube conforme os requisitos.

---

## 9. **Vídeo Demonstrativo**

> Grave um vídeo mostrando:
- Clone do repositório
- Execução dos scripts no Cloud Shell
- Deploy do JAR
- Teste dos endpoints CRUD
- Consulta via Swagger UI

---

## 10. **Observações Finais**

- Todos os comandos Azure CLI acima devem ser executados na ordem apresentada.
- Os exemplos de JSON podem ser adaptados conforme os dados reais do seu banco.
- O App Service e o banco de dados devem estar ativos na nuvem no momento da correção.

---
