import { useState } from "react";
import { useSession } from "src/hooks";

const ChatMessage = ( props : ChatMessageProps ) => {

  const { content, sender } = props

  const session = useSession()

  const [ userId ] = useState( session( 'id' ) )

  return (
      ( userId === sender )?
      ( <div>
          <strong>{ sender }:</strong> { content }
        </div>):
      ( <div>
          <strong>{ sender }:</strong> { content }
        </div>
      )
  )
}

export default ChatMessage;

type ChatMessageProps = {
  content?: string;
  sender?: string;
}