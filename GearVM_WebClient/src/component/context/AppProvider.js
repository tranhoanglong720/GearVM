import React, { useState } from "react";

export const AppContext = React.createContext();
function AppProvider({ children }) {
  const [show, setShow] = useState(false);
  const [UserOpen, setUserOpen] = useState(false);
  const [openMenu, setOpenMenu] = useState(false);
  const [showChat, setShowChat] = useState(false);
  const [showFilter, setShowFilter] = useState(false);
  const [user, setUser] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
  });
  const [messages, setMessages] = useState([
    {
      message: "Hello, I'm ChatGPT! Ask me anything!",
      sentTime: "just now",
      sender: "ChatGPT",
    },
  ]);
  const [discount, setDiscount] = useState("");
  const clearState = () => {
    setShow(false);
    setUserOpen(false);
    setOpenMenu(false);
    setShowChat(false);
    setUser();
    setShowFilter(false);
    setDiscount("");
  };
  return (
    <AppContext.Provider
      value={{
        show,
        setShow,
        clearState,
        UserOpen,
        setUserOpen,
        openMenu,
        setOpenMenu,
        showChat,
        setShowChat,
        user,
        setUser,
        setMessages,
        messages,
        setShowFilter,
        showFilter,
        discount,
        setDiscount,
      }}
    >
      {children}
    </AppContext.Provider>
  );
}

export default AppProvider;
