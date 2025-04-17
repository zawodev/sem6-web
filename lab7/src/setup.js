const axios = require('axios');
const db    = require('./db');

async function setup() {
    try {
        await db.schema.dropTableIfExists('todos');
        await db.schema.dropTableIfExists('users');
        
        await db.schema.createTable('users', table => {
            table.increments('id').primary();
            table.string('name').notNullable();
            table.string('email').notNullable();
            table.string('login').notNullable();
        });

        await db.schema.createTable('todos', table => {
            table.increments('id').primary();
            table.string('title').notNullable();
            table.boolean('completed').notNullable().defaultTo(false);
            table.integer('user_id')
                .unsigned()
                .references('id')
                .inTable('users')
                .onDelete('CASCADE');
        });
        
        console.log('🔄  Fetching users from JSONPlaceholder…');
        const usersRes = await axios.get('https://jsonplaceholder.typicode.com/users');
        const users = usersRes.data.map(u => ({
            id:    u.id,
            name:  u.name,
            email: u.email,
            login: u.username
        }));
        console.log(`✳️  Inserting ${users.length} users…`);
        await db('users').insert(users);
        
        console.log('🔄  Fetching todos from JSONPlaceholder…');
        const todosRes = await axios.get('https://jsonplaceholder.typicode.com/todos');
        const todos = todosRes.data.map(t => ({
            id:        t.id,
            title:     t.title,
            completed: t.completed,
            user_id:   t.userId
        }));
        console.log(`✳️  Inserting ${todos.length} todos…`);
        await db('todos').insert(todos);

        console.log('✅  Database setup complete.');
        process.exit(0);

    } catch (err) {
        console.error('❌  Error during setup:', err);
        process.exit(1);
    }
}

setup();
