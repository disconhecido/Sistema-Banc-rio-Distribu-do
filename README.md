# Sistema-Banc-rio-Distribu-do
Sistema bancário distribuído desenvolvido como projeto final da disciplina de sistemas distribuídos do curso de engenharia de computação da UFG.

ALUNOS:
DANIEL MARTINEZ ALENCAR FREITAS; 
IURY CANEDO ANABUKI; 
LUCAS DE JESUS BATISTA GONÇALVES; 
LUIZ YOKOYAMA FELIX DE SOUZA; 
VICTOR TAVARES PONTES; 

INSTUÇÕES DE USO DESTE SISTEMA:
- Usar o script para criar as tabelas no postgresql
- Executar o servidor java, usando o arquivo ServidorBanco
- Executar o cliente java, usando o arquivo ClienteBanc

INSTRUÇÕES PARA CONFIGURAR O POSTGRESQL PARA ACEITAR CONEXÕES EXTERNAS:
adicionar a seguinte linha no arquivo ..\Program Files\PostgreSQL\11\data\pg_hba.conf

para senha criptografada:

host all all 0.0.0.0/0  md5 

para senha não criptografada

host all all 0.0.0.0/0  trust 

no arquivo postgresql.conf 
alterar para:
listen_addresses = '*'


É NECESSÁRIO O RESTART O POSTGRESQL:
no prompt (Windows), entrar na pasta bin e usar a linha de comando:

pg_ctl -D "C:\arquivos de programas\PostgreSQL\11\data" restart -s


>>>>>>> ENJOY! ;)




