const db = require('./db');

async function setup() {
    // Usuń stare tabele (jeśli istnieją)
    await db.schema.dropTableIfExists('todos');
    await db.schema.dropTableIfExists('users');

    // Utwórz tabelę użytkowników
    await db.schema.createTable('users', table => {
        table.increments('id').primary();
        table.string('name').notNullable();
        table.string('email').notNullable();
        table.string('login').notNullable();
    });

    // Utwórz tabelę todo
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

    // Seed: przykładowi użytkownicy
    await db('users').insert([
        { name: 'Jan Konieczny', email: 'jan.konieczny@wonet.pl', login: 'jkonieczny' },
        { name: 'Anna Wesołowska', email: 'anna.wesolowska@sad.gov.pl', login: 'anna.wesolowska' }
    ]);

    // Seed: przykładowe zadania
    await db('todos').insert([
        { title: 'Naprawić samochód', completed: false, user_id: 1 },
        { title: 'Posprzątać garaż',  completed: true,  user_id: 1 },
        { title: 'Napisać e-mail',     completed: false, user_id: 1 },
        { title: 'Odebrać buty',       completed: false, user_id: 2 },
        { title: 'Wysłać paczkę',      completed: true,  user_id: 2 }
    ]);

    console.log('✳️  Database setup complete.');
    process.exit(0);
}

setup().catch(err => {
    console.error(err);
    process.exit(1);
});
