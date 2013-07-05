module GameMachine
  module Systems
    class Chat < Actor

      def post_init(*args)
        @player_id = args.first
        @client_connection = args.last
        @topic_handlers = {}
      end

      def on_receive(entity)
        if entity.has_join_chat
          join_channels(entity.join_chat.get_chat_channel_list)
          GameMachine.logger.info 'join_chat'
        end

        if entity.has_leave_chat
          leave_channels(entity.leave_chat.get_chat_channel_list)
          GameMachine.logger.info 'leave_chat'
        end

        if entity.has_chat_message
          GameMachine.logger.info "chat_message #{entity.chat_message}"
          send_message(entity.chat_message)
        end
      end

      private

      def message_queue
        MessageQueue.find
      end

      def create_topic_handler(topic)
        builder = ActorBuilder.new(Systems::ChatTopic,@player_id,@client_connection)
        @topic_handlers[topic] = builder.with_parent(context).with_name("topic#{@player_id}#{topic}").start
      end

      def join_channels(chat_channels)
        chat_channels.each do |channel|
          create_topic_handler(channel.name)
          message_queue.tell(Subscribe.new.set_topic(channel.name),@topic_handlers[channel.name])
        end
      end

      def leave_channels(chat_channels)
        chat_channels.each do |channel|
          message_queue.tell(Unsubscribe.new.set_topic(channel.name),@topic_handlers[channel.name])
        end
      end

      def send_private_message(chat_message)
        if @players.has_key?(chat_message.chat_channel.name)
          client_connection = @players.fetch(chat_message.chat_channel.name)
          client_message = ClientMessage.new
          client_message.add_entity(Entity.new.set_id('0').set_chat_message(chat_message))
          client_message.set_client_connection(client_connection)
          client_message.send_to_client
        end
      end

      def send_group_message(chat_message)
        topic = chat_message.chat_channel.name
        entity = Entity.new.set_id('0').set_chat_message(chat_message)
        publish = Publish.new
        publish.set_topic(topic).set_message(entity)
        message_queue.tell(publish,@topic_handlers[topic])
      end

      def send_message(chat_message)
        if chat_message.type == 'group'
          send_group_message(chat_message)
        elsif chat_message.type == 'private'
          send_private_message(chat_message)
        end
      end

    end
  end
end