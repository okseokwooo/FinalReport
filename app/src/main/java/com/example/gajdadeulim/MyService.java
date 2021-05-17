package com.example.gajdadeulim;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyService extends Service {
    public static final String hostname = "10.0.2.2";
    public final int port = 7777;
    public Socket clientSocket;
    public ConnectThread thread;
    public PrintWriter sendWriter;
    public ReceiveThread receivethread;

    public MyService() {
    }

    //여기서 소켓생성 및 인텐트 수신처리
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals("Access")){
            thread = new ConnectThread();
            thread.start();
        }
        return START_STICKY;
    }

    //쓰레드를 정지하는 코드작성
    @Override
    public void onDestroy() {
        super.onDestroy();
        receivethread.socket = null;
        thread.status = true;
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class ConnectThread extends Thread{
        public Boolean status=false;
        public void run(){
            try {
                clientSocket = new Socket(hostname,port);
                Log.d("ConnectThread","Success");
                sendWriter = new PrintWriter(clientSocket.getOutputStream());
                receivethread = new ReceiveThread();
                receivethread.setSocket(clientSocket);
                receivethread.start();
                while(true){
                    if(status){
                        sendWriter.close();
                        clientSocket.close();
                        break;
                    }
                }
            }
            catch (UnknownHostException e){
                Log.e("TAG"," 생성 Error : 호스트의 IP 주소를 식별할 수 없음.(잘못된 주소 값 또는 호스트이름 사용)");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch(SecurityException e){
                Log.e("TAG"," 생성 Error : 보안(Security) 위반에 대해 보안 관리자(Security Manager)에 의해 발생. (프록시(proxy) 접속 거부, 허용되지 않은 함수 호출)");
            }
            catch (IllegalArgumentException e){
                Log.e("TAG"," 생성 Error : 메서드에 잘못된 파라미터가 전달되는 경우 발생. (0~65535 범위 밖의 포트 번호 사용, null 프록시(proxy) 전달)");
            }
        }
    }

    public class ReceiveThread extends Thread{
        private Socket socket;

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receiveString ="";

                Log.d("ReceiveThread","Success");
                while(socket != null) {
                    receiveString = bufferedreader.readLine();


                }
                bufferedreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SendThread extends Thread {
        public String msg = "";

        public SendThread(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            sendWriter.println(msg);
            sendWriter.flush();
        }
    }
}