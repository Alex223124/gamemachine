
package io.gamemachine.client.messages;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;

import com.dyuproject.protostuff.ByteString;
import com.dyuproject.protostuff.GraphIOUtil;
import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Message;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.ProtobufOutput;

import java.io.ByteArrayOutputStream;
import com.dyuproject.protostuff.JsonIOUtil;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import io.gamemachine.util.LocalLinkedBuffer;


import java.nio.charset.Charset;


import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.UninitializedMessageException;


@SuppressWarnings("unused")
public final class Subscribe implements Externalizable, Message<Subscribe>, Schema<Subscribe>{



    public static Schema<Subscribe> getSchema()
    {
        return DEFAULT_INSTANCE;
    }

    public static Subscribe getDefaultInstance()
    {
        return DEFAULT_INSTANCE;
    }

    static final Subscribe DEFAULT_INSTANCE = new Subscribe();

    			public String topic;
	    
        			public String gameId;
	    
      
    public Subscribe()
    {
        
    }


	

	    
    public Boolean hasTopic()  {
        return topic == null ? false : true;
    }
        
		public String getTopic() {
		return topic;
	}
	
	public Subscribe setTopic(String topic) {
		this.topic = topic;
		return this;	}
	
		    
    public Boolean hasGameId()  {
        return gameId == null ? false : true;
    }
        
		public String getGameId() {
		return gameId;
	}
	
	public Subscribe setGameId(String gameId) {
		this.gameId = gameId;
		return this;	}
	
	
  
    // java serialization

    public void readExternal(ObjectInput in) throws IOException
    {
        GraphIOUtil.mergeDelimitedFrom(in, this, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException
    {
        GraphIOUtil.writeDelimitedTo(out, this, this);
    }

    // message method

    public Schema<Subscribe> cachedSchema()
    {
        return DEFAULT_INSTANCE;
    }

    // schema methods

    public Subscribe newMessage()
    {
        return new Subscribe();
    }

    public Class<Subscribe> typeClass()
    {
        return Subscribe.class;
    }

    public String messageName()
    {
        return Subscribe.class.getSimpleName();
    }

    public String messageFullName()
    {
        return Subscribe.class.getName();
    }

    public boolean isInitialized(Subscribe message)
    {
        return true;
    }

    public void mergeFrom(Input input, Subscribe message) throws IOException
    {
        for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
        {
            switch(number)
            {
                case 0:
                    return;
                            	case 1:
            	                	                	message.topic = input.readString();
                	break;
                	                	
                            	            	case 2:
            	                	                	message.gameId = input.readString();
                	break;
                	                	
                            	            
                default:
                    input.handleUnknownField(number, this);
            }   
        }
    }


    public void writeTo(Output output, Subscribe message) throws IOException
    {
    	    	
    	    	
    	    	    	if(message.topic != null)
            output.writeString(1, message.topic, false);
    	    	
    	            	
    	    	if(message.gameId == null)
            throw new UninitializedMessageException(message);
    	    	
    	    	    	if(message.gameId != null)
            output.writeString(2, message.gameId, false);
    	    	
    	            	
    }

    public String getFieldName(int number)
    {
        switch(number)
        {
        	        	case 1: return "topic";
        	        	case 2: return "gameId";
        	            default: return null;
        }
    }

    public int getFieldNumber(String name)
    {
        final Integer number = __fieldMap.get(name);
        return number == null ? 0 : number.intValue();
    }

    private static final java.util.HashMap<String,Integer> __fieldMap = new java.util.HashMap<String,Integer>();
    static
    {
    	    	__fieldMap.put("topic", 1);
    	    	__fieldMap.put("gameId", 2);
    	    }
   
   public static List<String> getFields() {
	ArrayList<String> fieldNames = new ArrayList<String>();
	String fieldName = null;
	Integer i = 1;
	
    while(true) { 
		fieldName = Subscribe.getSchema().getFieldName(i);
		if (fieldName == null) {
			break;
		}
		fieldNames.add(fieldName);
		i++;
	}
	return fieldNames;
}

public static Subscribe parseFrom(byte[] bytes) {
	Subscribe message = new Subscribe();
	ProtobufIOUtil.mergeFrom(bytes, message, Subscribe.getSchema());
	return message;
}

public static Subscribe parseFromJson(String json) throws IOException {
	byte[] bytes = json.getBytes(Charset.forName("UTF-8"));
	Subscribe message = new Subscribe();
	JsonIOUtil.mergeFrom(bytes, message, Subscribe.getSchema(), false);
	return message;
}

public Subscribe clone() {
	byte[] bytes = this.toByteArray();
	Subscribe subscribe = Subscribe.parseFrom(bytes);
	return subscribe;
}
	
public byte[] toByteArray() {
	return toProtobuf();
	//return toJson();
}

public String toJson() {
	boolean numeric = false;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	try {
		JsonIOUtil.writeTo(out, this, Subscribe.getSchema(), numeric);
	} catch (IOException e) {
		e.printStackTrace();
		throw new RuntimeException("Json encoding failed");
	}
	String json = new String(out.toByteArray(), Charset.forName("UTF-8"));
	return json;
}

public byte[] toPrefixedByteArray() {
	LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
  Schema<Subscribe> schema = Subscribe.getSchema();
    
	final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ProtobufOutput output = new ProtobufOutput(buffer);
    try
    {
    	schema.writeTo(output, this);
        final int size = output.getSize();
        ProtobufOutput.writeRawVarInt32Bytes(out, size);
        final int msgSize = LinkedBuffer.writeTo(out, buffer);
        assert size == msgSize;
        
        buffer.clear();
        return out.toByteArray();
    }
    catch (IOException e)
    {
        throw new RuntimeException("Serializing to a byte array threw an IOException " + 
                "(should never happen).", e);
    }
 
}

public byte[] toProtobuf() {
	LinkedBuffer buffer = LocalLinkedBuffer.get();
	byte[] bytes = null;

	try {
		bytes = ProtobufIOUtil.toByteArray(this, Subscribe.getSchema(), buffer);
		buffer.clear();
	} catch (Exception e) {
		buffer.clear();
		e.printStackTrace();
		throw new RuntimeException("Protobuf encoding failed");
	}
	return bytes;
}

}
