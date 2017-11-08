## Flink Streaming Mining - Protótipo
*Esse protótipo está em fase de desenvolvimento para melhorar a arquitetura e facilitar a interoperabilidade de novos módulos de mineração de fluxo de dados.*

### Sumário
**[1. Instalando do Apache Flink](#install)**

**[2. Utilizando o Apache Flink em seu projeto.](#maven)**

**[2.1 Integração com a IDE IntelliJ IDEA](#idea)**

**[3. Instalando e Configurando o Apache Kafka](#kafka)**

**[4. Executando o protótipo.](#prot)**


### 1 - Instalando o Apache Flink <a name="install"></a>
#### 1.1 - Dependências
Para executar o Apache Flink com sucesso, algumas dependencias são necessárias. Dentre elas:

**Java7+ SE**: Instale a versão do Java 7 ou superior e configure ela para ficar na variável de ambiente `JAVA_HOME`. Recomenda-se a instalação Java 8.
*Averigue se a versao do Java é compatível com a do Scala.*

**Apache Maven**: Simples [procedimento de extrair](https://maven.apache.org/install.html) o arquivo e adicionar a pasta `bin` com o comando `mvn` na variável de ambiente `PATH`.

**Scala**: Após a instalação do Java, será necessário instalar o Scala. Faça o [download](http://www.scala-lang.org/download/) do Scala, extraia e copie os arquivos para a variavel de ambiente `/usr/local/src`.
```
$ sudo mkdir /usr/local/src/scala
$ sudo tar xvf arquivo_de_instalacao_do_scala.tgz -C /usr/local/src/scala/
```
```
$ vi .bashrc
```
Adicione as seguintes linhas no final do arquivo:
```
$ export SCALA_HOME=/usr/local/src/scala/scala-SUAVERSAO
$ export PATH=$SCALA_HOME/bin:$PATH
```
Reinicie a bashrc
```
$ . .bashrc
```
Averigue se o Scala foi instalado com sucesso:
```
$ scala -version
```
Isso vai mostrar a versão do scala:

`Scala code runner version 2.10.4 -- Copyright 2002-2013, LAMP/EPFL`

#### 1.2 - Instalando o Apache Flink

Entre na [página de download](https://flink.apache.org/downloads.html) e baixe a versão com a combinação Hadoop/Scala que vocẽ achar melhor.

Descompacte o arquivo de download e execute o seguinte comando dentro da pasta do Apache Flink:

```
$ ./bin/start-local.sh
```
Se os passos anteriores foram seguidos corretamente, entre no endereço http://localhost:8081:

![Apache Flink](https://ci.apache.org/projects/flink/flink-docs-release-1.3/page/img/quickstart-setup/jobmanager-1.png)

Digite o seguinte comando caso deseje encerrar o Local Cluster do Apache Flink:
```
$ ./bin/stop-local.sh
```
Caso tenha interesse em rodar exemplos, acesse a página de [Quickstart do Apache Flink](https://ci.apache.org/projects/flink/flink-docs-release-1.3/quickstart/setup_quickstart.html).

### 2 - Utilizando o Apache Flink em seu projeto. <a name="maven"></a>
Se voce deseja programar usando as ferramentas do Apache Flink, crie um projeto utilizando o [Maven - Tutorial Criando repositório](https://maven.apache.org/guides/getting-started/). 

O maven gera uma hierarquia de pastas e alguns arquivos, dentre eles o que iremos focar aqui é o `pom.xml` (Project Object Model).

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Maven Quick Start Archetype</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

O arquivo `pom.xml` contém as informações do seu projeto, dentre elas as dependências. Para utilizar as ferramentas do Apache Flink, voce pode adicionar as dependencias no arquivo `pom.xml`. Essas podem ser encontradas aqui [Maven - Apache Flink](https://flink.apache.org/downloads.html). Basta selecionar as ferramentas que você julga necessário. 

Para executar esse projeto, copie os dados contidos no arquivo `pom.xml` [localizado no diretório](https://github.com/loezerl/flink-stream-processing/blob/master/pom.xml).

Recomenda-se também utilizar as libs (`/lib/`) presentes no diretório do Apache Flink.

#### 2.1 - Integração com a IDE IntelliJ IDEA <a name="idea"></a>
Esse prototótipo foi desenvolvido utilizando a IDE IntelliJ IDEA Community, pois ela facilita o processo de Debug e auto-importação das dependencias do Maven.
Para instalar essa IDE, basta seguir o [tutorial do próprio site](https://www.jetbrains.com/idea/download/).

Com o IDEA instalado e devidamente configurado com a sua JDK, você deverá criar um projeto do tipo Maven:

`File -> New Project -> Maven`

![IDEA New Project - Maven](https://i.imgur.com/cxsMYzS.png)

Clique em `Next` e preencha os dados de `GroupId` e `ArtifactId`. 

![IDEA Maven Project](https://i.imgur.com/7QClio6.png)

Clique em `Next` novamente e preencha os dados de `Project name` e `Project location` caso seja necessário. Clique em `Finish` para criar o projeto.

Pode demorar um pouco e exigir processamento do seu computador, pois o IDEA está indexando as informações presentes no `pom.xml`.

![Indexando o pom.xml](https://i.imgur.com/59II58m.png)

Com o projeto criado, basta adicionar as dependências necessárias do `pom.xml` e permitir a auto-importação dos pacotes na IDE.

![Permitindo a auto-importação](https://i.imgur.com/uDwumpx.png)

Com as dependências baixadas, você já pode iniciar sua implementação utilizando o Apache Flink. Caso você não saiba quais ferramentas irá precisar, basta pegar as dependências do arquivo `pom.xml` dos [exemplos disponibilizados](https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/pom.xml) pelo Apache Flink.

### 3 - Instalando e Configurando o Apache Kakfa <a name="kafka"></a>
A API do Apache Flink Streaming não tem compatibilidade com o módulo de leitura de arquivos `ARFF` disponível no [MOA](https://github.com/Waikato/moa/blob/master/moa/src/main/java/moa/streams/ArffFileStream.java).

Um *workarround* para isso, é conectar a leitura de arquivos do Apache Flink em um tópico do Apache Kafka. O Apache Kafka pode ler o arquivo utilizando o módulo `ArrFileStream` e emitir as instâncias para um tópico aonde o Apache Flink irá consumir.

**Kafka Producer - Arff File**:

A implementação de um Kafka Producer utilizando o módulo de leitura de arquivos Arff pode ser [encontrado aqui](https://github.com/loezerl/kafka-arff-producer/blob/master/src/main/java/Producer.java). 

Por enquanto, o parser das instancias é realizada utilizando o `StringSerializer`, o que não é uma boa prática. O correto seria que a classe Instance tivesse um serializador para enviar ela junto com os metadados e, o Apache Flink utilizar um deserializador para acessar a instancia como se tivesse utilizando o módulo de leitura de arquivos arff do MOA. Esse upgrade nesse protótipo facilitaria a interoperabilidade dos classificadores presentes no MOA com o Apache Flink.

#### 3.1 - Instalando o Apache Kafka
A instalação do Apache Kafka é simples, basta entrar na página de downloads, baixar o kafka (verifique a [versão compatível com o Apache Flink aqui](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/connectors/kafka.html)) e descompactar o arquivo.

Com o arquivo descompactado, você precisará levantar um servidor do ZooKeeper, para isso navegue até a pasta do kafka execute o comando abaixo:

```
$ sudo bin/zookeeper-server-start.sh config/zookeeper.properties
[2013-04-22 15:01:37,495] INFO Reading configuration from: config/zookeeper.properties (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
...
```
Abra outro terminal/bash e navegue novamente até a pasta do Kafka, execute os comandos abaixo para executar o Kafka Server:

```
$ bin/kafka-server-start.sh config/server.properties
[2013-04-22 15:01:47,028] INFO Verifying properties (kafka.utils.VerifiableProperties)
[2013-04-22 15:01:47,051] INFO Property socket.send.buffer.bytes is overridden to 1048576 (kafka.utils.VerifiableProperties)
...
```
Agora você deve criar um tópico para que o Kafka Producer consiga escrever e a aplicação do Apache Flink consiga consumir. Para isso basta:

```
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic instances
```
Agora nós criamos um tópico chamado `instances`, você pode listar os tópicos utilizando o seguinte comando:

```
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
instances
```

### 4 - Executando o protótipo. <a name="prot"></a>

Após criar um projeto, colocar os arquivos presentes deste repositório e averiguar as dependências. Você necessitará de uma base grande do tipo Arff.
Com isso, basta você: 

- Abrir o seu Kafka Producer (tenha certeza de ter as libs do MOA disponíveis para o Producer utilizar).
- Substituir o endereço para sua base de dados.
- Limitar um número de instâncias que você deseja escrever num tópico.
- Averiguar se o Kafka Producer estará enviando as instancias para o tópico `instances`.
- Verificar se o Protótipo está conectado nesse mesmo tópico.
- Realizar os passos descritos no item 3.1, levantando um servidor do Apache Kafka e ZooKeeper.
- Executar o protótipo e logo em seguida o Kafka Producer.
