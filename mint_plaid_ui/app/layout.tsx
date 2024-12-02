import type { Metadata } from "next";
import { Inter, IBM_Plex_Serif } from "next/font/google";
import "./globals.css";
import React from "react";
import Providers from "@/app/Providers";

const inter = Inter({ subsets: ["latin"], variable: '--font-inter' });
const ibmFlexSerif = IBM_Plex_Serif({
  subsets: ['latin'],
  weight: ['400', '700'],
  variable: '--font-ibm-plex-serif'
})

export const metadata: Metadata = {
  title: "Mint Plaid",
  description: "Modern personal finance app empowered by Plaid",
  icons: {
    icon: '/icons/logo.svg'
  }
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${inter.variable} ${ibmFlexSerif.variable}`}>
      <Providers>
        {children}
      </Providers>
      </body>
    </html>
  );
}
