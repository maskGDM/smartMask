#include <SoftwareSerial.h>//library to use of the SIM900
#include <Wire.h>
//The sketch starts with including four libraries viz. Wire.h, SPI.h, Adafruit_Sensor.h and Adafruit_BME280.h.
#include <Adafruit_Sensor.h>
#include <Adafruit_BME280.h>

//Next, we define SEALEVELPRESSURE_HPA variable needed to calculate the altitude and create an object of 
//Adafruit_BME280 library so that we can access functions related to it.
#define SEALEVELPRESSURE_HPA (1013.25)
Adafruit_BME280 bme;

SoftwareSerial SIM900(7, 8);//tx, rx
int analogica_mq135internal = 0;//variable internal sensor scope
int analogica_mq135external = 0;//variable external sensor scope
String temperatura_bme280 = " NAN";
String presion_bme280 = " NAN";
String altitud_bme280 = " NAN";
String humedad_bme280 = " NAN";
boolean alertppm = false;//load default alert false
int max_value = 220;//bad air quality average upper limit
unsigned long timeelapsed;//arduino time to power on
unsigned long timesend;//time of 2 min to send + 1 min it will take to send

void setup()
{
  SIM900.begin(9600);
  Serial.begin(9600);
//In setup section of code we initialize the serial communication with PC and call the begin() function.

  Serial.println("starting");
  delay(2000);//20sg time needed to turn on the SIM
  Serial.println("Done!...");
  SIM900.flush();//clear SIM900 buffer
  Serial.flush();//clear arduino buffer
  pinMode(13, OUTPUT);//activate pin 13 where the buzzer is
  delay(6000);//20sg time needed to turn on the SIM
}

void loop()
{

  analogica_mq135internal = analogRead(A0);//Get data from internal sensor from analog port 0
  analogica_mq135external = analogRead(A7);//Get data from external sensor from analog port 2

  Serial.println("/-----------------------------/");
  Serial.print("Air quality internal = ");
  Serial.print(analogica_mq135internal, DEC);
  Serial.println(" ppm i");//print internal sensor data
  Serial.print("Air quality external = ");
  Serial.print(analogica_mq135external, DEC);
  Serial.println(" ppm e");//print internal sensor data
 //The begin(I2C_ADDR) function takes the I2C address of the module as parameter. 
 //This function initializes I2C interface with given I2C Address and checks if the chip ID is correct. 
 //It then resets the chip using soft-reset & waits for the sensor for calibration after wake-up.
  if (!bme.begin(0x76)) {
    Serial.println("Could not find a valid BME280 sensor, check wiring!");
  temperatura_bme280 = " NAN";
  presion_bme280 = " NAN";
  altitud_bme280 = " NAN";
  humedad_bme280 = " NAN";
 }else{
  temperatura_bme280 = bme.readTemperature();
  presion_bme280 = bme.readPressure() / 100.0F;
  altitud_bme280 = bme.readAltitude(SEALEVELPRESSURE_HPA);
  humedad_bme280 = bme.readHumidity();
  //readTemperature() function returns the temperature from the sensor.
  //readPressure() function returns the barometric pressure from the sensor.
  //readAltitude(SEALEVELPRESSURE_HPA) function calculates the altitude (in meters) from the specified atmospheric pressure (in hPa), and sea-level pressure (in hPa).
  //readHumidity() function returns the relative humidity from the sensor.
  Serial.print("Temperature = ");
  Serial.print(temperatura_bme280);
  Serial.println("*C");

  Serial.print("Pressure = ");
  Serial.print(presion_bme280);
  Serial.println("hPa");

  Serial.print("Approx. Altitude = ");
  Serial.print(altitud_bme280);
  Serial.println("m");

  Serial.print("Humidity = ");
  Serial.print(humedad_bme280);
  Serial.println("%");
 } 
  if (((analogica_mq135internal+analogica_mq135external)/2) >= max_value) {//check average of gases does not exceed bad air quality
    Serial.println("/------------ALERT INTERNAL -------------/");
    Serial.println(String(analogica_mq135internal));
    Serial.println(" ppm average");//print ppm alert
    //digitalWrite(13, HIGH);//activate alert
    alertppm = true;//load alert for app
    delay(2000);//2sg sound alert
    digitalWrite(13, LOW);//disable alert
    SubmitHttpRequest();//immediately send to app
    delay(1000);//1sg
  } else {
    digitalWrite(13, LOW);//keep alarm disabled
    alertppm = false;//load the alert with false
  }
   
  timeelapsed = millis();//get the misiseconds since the arduino was turned on
  if(timeelapsed > (timesend+120000))//send if 2 min has passed
  {    
    SubmitHttpRequest();//send to the application web services
     delay(1000);//1sg
    timesend = millis();//load shipping with current time
  }
  delay(1000);//1sg
  
}

void SubmitHttpRequest()
{
  Serial.println("/------------SEND OFFICIAL SMARTBOX-------------/");
  // See if the SIM900 is ready
  SIM900.println("AT");
  delay(1000);//1sg
  toSerialSIM();//read data and write response from SIM900

  //////////////////////////////////

  //Get time
  SIM900.println("AT+CCLK?");
  delay(1000);
  toSerialSIM();
  
  //We will see on behalf of the manufacturer
  SIM900.println("AT+CGMI");
  delay(1000);
  toSerialSIM();

  //Activate GPS
  SIM900.println("AT+CGPSPWR=1");
  delay(1000);
  toSerialSIM();

  //Get GPS coordinates
  SIM900.println("AT+CGPSSTATUS?");
  delay(1000);
  toSerialSIM();

  //We will see on behalf of the manufacturer
  SIM900.println("ATI");
  delay(1000);
  toSerialSIM();

  //Ask the Baud Rate in which the SIM can operate
  SIM900.println("AT+IPR=?");
  delay(1000);
  toSerialSIM();
  
  //It is used to ask the current Baud Rate
  SIM900.println("AT+IPR?");
  delay(1000);
  toSerialSIM();
  
  //Telephone company name
  SIM900.println("AT+COPS?");
  delay(1000);
  toSerialSIM();
  
  //View the IMEI of the chip used
  SIM900.println("AT+CGSN");
  delay(1000);
  toSerialSIM();

  ///////////////////////////////////

  // SIM card inserted and unlocked?
  SIM900.println("AT+CPIN?");
  delay(2000);//2sg
  toSerialSIM();

  // Is the SIM card registered?
  SIM900.println("AT+CREG?");
  delay(2000);//2sg
  toSerialSIM();

  // Is GPRS attached?
  SIM900.println("AT+CGATT?");
  delay(2000);//2sg
  toSerialSIM();

  // Check signal strength
  SIM900.println("AT+CSQ ");
  delay(2000);//2sg
  toSerialSIM();

  // Set connection type to GPRS
  SIM900.println("AT+SAPBR=3,1,\"Contype\",\"GPRS\"");
  delay(3000);//3sg
  toSerialSIM();

  // Set the APN
  SIM900.println("AT+SAPBR=3,1,\"APN\",\"internet.movistar.com.ec\"");
  delay(3000);//3sg
  toSerialSIM();

  // Enable GPRS
  SIM900.println("AT+SAPBR=0,1");
  delay(2000);//2sg
  toSerialSIM();

  // Enable GPRS
  SIM900.println("AT+SAPBR=1,1");
  delay(3000);//3sg
  toSerialSIM();

  // Check to see if connection is correct and get your IP address
  SIM900.println("AT+SAPBR=2,1");
  delay(4000);//4sg
  toSerialSIM();

  // initialize http service
  SIM900.println("AT+HTTPINIT");
  delay(5000);//5sg
  toSerialSIM();

  if (temperatura_bme280 == " NAN"){
    temperatura_bme280 = "N";
    presion_bme280 = "N";
    altitud_bme280 = "N";
    humedad_bme280 = "N";
  }

  // set http param value
  // ToDO : send dynamic value
  String webServices = "http://190.15.134.7/smartmask/RecordsS?pi=" + String(analogica_mq135internal) + "&pe=" + String(analogica_mq135internal) + + "&m=" + String("520181") + "&a="+boolean(alertppm)+ "&t="+String(temperatura_bme280)+ "&p="+String(presion_bme280)+ "&al="+String(altitud_bme280)+ "&h="+String(humedad_bme280)+"";//concatenated string for submission
  Serial.println(webServices);
  SIM900.println("AT+HTTPPARA=\"URL\",\"" + String(webServices) + "\"");//I send to the application through the web services
  delay(5000);//5sg
  toSerialSIM();

  // set http action type 0 = GET, 1 = POST, 2 = HEAD
  SIM900.println("AT+HTTPACTION=0");
  delay(5000);//5sg
  toSerialSIM();

  // read server response
  SIM900.println("AT+HTTPREAD");
  delay(4000);//4sg
  toSerialSIM();

  SIM900.println("AT+HTTPTERM");
  toSerialSIM();
  delay(4000);//4sg

  SIM900.println("");
  delay(3000);//3sg
}

void toSerialSIM()
{
  while (SIM900.available() != 0)
  {
    Serial.write(SIM900.read());//read at command and write your response
  }
}
