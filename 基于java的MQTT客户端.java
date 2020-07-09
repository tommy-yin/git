package com.ourselec.test;

import java.util.Random;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MqttClient implements MqttCallback{
	private String serverIp; //服务器IP地址
	private int serverPort;  //服务端口
	private boolean ssl; //连接方式是否为ssl
	private boolean cleanSession; //连接时是否清除session
	private String userName; //用户账号
	private String password; //用户密码
	private MqttAsyncClient client;//一个允许在后台运行的轻量级客户端，采用非阻塞式的方法连接MQTT服务器
	
	public MqttClient(String serverIp, int serverPort, boolean ssl, boolean cleanSession, 
			String userName, String password) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.ssl = ssl;
		this.cleanSession = cleanSession;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 连接MQTT服务器
	 * @throws MqttException
	 */
	public void connect() throws MqttException{
		//拼接MQTT服务地址
		String brokerUrl = ssl?("ssl://"+this.serverIp+":"+this.serverPort):("tcp://"+this.serverIp+":"+this.serverPort);
		//持久化数据存放目录
		String tmpDir = MqttClient.class.getResource("/").getPath().replaceAll("bin", "temp");
		//针对单个客户端的持久化数据存储对象
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
		//客户端连接服务器配置选项
		MqttConnectOptions conOpt = new MqttConnectOptions();
		//设置每次连接时是否清除以前连接的数据 
		conOpt.setCleanSession(cleanSession);
		if(userName!=null) {
			//设置用户名信息
			conOpt.setUserName(userName);
		}
		if(password!=null) {
			//设置用户密码信息
			conOpt.setPassword(password.toCharArray());
		}
		//初始化创建客户端////////////////////////////////////////////////////////////////////////////////////////////////
		client = new MqttAsyncClient(brokerUrl,"client_0415", dataStore);
		//设置客户端回调函数
		client.setCallback(this);
		//连接时的监听器，用于提示连接状态
		IMqttActionListener conListener = new IMqttActionListener() {
			public void onSuccess(IMqttToken asyncActionToken) {
				System.out.println("CONNECT OK");
			}

			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
				System.out.println("CONNECT ERROR");
			}
		};
		
		if(!client.isConnected()){
			//连接服务器
			client.connect(conOpt,"Connect sample context", conListener);
		}
	}
	
	/**
	 * 发布消息
	 * @param topic 消息主题
	 * @param qos 消息等级
	 * @param payload 消息内容
	 * @throws MqttException 
	 */
	public void publish(String topic, int qos, byte[] payload) throws MqttException{
		//封装要发布的消息
		MqttMessage message = new MqttMessage(payload);
		IMqttActionListener pubListener = new IMqttActionListener() {
			public void onSuccess(IMqttToken asyncActionToken) {
				System.out.println("发布成功");
			}

			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
				System.out.println("发布失败");
			}
		};
		//发布主题消息
		client.publish(topic, message, "Pub sample context", pubListener);
	}	
	/**
	 * 订阅主题
	 * @param topic 主题名称
	 * @param qos 消息等级
	 * @throws MqttException
	 */
	public void subscribe(final String topic, int qos) throws MqttException{
		IMqttActionListener subListener = new IMqttActionListener() {
			public void onSuccess(IMqttToken asyncActionToken) {
				System.out.println("订阅主题："+topic+"成功");
			}

			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
				System.out.println("订阅失败");
			}
		};
		//订阅主题消息
		client.subscribe(topic, qos, "Subscribe sample context", subListener);
	}	
	 /**
     * 连接断开时触发
     */
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection is lost!" + cause);
	}
	/**
	 * 消息到达时触发
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message)throws Exception {
		System.out.println("receive:"+topic+"----"+new String(message.getPayload()));
	}
	/**
	 * 消息送达时触发
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}
	/**
	 * 生成指定长度client ID
	 * @param len ID长度
	 * @return
	 */
	private static String newClientId(int len){
		String str = "";
		Random ran = new Random();
		for(int i=0;i<len;i++){
			str+= (char)(ran.nextInt(26)+97);
		}
		return str;
	}
	
	public static void main(String[] args) throws InterruptedException, MqttException {
		//这是一个公网IP，部署了MQTTServer，在自己电脑上运行MQTTServer，这样IP修改为127.0.0.1即可
		String ip = "127.0.0.1";
		int port = 10415;
		boolean ssl = false;
		boolean cleanSession = true;
		String userName = null;
		String password = null;
		//实例化Mqtt客户端
		MqttClient mc = new MqttClient(ip, port, ssl, cleanSession, userName, password);
		//初始化信息并连接服务器
		mc.connect();
		
		while(!mc.client.isConnected()){
			System.out.println("等待连接！");
			Thread.sleep(1000);
		}
		
		int qos = 2;
		String topic = "myMqtt_0415";/////////////////////////////////////////////////////////////
		mc.subscribe(topic, qos); //订阅主题
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while(true){
			//发布主题消息，消息内容为每次输入的内容
			String msg = scan.nextLine();
			mc.publish(topic, qos, msg.getBytes());
		}
	}
}
