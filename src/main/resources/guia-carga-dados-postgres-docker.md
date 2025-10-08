# Tutorial: Populando o Banco de Dados PostgreSQL no Docker

Este guia mostra o passo a passo completo para executar um script SQL (como o nosso `carga_inicial.sql`) em um banco de dados PostgreSQL que está rodando dentro de um container Docker.

## Pré-requisitos

Antes de começar, garanta que você tenha:

  - **Docker** instalado e em execução.
  - O **container do seu PostgreSQL rodando**.
  - O arquivo `carga_inicial.sql` está em `../src/main/resources/carga_inicial.sql`.
  - As informações de conexão com seu banco de dados. Para este tutorial, usaremos os dados do nosso banco.

-----

## Passo a Passo

### Passo 1: Abra o Terminal

Abra o seu terminal (Prompt de Comando, PowerShell, ou o terminal do seu sistema operacional).

**Importante:** Navegue até o diretório onde está salvo o arquivo `carga_inicial.sql`.

### Passo 2: Verifique o Nome do Container

É sempre bom confirmar se o container está rodando e qual é o nome exato dele. Execute o comando:

```bash
docker ps
```

Você verá uma lista dos containers em execução. Procure na coluna `NAMES` pelo nome do seu container de banco de dados.

```
CONTAINER ID   IMAGE          COMMAND                  CREATED       STATUS       PORTS                    NAMES
a1b2c3d4e5f6   postgres:13    "docker-entrypoint.s…"   2 hours ago   Up 2 hours   0.0.0.0:5432->5432/tcp   postgres
```

### Passo 3: Execute o Script

Vamos usar um único comando que lê o seu arquivo `.sql` e envia todo o seu conteúdo para ser executado pelo `psql` dentro do container.

Copie e cole o comando abaixo no seu terminal:

```bash
cat carga_inicial_up.sql | docker exec -i postgres psql -U postgres -d easy_coast
```

#### O que este comando faz?

  - **`cat carga_inicial.sql`**: Lê e exibe todo o conteúdo do arquivo `carga_inicial.sql`.
  - **`|`** (pipe): Pega a saída do comando anterior (`cat`) e a envia como entrada para o próximo comando.
  - **`docker exec -i postgres ...`**: Executa um comando dentro do container chamado `postgres`. A flag `-i` (interativo) mantém a entrada padrão aberta, permitindo que o comando receba os dados do `cat`.
  - **`psql -U postgres -d easy_coast`**: É o programa de linha de comando do PostgreSQL.
      - `-U postgres`: Especifica o **usuário**.
      - `-d easy_coast`: Especifica o **banco de dados** (database).

-----

### Passo 4: Verificando os Dados (Duas Opções)

Depois que o script for executado, é hora de confirmar que os dados estão no banco. A forma mais fácil é usando uma ferramenta visual (GUI), mas também é possível fazer uma verificação rápida pelo terminal.

### Verificação Rápida via Terminal

Se você não quer instalar outro programa e prefere uma checagem rápida, pode usar o terminal como mostramos antes.

1.  **Conecte-se ao `psql` dentro do container:**
    ```bash
    docker exec -it postgres psql -U postgres -d easy_coast
    ```
2.  **Digite no terminal do `psql`:**
    ```sql
    \dt
    ```
3.  **Saia do `psql`** digitando `\q` e pressionando Enter.

-----


### 🔧 Solução de Problemas Comuns

  - **Erro: `cat: carga_inicial.sql: No such file or directory`**

      - **Causa:** O terminal não está na mesma pasta que o arquivo ou o nome do arquivo está incorreto.
      - **Solução:** Use o comando `ls` (ou `dir` no Windows) para ver os arquivos na pasta atual e `cd` para navegar até o diretório correto.

  - **Erro: `Error response from daemon: No such container: postgres`**

      - **Causa:** O nome do container está incorreto ou ele não está em execução.
      - **Solução:** Use `docker ps` para verificar o nome exato e o status do container.

  - **Erro: `psql: FATAL: database "easy_coast" does not exist`**

      - **Causa:** O nome do banco de dados no comando está incorreto.
      - **Solução:** Verifique a configuração da sua aplicação para confirmar o nome exato do banco.

-----
