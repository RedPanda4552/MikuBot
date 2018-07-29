/**
 * This file is part of MikuBot, licensed under the MIT License (MIT)
 * 
 * Copyright (c) 2018 Brian Wood
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.redpanda4552.MikuBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class MikuBot {

    private static MikuBot mikuBot;
    private static String discordToken;
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar MikuBot-x.y.z.jar <discord-token>");
            return;
        }
        
        discordToken = args[0];
        
        while (true) {
            if (mikuBot == null) {
                System.out.println("No self instance, creating one");
                mikuBot = new MikuBot();
            }
            
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                System.out.println("MikuBot Interrupt!");
            }
        }
    }
    
    public static MikuBot getSelf() {
        return mikuBot;
    }
    
    private JDA jda;
    
    public MikuBot() {
        if (discordToken == null || discordToken.isEmpty()) {
            System.out.println("Attempted to start with a null or empty Discord token!");
            return;
        }
        
        try {
            jda = new JDABuilder(AccountType.CLIENT)
                    .setToken(discordToken)
                    .setAutoReconnect(true)
                    .addEventListener(new EventListener())
                    .buildBlocking();
        } catch (LoginException | IllegalArgumentException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public JDA getJDA() {
        return jda;
    }
    
}
