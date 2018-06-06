import java.io.IOException;

class MessageHandler {

    public void handle(Message message) throws IOException {
        System.out.println("MessageHandler: in handle(Message message):\nmessage: " + message);
        Request request = MainWebSocket.mapper.readValue(message.getData(), Request.class);
        System.out.println("request.testCase = " + request.testCase);
        ResponseData responseData = new Physics(request.testCase).getTrajectory();
        String data = MainWebSocket.mapper.writeValueAsString(responseData);

        Message responseMessage = new Message();
        responseMessage.setType(MainWebSocket.RESPONSE);
        responseMessage.setStatus(MainWebSocket.SUCCEED);
        responseMessage.setData(data);
        MainWebSocket.sendMessage(responseMessage);
    }
}