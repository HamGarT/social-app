import React, { useEffect, useState } from 'react';
import { Client, over } from 'stompjs';
import SockJS from 'sockjs-client';

// Define types
interface ChatMessage {
  senderName: string;
  receiverName?: string;
  message?: string;
  status: "JOIN" | "MESSAGE";
}

type PrivateChatsMap = Map<string, ChatMessage[]>;

let stompClient: Client | null = null;

const ChatRoom: React.FC = () => {
  const [privateChats, setPrivateChats] = useState<PrivateChatsMap>(new Map());
  const [publicChats, setPublicChats] = useState<ChatMessage[]>([]);
  const [tab, setTab] = useState<string>("CHATROOM");
  const [userData, setUserData] = useState({
    username: '',
    receivername: '',
    connected: false,
    message: ''
  });

  useEffect(() => {
    //console.log(userData);
  }, [userData]);

  const connect = () => {
    let Sock = new SockJS('http://localhost:8585/ws');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    setUserData({ ...userData, "connected": true });
    if (stompClient) {
      stompClient.subscribe('/topic/messages', onMessageReceived);
      stompClient.subscribe('/user/'+userData.username+'/queue/private', onPrivateMessage);
      userJoin();
    }
  };

  const userJoin = () => { 
    if (stompClient) {
      const chatMessage: ChatMessage = {
        senderName: userData.username,
        status: "JOIN"
      };
      stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }
  };

  const onMessageReceived = (payload: any) => {
    const payloadData: ChatMessage = JSON.parse(payload.body);
    switch (payloadData.status) {
      case "JOIN":
        if (!privateChats.get(payloadData.senderName)) {
          privateChats.set(payloadData.senderName, []);
          setPrivateChats(new Map(privateChats));
        }
        break;
      case "MESSAGE":
        publicChats.push(payloadData);
        setPublicChats([...publicChats]);
        break;
    }
  };

  const onPrivateMessage = (payload: any) => {
    const message: ChatMessage = JSON.parse(payload.body);
    setPrivateChats(prev => {
      // 1) Clone the old map
      const updated = new Map(prev);
  
      // 2) Grab the list (or start a new one)
      const sender = message.senderName;
      const list = updated.get(sender) ?? [];
  
      // 3) Add the new message
      list.push(message);
  
      // 4) Put it back under that key
      updated.set(sender, list);
  
      // 5) Return the new Map
      return updated;
    });
  };

  const onError = (err: any) => {
    //console.log(err);
  };

  const handleMessage = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setUserData({ ...userData, "message": value });
  };

  const sendValue = () => {
    if (stompClient) {
      const chatMessage: ChatMessage = {
        senderName: userData.username,
        message: userData.message,
        status: "MESSAGE"
      };
      //console.log(chatMessage);
      stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, "message": "" });
    }
  };

  const sendPrivateValue = () => {
    if (stompClient) {
      const chatMessage: ChatMessage = {
        senderName: userData.username,
        receiverName: tab,
        message: userData.message,
        status: "MESSAGE"
      };

      if (userData.username !== tab) {
        const chats = privateChats.get(tab);
        if (chats) {
          chats.push(chatMessage);
          setPrivateChats(new Map(privateChats));
        }
      }
      stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, "message": "" });
    }
  };

  const handleUsername = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setUserData({ ...userData, "username": value });
  };

  const registerUser = () => {
    connect();
  };

  return (
    <div className="container">
      {userData.connected ?
        <div className="chat-box">
          <div className="member-list">
            <ul>
              <li onClick={() => { setTab("CHATROOM") }} className={`member ${tab === "CHATROOM" && "active"}`}>Chatroom</li>
              {[...privateChats.keys()].map((name, index) => (
                <li onClick={() => { setTab(name) }} className={`member ${tab === name && "active"}`} key={index}>{name}</li>
              ))}
            </ul>
          </div>
          {tab === "CHATROOM" && <div className="chat-content">
            <ul className="chat-messages">
              {publicChats.map((chat, index) => (
                <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                  {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                  <div className="message-data">{chat.message}</div>
                  {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                </li>
              ))}
            </ul>

            <div className="send-message">
              <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
              <button type="button" className="send-button" onClick={sendValue}>send</button>
            </div>
          </div>}
          {tab !== "CHATROOM" && <div className="chat-content">
            <ul className="chat-messages">
              {privateChats.get(tab)?.map((chat, index) => (
                <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                  {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                  <div className="message-data">{chat.message}</div>
                  {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                </li>
              ))}
            </ul>

            <div className="send-message">
              <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
              <button type="button" className="send-button" onClick={sendPrivateValue}>send</button>
            </div>
          </div>}
        </div>
        :
        <div className="register">
          <input
            id="user-name"
            placeholder="Enter your name"
            name="userName"
            value={userData.username}
            onChange={handleUsername}
            
          />
          <button type="button" onClick={registerUser}>
            connect
          </button>
        </div>}
    </div>
  );
};

export default ChatRoom;