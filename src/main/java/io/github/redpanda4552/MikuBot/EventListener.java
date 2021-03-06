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

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

    public EventListener() {
        
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Stop conditions
        if (event.getChannelType() != ChannelType.PRIVATE)
            return;
        
        if (event.getAuthor().getId().equals(MikuBot.getSelf().getJDA().getSelfUser().getId()))
            return;
        
        if (event.getAuthor().isBot())
            return;
        
        if (MikuBot.getSelf().getJDA().asClient().getFriend(event.getAuthor()) != null)
            return;
        
        MessageBuilder mb = new MessageBuilder("**Sorry, but I do not respond to unsolicited DMs from non-friends.**\n\n");
        List<Guild> mutualGuilds = event.getAuthor().getMutualGuilds();
        
        if (mutualGuilds.size() == 0) {
            mb.append("I don't see any mutual servers between us, but maybe if I recognize you I will add you as a friend and respond.");
        } else if (mutualGuilds.size() == 1) {
            mb.append("You probably found me on the " + mutualGuilds.get(0).getName() + " server, please try to get ahold of me there instead.");
        } else {
            mb.append("It looks like we have multiple mutual servers, please find me on the appropriate one and get ahold of me there instead.");
        }
        
        event.getChannel().sendMessage(mb.build()).complete();
    }
}
