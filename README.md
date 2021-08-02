# api.blog
Teste Api Blog

Para teste, efetue um post em "http://localhost:8080/usuario/salvar" para cadastrar usuário
{
    "nome": "usuario",
    "email": "usuario@user.com",
    "senha": "****",
    "role":"ROLE_ADMIN", //ROLE_ADMIN ou ROLE_USER
    "authorities": ["ADMIN_AUTHORITIES"] // ADMIN_AUTHORITIES ou USUARIO_ATHORITIES
}

pegue o Token em http://localhost:8080/usuario/login, ele vira no HEADER da solicitação

{
    "nome": "usuario",
    "email": "usuario@user.com"
}
## Adicione o Token no Authorization/Bearer Token no Postman

http://localhost:8080/post/salvar, salvar o post
{
  "comentario":"Add comemtario",
	"link":"www.seila.com.br",
    "usuario": {
        "id":"1"
        }
}

http://localhost:8080/post, exibir todos os posts

http://localhost:8080/post/id, para deletar  

http://localhost:8080/atualizar/id, para atualizar ## USUARIO DO POST Nao pode ser atualizado 


