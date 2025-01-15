import React from 'react';
import Navbar from '@/components/Navbar';

const ClientLayout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <div>
      <Navbar />
      <main>
        {children}
      </main>
    </div>
  );
};

export default ClientLayout;