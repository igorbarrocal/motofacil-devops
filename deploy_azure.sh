'# Registrar provider Microsoft.Sql (obrigatório)
az provider register --namespace Microsoft.Sql

# Criar grupo de recurso
az group create --name motofacil-rg --location eastus

# Criar servidor SQL + banco de dados
az sql server create --name motofacil-sqlserver --resource-group motofacil-rg --location eastus --admin-user myadmin --admin-password MyPassw0rd123
az sql db create --resource-group motofacil-rg --server motofacil-sqlserver --name motofacil-db --service-objective S0

# Liberar IP local para acesso ao banco
az sql server firewall-rule create --resource-group motofacil-rg --server motofacil-sqlserver --name AllowYourIP --start-ip-address <SEU_IP> --end-ip-address <SEU_IP>

# Criar App Service Plan + App Service
az appservice plan create --name motofacil-plan --resource-group motofacil-rg --sku B1 --is-linux
az webapp create --resource-group motofacil-rg --plan motofacil-plan --name motofacil-app --runtime "JAVA|17-java11"

# Configurar connection string do banco no App Service
az webapp config connection-string set --name motofacil-app --resource-group motofacil-rg \
  --settings DefaultConnection="Server=tcp:motofacil-sqlserver.database.windows.net,1433;Database=motofacil-db;User ID=myadmin@motofacil-sqlserver;Password=MyPassw0rd123;Encrypt=true;TrustServerCertificate=false;Connection Timeout=30;" \
  --connection-string-type=SQLAzure