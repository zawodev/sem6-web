const { readFileSync }                  = require('fs')
const { join }                          = require('path')
const { createSchema, createYoga }      = require('graphql-yoga')
const { createServer }                  = require('http')
const axios                         = require('axios')

async function getRestUsersList(){
    try {
        const users = await axios.get("https://jsonplaceholder.typicode.com/users")
        console.log(users);
        return users.data.map(({ id, name, email, username }) => ({
            id: id,
            name: name,
            email: email,
            login: username,
        }))
    } catch (error) {
        throw error
    }
}

// 1) Load your SDL from file
const typeDefs = readFileSync(
    join(__dirname, './schema.graphql'),
    'utf-8'
)

const usersList = [
    { id: 1, name: "Jan Konieczny", email: "jan.konieczny@wonet.pl", login: "jkonieczny" },
    { id: 2, name: "Anna Wesołowska", email: "anna.w@sad.gov.pl", login: "anna.wesolowska" },
    { id: 3, name: "Piotr Waleczny", email: "piotr.waleczny@gp.pl", login: "p.waleczny" }
];
const todosList = [
    { id: 1, title: "Naprawić samochód", completed: false, user_id: 3 },
    { id: 2, title: "Posprzątać garaż", completed: true, user_id: 3 },
    { id: 3, title: "Napisać e-mail", completed: false, user_id: 3 },
    { id: 4, title: "Odebrać buty", completed: false, user_id: 2 },
    { id: 5, title: "Wysłać paczkę", completed: true, user_id: 2 },
    { id: 6, title: "Zamówic kuriera", completed: false, user_id: 3 },
];

function todoById(parent, args, context, info){
    return todosList.find(t => t.id == args.id);
}
function userById(parent, args, context, info){
    return usersList.find(u => u.id == args.id);
}


// 2) Define resolvers as before
const resolvers = {
    Query: {
        users: async () => getRestUsersList(),
        todos: () => todosList,
        todo: (parent, args, context, info) => todoById(parent, args, context, info),
        user: (parent, args, context, info) => userById(parent, args, context, info),
    },
    ToDoItem:{
        user: (parent, args, context, info) => {
            return usersList.find(u => u.id == parent.user_id);
        }
    },
    User:{
        todos: (parent, args, context, info) => {
            return todosList.filter(t => t.user_id == parent.id);
        }
    }
}

// 3) Create a combined schema object
const schema = createSchema({ typeDefs, resolvers })

// 4) Create the Yoga handler
const yoga = createYoga({ schema })

// 5) Hook it into an HTTP server
const server = createServer(yoga)

// 6) Start listening
server.listen(4000, () =>
    console.log('Server is running on http://localhost:4000/graphql')
)
