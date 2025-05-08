import sqlite3 from 'sqlite3';
import { open } from 'sqlite';
import express from 'express';
import http from 'http';
import path from 'path';
import { Server } from 'socket.io';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const app = express();
const server = http.createServer(app);
const io = new Server(server);


//app.use(express.static(path.join(__dirname, 'public')));
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'index.html'));
});

  const db = await open({
    filename: 'chat.db',
    driver: sqlite3.Database
  });
  //i dont need timestamp to be datetime
  await db.exec(`
    CREATE TABLE IF NOT EXISTS messages (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      nick TEXT,
      room TEXT,
      content TEXT,
      timestamp TEXT,
      type TEXT
    );
  `);

const userData = {};

io.on('connection', socket => {
  userData[socket.id] = { room: 'lobby', nick: '' };

  socket.on('join', async ({ nick, room }) => {
    const prev = userData[socket.id];
    socket.leave(prev.room);

    const newRoom = room?.trim() || 'lobby';
    userData[socket.id] = { room: newRoom, nick };
    socket.join(newRoom);
    io.to(newRoom).emit('systemMessage', `${nick} dołączył(a) do pokoju ${newRoom}.`);

    const rows = await db.all(
      `SELECT nick, content, timestamp, type
       FROM messages
       WHERE room = ?
       ORDER BY id DESC
       LIMIT 50`,
      [room]
    );

  const history = rows.reverse();
  socket.emit('chatHistory', history)
  //kinda ugly, but otherwise the msg emits before history
  if (prev.nick) {
    io.to(prev.room).emit('systemMessage', `${prev.nick} opuścił(a) pokój ${prev.room}.`);
  }

  });

  socket.on('chatMessage',async msg => {
    const { nick, room } = userData[socket.id];
    const timestamp = new Date().toLocaleTimeString();

    // my message to me
    socket.emit('myMessage', { nick, msg, timestamp });

    // alien message to rest
    socket.broadcast
      .to(room)
      .emit('chatMessage', { nick, msg, timestamp, senderId: socket.id });

    //get into database
    await db.run(
      `INSERT INTO messages (nick, room, content, timestamp, type)
       VALUES (?, ?, ?, ?, 'text')`,
      [nick, room, msg, timestamp]
    );
  });

  socket.on('image',async imgData => {
    const { nick, room } = userData[socket.id];
    const timestamp = new Date().toLocaleTimeString();

    // same here
    socket.emit('myImage', { nick, imgData, timestamp });

    socket.broadcast
      .to(room)
      .emit('image', { nick, imgData, timestamp, senderId: socket.id });

    await db.run(
      `INSERT INTO messages (nick, room, content, timestamp, type)
        VALUES (?, ?, ?, ?, 'image')`,
      [nick, room, imgData, timestamp]
    );
  });

  socket.on('typing', () => {
    const { nick, room } = userData[socket.id];
    socket.to(room).emit('typing', nick);
  });
  socket.on('stopTyping', () => {
    const { room } = userData[socket.id];
    socket.to(room).emit('stopTyping');
  });

  socket.on('disconnect', () => {
    const { nick, room } = userData[socket.id] || {};
    if (nick) {
      io.to(room).emit('systemMessage', `${nick} opuścił(a) pokój ${room}.`);
    }
    delete userData[socket.id];//frees up nickname
  });

  socket.on('checkNick', (nick, callback) => {
    if (typeof nick !== 'string' || nick.trim().length < 3) {
      return callback(false);
    }

    if (Array.from(io.sockets.sockets.values()).some(s => userData[s.id]?.nick === nick.trim())) {
      return callback(false); // nickname is taken
    }

    callback(true); // nickname is available
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => console.log(`Serwer działa na porcie ${PORT}`));