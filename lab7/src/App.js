const { readFileSync } = require('fs');
const { join } = require('path');
const { createSchema, createYoga } = require('graphql-yoga');
const { createServer } = require('http');
const db = require('./db');

const typeDefs = readFileSync(join(__dirname, 'schema.graphql'), 'utf-8');

const resolvers = {
    Query: {
        users:    ()                 => db('users'),
        user:     (_, { id })        => db('users').where({ id }).first(),
        todos:    ()                 => db('todos'),
        todo:     (_, { id })        => db('todos').where({ id }).first(),
    },

    Mutation: {
        // USERS
        addUser:    async (_, { name, email, login }) => {
            const [id] = await db('users').insert({ name, email, login });
            return { id, name, email, login };
        },
        updateUser: async (_, { id, name, email, login }) => {
            const fields = {};
            if (name  !== undefined) fields.name  = name;
            if (email !== undefined) fields.email = email;
            if (login !== undefined) fields.login = login;
            await db('users').where({ id }).update(fields);
            return db('users').where({ id }).first();
        },
        deleteUser: async (_, { id }) => {
            await db('users').where({ id }).del();
            return true;
        },

        // TODOS
        addTodo:    async (_, { title, completed, user_id }) => {
            const [id] = await db('todos').insert({ title, completed, user_id });
            return { id, title, completed, user_id };
        },
        updateTodo: async (_, { id, title, completed, user_id }) => {
            const fields = {};
            if (title     !== undefined) fields.title     = title;
            if (completed !== undefined) fields.completed = completed;
            if (user_id   !== undefined) fields.user_id   = user_id;
            await db('todos').where({ id }).update(fields);
            return db('todos').where({ id }).first();
        },
        deleteTodo: async (_, { id }) => {
            await db('todos').where({ id }).del();
            return true;
        }
    },

    ToDoItem: {
        user: parent => db('users').where({ id: parent.user_id }).first()
    },
    User: {
        todos: parent => db('todos').where({ user_id: parent.id })
    }
};

const schema = createSchema({ typeDefs, resolvers });
const yoga = createYoga({ schema });

const server = createServer(yoga);
server.listen(4000, () =>
    console.log('🚀 Server is running at http://localhost:4000/graphql')
);
