Projeto acadêmico da disciplina de Programação 2 (UFAL)

Estrutura do projeto -
  src/
  br/ufal/ic/myfood/
  models/          → Classes que representam os dados do sistema ///
  services/        → Classes que contém a lógica e regras de negócio ///
  exceptions/      → Uma classe por tipo de erro do sistema ///
  Facade.java      → Ponto de entrada usado pelo EasyAccept ///

Banco de Dados - 
  Este projeto não usa um banco de dados usual. Os dados são salvos em um arquivo binário chamado "myfood_dados.ser", importado do Serializable.
  Serializar seria converter um objeto Java em uma sequência de bytes para salva-lo em disco, e desserializar seria o contrário, ler os bytes e reconstruir o objeto na memoria.
  
  Ao chamar encerrarSistema(), o PersistenciaService salva todos os dados no arquivo myfood_dados.ser, na próxima execução, o sistema carrega automaticamente esse arquivo e reconstrói todos os objetos, ao chamar zerarSistema(), o arquivo é deletado e os dados são reiniciados.

  O package "models", são as classes modelo que representam as entidades do sistema, armazenando dados e retornando os atributos.
  O package "service", contêm toda a lógica e regras do projeto e cada service é responsável por uma entidade.
