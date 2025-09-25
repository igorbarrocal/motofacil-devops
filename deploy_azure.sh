# Passo 2: Criar grupo de recurso no Brasil
az group create --name motofacil-br-rg --location brazilsouth

# Passo 3: Criar servidor SQL + banco de dados no Brasil
az sql server create --name motofacil-sqlserver --resource-group motofacil-br-rg --location brazilsouth --admin-user myadmin --admin-password MyPassw0rd123
az sql db create --resource-group motofacil-br-rg --server motofacil-sqlserver --name motofacil-db --service-objective S0

# Passo 4: Liberar IP (Cloud Shell ou seu IP)
az sql server firewall-rule create --resource-group motofacil-br-rg --server motofacil-sqlserver --name AllowAll --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0

# Passo 5: Criar App Service Plan + App Service (Brasil)
az appservice plan create --name motofacil-plan --resource-group motofacil-br-rg --sku B1 --is-linux --location brazilsouth
az webapp create --resource-group motofacil-br-rg --plan motofacil-plan --name motofacil-app --runtime "JAVA|17-java11" --location brazilsouth

# Passo 6: Configurar connection string do banco no App Service
az webapp config connection-string set --name motofacil-app --resource-group motofacil-br-rg \
  --settings DefaultConnection="Server=tcp:motofacil-sqlserver.database.windows.net,1433;Database=motofacil-db;User ID=myadmin@motofacil-sqlserver;Password=MyPassw0rd123;Encrypt=true;TrustServerCertificate=false;Connection Timeout=30;" \
  --connection-string-type=SQLAzure