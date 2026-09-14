# nfw-srv-tbca-importacao

Serviço responsável por importar os JSONs gerados pelo `nfw-srv-tbca-exportacao` para as tabelas nutricionais do PostgreSQL.

## Fluxo

1. Coloque arquivos `.json` em `importacao/pendentes`.
2. Inicie o serviço. A importação começa automaticamente no startup.
3. Para cada arquivo, o serviço usa `nome`, `versao` e `descricao` do JSON em `tabela_nutricional_ref` e grava alimentos, porções e nutrientes em cascata.
4. Após a conclusão de todos os lotes, o arquivo é movido para `importacao/processados`.

Arquivos com JSON inválido, metadados ausentes ou nutriente desconhecido permanecem na pasta de pendentes para correção e nova tentativa.
Se o processo for interrompido, a referência da tabela e os lotes já confirmados permanecem no banco. No próximo startup, o serviço reutiliza a referência e ignora os alimentos já importados, continuando pelos demais.

## Configuração

O serviço usa as tabelas criadas pelo projeto `nfw-migration` e não executa migrations próprias. As configurações podem ser alteradas por variáveis de ambiente:

- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `TBCA_IMPORT_PENDING_DIR` (padrão: `importacao/pendentes`)
- `TBCA_IMPORT_PROCESSED_DIR` (padrão: `importacao/processados`)
- `TBCA_IMPORT_BATCH_SIZE` (padrão: `100`, quantidade de alimentos por lote)

O nome do arquivo pode ser qualquer nome `.json`. Para o arquivo de exemplo, a referência criada será `codigo=TBCA-7.3`, `nome=TBCA`, `versao=7.3` e `descricao=Tabela Brasileira de Composição de Alimentos`.

## Execução

```bash
mvn spring-boot:run
```

Para testar com o arquivo de exemplo deste projeto, copie-o para `importacao/pendentes` antes de iniciar a aplicação.

A tabela de referência é criada primeiro. Depois, os alimentos são inseridos em lotes; cada lote executa `flush` e limpa o contexto JPA antes de continuar. Se algum lote falhar, a transação do arquivo inteiro é revertida e o JSON permanece em `importacao/pendentes`.
